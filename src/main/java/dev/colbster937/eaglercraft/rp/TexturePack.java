package dev.colbster937.eaglercraft.rp;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.lwjgl.opengl.GL11;

import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.EaglerInputStream;
import net.lax1dude.eaglercraft.EaglercraftUUID;
import net.lax1dude.eaglercraft.HString;
import net.lax1dude.eaglercraft.internal.FileChooserResult;
import net.lax1dude.eaglercraft.internal.vfs2.VFile2;
import net.lax1dude.eaglercraft.opengl.ImageData;
import net.minecraft.client.Minecraft;
import net.minecraft.src.IProgressUpdate;

public class TexturePack {
  private static final VFile2 root = new VFile2(Minecraft.getMinecraftDir(), "texturePacks");
  private static final VFile2 packs = new VFile2(root, "packs.txt");

  private static Minecraft mc = null;
  private static boolean mod = false;
  private static TexturePack selectedPack = new TexturePack("Default");
  private static List<TexturePack> texturePacksCache = null;

  private final String name;
  private final VFile2 pack;
  private final VFile2 meta;
  private final VFile2 files;

  private String[] description = new String[] { "", "" };
  private ImageData icon = null;
  private int iconTexture = -1;
  private String id = "";

  protected TexturePack(String name, String[] description) {
    this.name = name;
    this.pack = new VFile2(root, name);
    this.meta = new VFile2(pack, "pack.txt");
    this.files = new VFile2(pack, "files.txt");
    this.init(description);
  }

  protected TexturePack(String name) {
    this(name, new String[0]);
  }

  private void init(String[] description) {
    if (description.length > 0) {
      for (int i = 0; i < Math.min(2, description.length); i++) {
        String line = description[i];
        if (line != null)
          this.description[i] = truncateDescLine(line);
      }
    } else if (meta.exists()) {
      String[] lines = meta.getAllLines();
      for (int i = 0; i < Math.min(2, lines.length); i++) {
        String line = lines[i];
        if (line != null)
          this.description[i] = truncateDescLine(line);
      }
    }
    this.icon = ImageData.loadImageFile(this.getResourceStream("pack.png"));
    EaglercraftUUID nameId = EaglercraftUUID.nameUUIDFromBytes(this.name.getBytes());
    EaglercraftUUID descId = EaglercraftUUID
        .nameUUIDFromBytes((this.description[0] + "\n " + this.description[1]).getBytes());
    EaglercraftUUID packId = EaglercraftUUID.nameUUIDFromBytes((nameId + "-" + descId).getBytes());
    this.id = packId.toString();
  }

  public String getName() {
    return this.name;
  }

  public String[] getDescription() {
    return this.description;
  }

  public ImageData getIcon() {
    return this.icon;
  }

  public String getID() {
    return this.id;
  }

  public InputStream getResourceStream(String path) {
    VFile2 file = new VFile2(this.pack, path);
    if (file.exists())
      return file.getInputStream();
    else
      return EagRuntime.getResourceStream(path);
  }

  public void bindIconTexture() {
    if (this.icon != null) {
      if (this.iconTexture < 0) this.iconTexture = mc.renderEngine.allocateAndSetupTexture(this.icon);
      mc.renderEngine.bindTexture(this.iconTexture);
    } else {
      GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/gui/unknown_pack.png"));
    }
  }

  public void delete(IProgressUpdate prog) {
    updateProgress(prog, "Deletinging Files");
    String[] files = this.files.getAllLines();
    for (int i = 0; i < files.length; i++) {
      VFile2 file = new VFile2(pack, files[i]);
      updateProgress(prog, HString.format("Deleting %s", file.getName()),
          (int) Math.round(((i + 1) * 100.0) / files.length));
      file.delete();
    }
    PrintWriter pw = new PrintWriter(packs.getOutputStream());
    for (String file : packs.getAllLines())
      if (!this.name.equals(file))
        pw.println(file);
    pw.close();

    this.files.delete();
    this.pack.delete();

    this.dispose();

    mod = true;
  }

  public void delete() {
    this.delete(null);
  }

  public void dispose() {
    if (this.iconTexture >= 0) {
      mc.renderEngine.deleteTexture(iconTexture);
      this.iconTexture = -1;
    }
    this.icon = null;
  }

  private boolean isPack(TexturePack pack) {
    return this.id.equals(pack.id);
  }

