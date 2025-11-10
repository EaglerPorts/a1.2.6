package dev.colbster937.eaglercraft.storage;

import net.minecraft.client.Minecraft;

public class SaveUtils {
  public static DirStorage i;

  public static void init(Minecraft mc) {
    i = new DirStorage(mc, "saves");
  }
}
