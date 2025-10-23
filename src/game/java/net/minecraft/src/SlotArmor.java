package net.minecraft.src;

class SlotArmor extends SlotInventory {
	final int field_1124_c;
	final GuiInventory field_1123_d;

	SlotArmor(GuiInventory var1, GuiContainer var2, IInventory var3, int var4, int var5, int var6, int var7) {
		super(var2, var3, var4, var5, var6);
		this.field_1123_d = var1;
		this.field_1124_c = var7;
	}

	public int getSlotStackLimit() {
		return 1;
	}

	public boolean isItemValid(ItemStack var1) {
		if(var1.getItem() instanceof ItemArmor) {
			return ((ItemArmor)var1.getItem()).armorType == this.field_1124_c;
		} else {
			System.out.println(var1.getItem().shiftedIndex + ", " + this.field_1124_c);
			return var1.getItem().shiftedIndex == Block.pumpkin.blockID ? this.field_1124_c == 0 : false;
		}
	}

	public int func_775_c() {
		return 15 + this.field_1124_c * 16;
	}
}
