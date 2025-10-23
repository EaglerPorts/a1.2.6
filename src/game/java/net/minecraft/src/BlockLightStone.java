package net.minecraft.src;

import java.util.Random;

public class BlockLightStone extends Block {
	public BlockLightStone(int var1, int var2, Material var3) {
		super(var1, var2, var3);
	}

	public int idDropped(int var1, Random var2) {
		return Item.lightStoneDust.shiftedIndex;
	}
}
