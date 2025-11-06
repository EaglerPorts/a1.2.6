package net.minecraft.src;

import dev.colbster937.eaglercraft.rp.TexturePack;

import net.lax1dude.eaglercraft.opengl.ImageData;

public class ColorizerGrass {
	private static final int[] field_6540_a = new int[65536];

	public static int func_4147_a(double var0, double var2) {
		var2 *= var0;
		int var4 = (int)((1.0D - var0) * 255.0D);
		int var5 = (int)((1.0D - var2) * 255.0D);
		return field_6540_a[var5 << 8 | var4];
	}

	static {
		try {
			ImageData var0 = ImageData.loadImageFile(TexturePack.getResourceAsStream("/misc/grasscolor.png")).swapRB();
			var0.getRGB(0, 0, 256, 256, field_6540_a, 0, 256);
		} catch (Exception var1) {
			var1.printStackTrace();
		}

	}
}
