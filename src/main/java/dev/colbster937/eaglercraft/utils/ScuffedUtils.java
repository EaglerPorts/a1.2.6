package dev.colbster937.eaglercraft.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.lax1dude.eaglercraft.HString;
import net.lax1dude.eaglercraft.internal.vfs2.VFile2;

public class ScuffedUtils {
  public static File getFileFromVFile(VFile2 vfile) throws IOException {
    File file = new File(vfile.getName());
    FileOutputStream fos = new FileOutputStream(file);
    fos.write(vfile.getAllBytes());
    fos.close();
    return file;
  }

  public static String getFormattedTime(long ticks) {
    long t = (((ticks % 24000) + 24000) % 24000 + 6000) % 24000;
    long h = t / 1000;
    long m = (t % 1000) * 60 / 1000;
    return HString.format("The day is %s at %s", ticks / 24000,
        HString.format("%02d:%02d", h, m));
  }

  public static boolean isCtrlKeyDown() {
    return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
  }

  public static boolean isShiftKeyDown() {
    return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
  }
}
