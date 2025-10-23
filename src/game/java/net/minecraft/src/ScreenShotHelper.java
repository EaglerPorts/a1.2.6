package net.minecraft.src;

import net.lax1dude.eaglercraft.internal.PlatformApplication;
import net.lax1dude.eaglercraft.internal.vfs2.VFile2;

public class ScreenShotHelper {

	public static String func_4148_a(VFile2 var0, int var1, int var2) {
		return "Saved screenshot as " + PlatformApplication.saveScreenshot();
	}
}
