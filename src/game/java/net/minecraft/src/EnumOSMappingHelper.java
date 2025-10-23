package net.minecraft.src;

public class EnumOSMappingHelper {
	public static final int[] field_1585_a = new int[EnumOS2.values().length];

	static {
		try {
			field_1585_a[EnumOS2.a.ordinal()] = 1;
		} catch (NoSuchFieldError var4) {
		}

		try {
			field_1585_a[EnumOS2.b.ordinal()] = 2;
		} catch (NoSuchFieldError var3) {
		}

		try {
			field_1585_a[EnumOS2.c.ordinal()] = 3;
		} catch (NoSuchFieldError var2) {
		}

		try {
			field_1585_a[EnumOS2.d.ordinal()] = 4;
		} catch (NoSuchFieldError var1) {
		}

	}
}
