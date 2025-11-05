package dev.colbster937.eaglercraft.rp;

import java.io.InputStream;

import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.opengl.ImageData;

public class DefaultTexturePack extends TexturePack {
  private static final DefaultTexturePack pack = new DefaultTexturePack();

  private final ImageData icon = ImageData.loadImageFile(this.getResourceStream("pack.png"));

  public static DefaultTexturePack getInstance() {
    return pack;
  }

  protected DefaultTexturePack() {
    super("Default", new String[] { "The default look of Minecraft" });
  }

  @Override
  public ImageData getIcon() {
    return this.icon;
  }

  @Override
  public InputStream getResourceStream(String path) {
    return EagRuntime.getResourceStream(path);
  }
}
