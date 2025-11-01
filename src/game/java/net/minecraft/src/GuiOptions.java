package net.minecraft.src;

import java.util.ArrayList;

public class GuiOptions extends GuiScreen {
	private GuiScreen parentScreen;
	protected String screenTitle = "Options";
	private GameSettings options;

	public GuiOptions(GuiScreen var1, GameSettings var2) {
		this.parentScreen = var1;
		this.options = var2;
	}

	public void initGui() {
		int i = 0;
		for(int var1 = 0; var1 < this.options.numberOfOptions; ++var1) {
			int var2 = this.options.getOptionControlType(var1);
			GuiButton b;

			if(var2 == 0) {
				b = new GuiSmallButton(var1, this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), this.options.getOptionDisplayString(var1));
			} else {
				b = new GuiSlider(var1, this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), var1, this.options.getOptionDisplayString(var1), this.options.getOptionFloatValue(var1));
			}

			this.controlList.add(b);
			
			++i;
		}

		ArrayList<GuiButton> extraOpts = new ArrayList<>();
		extraOpts.add(new GuiButton(100, this.width / 2 - 100, this.height / 6 + 132 + 12, "Controls..."));
		extraOpts.add(new GuiButton(101, 0, 0, "Texture Packs..."));

		this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, "Done"));

		for (GuiButton extraOpt : extraOpts) {
			GuiButton opt = new GuiSmallButton(extraOpt.id, this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), extraOpt.displayString);
			this.controlList.add(opt);
			++i;
		}
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.enabled) {
			if(var1.id < 100) {
				this.options.setOptionValue(var1.id, 1);
				var1.displayString = this.options.getOptionDisplayString(var1.id);
			}

			if(var1.id == 100) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiControls(this, this.options));
			}

			if(var1.id == 101) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiTexturePacks(this));
			}

			if(var1.id == 200) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(this.parentScreen);
			}

		}
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
		super.drawScreen(var1, var2, var3);
	}
}
