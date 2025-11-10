package dev.colbster937.eaglercraft.gui;

import dev.colbster937.eaglercraft.storage.SaveUtils;
import dev.colbster937.eaglercraft.utils.ScuffedUtils;
import net.lax1dude.eaglercraft.EagRuntime;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.peyton.java.awt.Color;

public class GuiScreenManageWorld extends GuiScreen {
  private final GuiScreenSelectManageWorld parent;
  private final int world;
  private final String worldName;
  private final boolean exists;

  public GuiScreenManageWorld(GuiScreenSelectManageWorld parent, int world, boolean exists) {
    this.parent = parent;
    this.world = world;
    this.worldName = "World" + this.world;
    this.exists = exists;
  }

  @Override
  public void initGui() {
    if (exists) {
      controlList.add(new GuiButton(0, (width - 200) / 2, height / 3 + 29, "Delete World"));
      controlList.add(new GuiButton(1, (width - 200) / 2, height / 3 + 5, "Export World"));
    } else {
      controlList.add(new GuiButton(2, (width - 200) / 2, height / 3 + 5, "Create New World"));
      controlList.add(new GuiButton(3, (width - 200) / 2, height / 3 + 29, "Import World"));
    }
    controlList.add(new GuiButton(4, (width - 200) / 2, height / 3 + 53, "Cancel"));
  }

  @Override
  public void drawScreen(int mx, int my, float f) {
    drawDefaultBackground();
    drawCenteredString(fontRenderer, "Manage World " + world, width / 2, height / 4, Color.WHITE.getRGB());
    super.drawScreen(mx, my, f);
  }

  @Override
  public void updateScreen() {
    super.updateScreen();
    if (EagRuntime.fileChooserHasResult()) {
      SaveUtils.i._import(this.mc.loadingScreen, this.worldName, EagRuntime.getFileChooserResult().fileData);
      this.mc.displayGuiScreen(this.parent.getParentScreen());
    }
  }

  @Override
  protected void actionPerformed(GuiButton btn) {
    if (btn.enabled) {
      GuiScreen parent = this.parent.getParentScreen();
      if (btn.id == 0) {
        SaveUtils.i.delete(this.mc.loadingScreen, this.worldName);
        this.mc.displayGuiScreen(parent);
      } else if (btn.id == 1) {
        SaveUtils.i.export(this.mc.loadingScreen, this.worldName);
        this.mc.displayGuiScreen(parent);
      } else if (btn.id == 2) {
        this.parent.createWorld(this.world);
      } else if (btn.id == 3) {
        ScuffedUtils.showZipFileChooser();
      } else if (btn.id == 4) {
        this.mc.displayGuiScreen(this.parent);
      }
    }
  }
}
