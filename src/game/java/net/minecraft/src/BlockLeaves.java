package net.minecraft.src;

import net.lax1dude.eaglercraft.Random;

public class BlockLeaves extends BlockLeavesBase {
	private int baseIndexInPNG;
	private int field_464_c = 0;

	protected BlockLeaves(int var1, int var2) {
		super(var1, var2, Material.leaves, false);
		this.baseIndexInPNG = var2;
	}

	public int colorMultiplier(IBlockAccess var1, int var2, int var3, int var4) {
		var1.func_4075_a().func_4069_a(var2, var4, 1, 1);
		double var5 = var1.func_4075_a().temperature[0];
		double var7 = var1.func_4075_a().humidity[0];
		return ColorizerFoliage.func_4146_a(var5, var7);
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		if(this == null) {
			this.field_464_c = 0;
			this.func_308_h(var1, var2, var3, var4);
			super.onNeighborBlockChange(var1, var2, var3, var4, var5);
		}
	}

	public void func_6361_f(World var1, int var2, int var3, int var4, int var5) {
		if(var1.getBlockId(var2, var3, var4) == this.blockID) {
			int var6 = var1.getBlockMetadata(var2, var3, var4);
			if(var6 != 0 && var6 == var5 - 1) {
				this.func_308_h(var1, var2, var3, var4);
			}
		}
	}

	public void func_308_h(World var1, int var2, int var3, int var4) {
		if(this == null) {
			if(this.field_464_c++ < 100) {
				int var5 = var1.getBlockMaterial(var2, var3 - 1, var4).func_878_a() ? 16 : 0;
				int var6 = var1.getBlockMetadata(var2, var3, var4);
				if(var6 == 0) {
					var6 = 1;
					var1.setBlockMetadataWithNotify(var2, var3, var4, 1);
				}

				var5 = this.func_6362_g(var1, var2, var3 - 1, var4, var5);
				var5 = this.func_6362_g(var1, var2, var3, var4 - 1, var5);
				var5 = this.func_6362_g(var1, var2, var3, var4 + 1, var5);
				var5 = this.func_6362_g(var1, var2 - 1, var3, var4, var5);
				var5 = this.func_6362_g(var1, var2 + 1, var3, var4, var5);
				int var7 = var5 - 1;
				if(var7 < 10) {
					var7 = 1;
				}

				if(var7 != var6) {
					var1.setBlockMetadataWithNotify(var2, var3, var4, var7);
					this.func_6361_f(var1, var2, var3 - 1, var4, var6);
					this.func_6361_f(var1, var2, var3 + 1, var4, var6);
					this.func_6361_f(var1, var2, var3, var4 - 1, var6);
					this.func_6361_f(var1, var2, var3, var4 + 1, var6);
					this.func_6361_f(var1, var2 - 1, var3, var4, var6);
					this.func_6361_f(var1, var2 + 1, var3, var4, var6);
				}

			}
		}
	}

	private int func_6362_g(World var1, int var2, int var3, int var4, int var5) {
		int var6 = var1.getBlockId(var2, var3, var4);
		if(var6 == Block.wood.blockID) {
			return 16;
		} else {
			if(var6 == this.blockID) {
				int var7 = var1.getBlockMetadata(var2, var3, var4);
				if(var7 != 0 && var7 > var5) {
					return var7;
				}
			}

			return var5;
		}
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		if(this == null) {
			int var6 = var1.getBlockMetadata(var2, var3, var4);
			if(var6 == 0) {
				this.field_464_c = 0;
				this.func_308_h(var1, var2, var3, var4);
			} else if(var6 == 1) {
				this.func_6360_i(var1, var2, var3, var4);
			} else if(var5.nextInt(10) == 0) {
				this.func_308_h(var1, var2, var3, var4);
			}

		}
	}

	private void func_6360_i(World var1, int var2, int var3, int var4) {
		this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
		var1.setBlockWithNotify(var2, var3, var4, 0);
	}

	public int quantityDropped(Random var1) {
		return var1.nextInt(20) == 0 ? 1 : 0;
	}

	public int idDropped(int var1, Random var2) {
		return Block.sapling.blockID;
	}

	public boolean isOpaqueCube() {
		return !this.graphicsLevel;
	}

	public void setGraphicsLevel(boolean var1) {
		this.graphicsLevel = var1;
		this.blockIndexInTexture = this.baseIndexInPNG + (var1 ? 0 : 1);
	}

	public void onEntityWalking(World var1, int var2, int var3, int var4, Entity var5) {
		super.onEntityWalking(var1, var2, var3, var4, var5);
	}
}
