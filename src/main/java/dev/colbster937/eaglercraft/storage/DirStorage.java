package dev.colbster937.eaglercraft.storage;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.json.JSONArray;

import dev.colbster937.eaglercraft.FormattingCodes;
import dev.colbster937.eaglercraft.gui.GuiScreenInfo;
import dev.colbster937.eaglercraft.gui.GuiScreenInfo.TextLine;
import dev.colbster937.eaglercraft.utils.JSONUtils;
import dev.colbster937.eaglercraft.utils.ScuffedUtils;

import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.EaglerInputStream;
import net.lax1dude.eaglercraft.HString;
import net.lax1dude.eaglercraft.internal.FileChooserResult;
import net.lax1dude.eaglercraft.internal.vfs2.VFile2;

import net.minecraft.client.Minecraft;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.LoadingScreenRenderer;

public class DirStorage {
  public final VFile2 root;

  private final Minecraft mc;
  private final VFile2 files;
  private JSONArray filesArr;

  public DirStorage(Minecraft mc, String name) {
    this.mc = mc;
    this.root = new VFile2(Minecraft.getMinecraftDir(), name);
    this.files = new VFile2(root, name + ".json");
    this.filesArr = JSONUtils.stringToArray(this.files.getAllChars());
  }

  public void _import(IProgressUpdate prog, String name, byte[] data) {
    String _name = name;
    try {
      int count = 0;
      ZipEntry entry;

      updateProgress(prog, "Counting Files");

      try (ZipInputStream zis = new ZipInputStream(new EaglerInputStream(data.clone()))) {
        while ((entry = zis.getNextEntry()) != null) {
          if (!entry.isDirectory())
            count++;
        }
        zis.close();
      }

      int d = name.lastIndexOf('.');
      if (d > 0)
        name = name.substring(0, d);

      for (String file : getFilesInManifest())
        while (name.equalsIgnoreCase(file))
          name += "-";

      updateProgress(prog, "Extracting Files");

      JSONArray arr = new JSONArray();
      VFile2 dir = new VFile2(root, name);

      try (ZipInputStream zis = new ZipInputStream(new EaglerInputStream(data.clone()))) {
        int i = 0;
        while ((entry = zis.getNextEntry()) != null) {
          if (entry.isDirectory())
            continue;
          String file = entry.getName();
          arr.put(file);
          updateProgress(prog, HString.format("Extracting '%s'", file), (int) Math.round((++i * 100.0) / count));
          (new VFile2(dir, file)).setAllBytes(EaglerInputStream.inputStreamToBytesNoClose(zis));
        }
        zis.close();
      }

      addToManifest(prog, name);
    } catch (Throwable t) {
      delete(prog, name);
      showErrorScreen(HString.format("An error occurred while importing '%s'", _name), t);
    }
  }

  public void _import(String name, byte[] data) {
    this._import(null, name, data);
  }

  public void _import(IProgressUpdate prog, FileChooserResult result) {
    this._import(prog, result.fileName, result.fileData);
  }

  public void _import(FileChooserResult result) {
    this._import(result.fileName, result.fileData);
  }

  public void export(IProgressUpdate prog, String name) {
    ByteArrayOutputStream baos = null;
    ZipOutputStream zos = null;
    try {
      VFile2 _root = (new VFile2(this.root, name));
      List<VFile2> files = _root.listFiles(true);
      int subpath = _root.getPath().length();
      baos = new ByteArrayOutputStream();
      zos = new ZipOutputStream(baos);
      updateProgress(prog, "Exporting Files");
      int i = 0;
      for (VFile2 file : files) {
        String path = file.getPath().substring(subpath);
        if (ScuffedUtils.isStringEmpty(path))
          continue;
        updateProgress(prog, HString.format("Zipping '%s'", path.substring(1)),
            (int) Math.round((++i * 100.0) / files.size()));
        zos.putNextEntry(new ZipEntry(path));
        zos.write(file.getAllBytes());
        zos.closeEntry();
      }
      zos.finish();
      zos.flush();
      EagRuntime.downloadFileWithName(name + ".zip", baos.toByteArray());
    } catch (Throwable t) {
      showErrorScreen(HString.format("An error occurred while exporting '%s'", name), t);
    } finally {
      try {
        baos.close();
      } catch (Throwable t) {
      }

      try {
        zos.close();
      } catch (Throwable t) {
      }
    }
  }

