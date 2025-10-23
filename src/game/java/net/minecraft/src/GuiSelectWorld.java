package net.minecraft.src;

import net.lax1dude.eaglercraft.internal.vfs2.VFile2;
import net.minecraft.client.Minecraft;

public class GuiSelectWorld extends GuiScreen {
	protected GuiScreen parentScreen;
	protected String screenTitle = "Select world";
	private boolean selected = false;

	public GuiSelectWorld(GuiScreen var1) {
		this.parentScreen = var1;
	}

	public void initGui() {
		VFile2 var1 = Minecraft.getMinecraftDir();

		for(int var2 = 0; var2 < 5; ++var2) {
			NBTTagCompound var3 = World.func_629_a(var1, "World" + (var2 + 1));
			if(var3 == null) {
				this.controlList.add(new GuiButton(var2, this.width / 2 - 100, this.height / 6 + 24 * var2, "- empty -"));
			} else {
				String var4 = "World " + (var2 + 1);
				long var5 = var3.getLong("SizeOnDisk");
				var4 = var4 + " (" + (float)(var5 / 1024L * 100L / 1024L) / 100.0F + " MB)";
				this.controlList.add(new GuiButton(var2, this.width / 2 - 100, this.height / 6 + 24 * var2, var4));
			}
		}

		this.initGui2();
	}

	protected String getWorldName(int var1) {
		VFile2 var2 = Minecraft.getMinecraftDir();
		return World.func_629_a(var2, "World" + var1) != null ? "World" + var1 : null;
	}

	public void initGui2() {
		this.controlList.add(new GuiButton(5, this.width / 2 - 100, this.height / 6 + 120 + 12, "Delete world..."));
		this.controlList.add(new GuiButton(6, this.width / 2 - 100, this.height / 6 + 168, "Cancel"));
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.enabled) {
			if(var1.id < 5) {
				this.selectWorld(var1.id + 1);
			} else if(var1.id == 5) {
				this.mc.displayGuiScreen(new GuiDeleteWorld(this));
			} else if(var1.id == 6) {
				this.mc.displayGuiScreen(this.parentScreen);
			}

		}
	}

	public void selectWorld(int var1) {
		this.mc.displayGuiScreen((GuiScreen)null);
		if(!this.selected) {
			this.selected = true;
			this.mc.field_6327_b = new PlayerControllerSP(this.mc);
			this.mc.func_6247_b("World" + var1);
			this.mc.displayGuiScreen((GuiScreen)null);
		}
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
		super.drawScreen(var1, var2, var3);
	}
}
