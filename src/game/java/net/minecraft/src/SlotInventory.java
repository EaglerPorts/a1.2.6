package net.minecraft.src;

public class SlotInventory extends Slot {
	private final GuiContainer guiHandler;
	public final int xPos;
	public final int yPos;

	public SlotInventory(GuiContainer var1, IInventory var2, int var3, int var4, int var5) {
		super(var2, var3);
		this.guiHandler = var1;
		this.xPos = var4;
		this.yPos = var5;
	}

	public boolean isAtCursorPos(int var1, int var2) {
		int var3 = (this.guiHandler.width - this.guiHandler.xSize) / 2;
		int var4 = (this.guiHandler.height - this.guiHandler.ySize) / 2;
		var1 -= var3;
		var2 -= var4;
		return var1 >= this.xPos - 1 && var1 < this.xPos + 16 + 1 && var2 >= this.yPos - 1 && var2 < this.yPos + 16 + 1;
	}
}
