package dev.colbster937.eaglercraft.gui;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSelectWorld;

public class GuiScreenSelectManageWorld extends GuiSelectWorld {
	public GuiScreenSelectManageWorld(GuiScreen parent) {
		super(parent);
		this.screenTitle = "Manage world";
	}

	@Override
	public void initGui2() {
		this.controlList.add(new GuiButton(6, this.width / 2 - 100, this.height / 6 + 168, "Cancel"));
	}

	@Override
	public void selectWorld(int id) {
		this.mc.displayGuiScreen(new GuiScreenManageWorld(this, id, this.getWorldName(id) != null));
	}

	public void createWorld(int id) {
		super.selectWorld(id);
	}

	public void end() {
		this.mc.displayGuiScreen(this.parentScreen);
	}
	
  public GuiScreen getParentScreen() {
    return this.parentScreen;
  }
}