  public void export(String name) {
    this.export(null, name);
  }

  public void delete(IProgressUpdate prog, String name) {
    VFile2 _root = (new VFile2(this.root, name));
    List<VFile2> files = _root.listFiles(true);
    int subpath = _root.getPath().length();
    int i = 0;
    updateProgress(prog, "Deleting Files");
    for (VFile2 file : files) {
      updateProgress(prog, HString.format("Deleting '%s'", file.getPath().substring(subpath + 1)),
          (int) Math.round((++i * 100.0) / files.size()));
      if (file.exists())
        file.delete();
    }
    removeFromManifest(name);
  }

  public void delete(String name) {
    this.delete(null, name);
  }

  public void addToManifest(IProgressUpdate prog, String name) {
    this.filesArr.put(name);
    updateManifest(prog);
  }

  public void addToManifest(String name) {
    this.addToManifest(null, name);
  }

  public void removeFromManifest(IProgressUpdate prog, String name) {
    JSONArray arr = new JSONArray();
    for (String file : getFilesInManifest())
      if (!file.equals(name))
        arr.put(file);
    this.filesArr = arr;
    updateManifest(prog);
  }

  public void removeFromManifest(String name) {
    this.removeFromManifest(null, name);
  }

  public List<String> getFilesInManifest() {
    return JSONUtils.<String>arrayToList(this.filesArr);
  }

  public void updateProgress(IProgressUpdate prog, String title, String text, int perc) {
    if (prog != null) {
      if (!ScuffedUtils.isStringEmpty(title)) {
        if (prog instanceof LoadingScreenRenderer)
          ((LoadingScreenRenderer) prog).func_596_a(title);
        else
          prog.func_594_b(title);
      }
      if (!ScuffedUtils.isStringEmpty(text)) {
        prog.displayLoadingString(text);
      }
      if (perc != -2) {
        prog.setLoadingProgress(perc);
      }
    }
  }

  public void updateProgress(IProgressUpdate prog, String title) {
    this.updateProgress(prog, title, "", -2);
  }

  public void updateProgress(IProgressUpdate prog, String title, String text) {
    this.updateProgress(prog, title, text, -2);
  }

  public void updateProgress(IProgressUpdate prog, String text, int perc) {
    this.updateProgress(prog, "", text, perc);
  }

  private void updateManifest(IProgressUpdate prog) {
    this.updateProgress(prog, "Updating Manifest");
    if (this.filesArr.length() > 0)
      files.setAllBytes(this.filesArr.toString().getBytes());
    else if (files.exists())
      files.delete();
  }

  private void showErrorScreen(String title, Throwable t) {
    List<TextLine> lines = new ArrayList<>();
    String msg = t.getMessage();
    lines.add(new TextLine(title, FormattingCodes.COLOR_ERROR));
    lines.add(new TextLine(""));
    if (this.mc.fontRenderer.getStringWidth(msg) > (this.mc.displayWidth * 0.75)) {
      String[] words = msg.split(" ");
      String current = "";
      for (String word : words) {
        if (this.mc.fontRenderer.getStringWidth(current + word + " ") > (this.mc.displayWidth * 0.75)) {
          lines.add(new TextLine(current.trim()));
          current = "";
        }
        current += word + " ";
      }
      if (!current.isEmpty())
        lines.add(new TextLine(current.trim()));
    } else {
      lines.add(new TextLine(msg));
    }
    mc.displayGuiScreen(new GuiScreenInfo(this.mc.currentScreen, lines.toArray(new TextLine[0])));
  }
}