  public static void init(Minecraft mc) {
    TexturePack.mc = mc;
    if (!mc.gameSettings.skin.equals("Default"))
      selectedPack = new TexturePack(mc.gameSettings.skin);
    else
      selectedPack = DefaultTexturePack.getInstance();
  }

  public static List<TexturePack> getTexturePacks() {
    boolean changed = mod;
    mod = false;

    if (texturePacksCache == null || changed) {
      if (texturePacksCache != null)
        for (TexturePack pack : texturePacksCache)
          pack.dispose();
      List<TexturePack> arr = new ArrayList<>();
      arr.add(DefaultTexturePack.getInstance());
      if (packs.exists()) {
        for (String pack : packs.getAllLines()) {
          arr.add(new TexturePack(pack));
        }
      }
      return texturePacksCache = arr;
    } else {
      return texturePacksCache;
    }
  }

  public static TexturePack getSelectedPack() {
    return selectedPack;
  }

  public static void setSelectedPack(TexturePack pack, IProgressUpdate prog) {
    if (isSelectedPack(pack))
      return;
    selectedPack = pack;
    mc.gameSettings.skin = pack.getName();
    mc.gameSettings.saveOptions();
    mc.renderEngine.refreshTextures();
    updateProgress(prog, "Refreshing Texture Pack");
  }

  public static void setDefaultPack(IProgressUpdate prog) {
    setSelectedPack((TexturePack) null, prog);
  }

  public static void setSelectedPack(TexturePack pack) {
    setSelectedPack(pack, null);
  }

  public static void setDefaultPack() {
    setDefaultPack(null);
  }

  public static boolean isSelectedPack(TexturePack pack) {
    return selectedPack.isPack(pack);
  }

  public static boolean isSelectedPack(int pack) {
    return isSelectedPack(getTexturePacks().get(pack));
  }

  public static void addTexturePack(String name, byte[] data, IProgressUpdate prog) throws IOException {
    List<TexturePack> existingPacks = getTexturePacks();

    int d = name.lastIndexOf('.');
    if (d > 0)
      name = name.substring(0, d);

    for (TexturePack pack : existingPacks)
      while (name.equalsIgnoreCase(pack.name))
        name += "-";

    TexturePack pack = new TexturePack(name);
    List<String> files = new ArrayList<>();

    updateProgress(prog, "Extracting Files");

    try (ZipInputStream zis = new ZipInputStream(new EaglerInputStream(data))) {
      ZipEntry entry;
      while ((entry = zis.getNextEntry()) != null) {
        if (entry.isDirectory()) {
          zis.closeEntry();
          continue;
        }
        String file = entry.getName();
        files.add(file);
        updateProgress(prog, HString.format("Extracting %s", file));
        (new VFile2(pack.pack, file)).setAllBytes(EaglerInputStream.inputStreamToBytesNoClose(zis));
        zis.closeEntry();
      }
    }

    updateProgress(prog, HString.format("Updating Manifest"));

    PrintWriter pw = new PrintWriter(pack.files.getOutputStream());

    for (String file : files)
      pw.println(file);

    pw.close();

    pw = new PrintWriter(packs.getOutputStream());

    if (packs.exists()) {
      for (String file : packs.getAllLines())
        pw.println(file);
      packs.delete();
    }

    pw.println(name);

    pw.close();

    mod = true;
  }

  public static void addTexturePack(String name, byte[] data) throws IOException {
    addTexturePack(name, data, null);
  }

  public static void addTexturePack(FileChooserResult result, IProgressUpdate prog) throws IOException {
    addTexturePack(result.fileName, result.fileData, prog);
  }

  public static void addTexturePack(FileChooserResult result) throws IOException {
    addTexturePack(result.fileName, result.fileData);
  }

  public static InputStream getResourceAsStream(String path) {
    if (selectedPack != null)
      return selectedPack.getResourceStream(path);
    else
      return EagRuntime.getResourceStream(path);
  }

  private static void updateProgress(IProgressUpdate prog, String text, int perc) {
    if (prog != null) {
      if (text != null && !text.isEmpty() && !text.isBlank())
        prog.displayLoadingString(text);
      if (perc != -2)
        prog.setLoadingProgress(perc);
    }
  }

  private static void updateProgress(IProgressUpdate prog, String text) {
    updateProgress(prog, text, -2);
  }

  private static String truncateDescLine(String var1) {
    if (var1 != null && var1.length() > 34)
      var1 = var1.substring(0, 34);
    return var1;
  }
}
