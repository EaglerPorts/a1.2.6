package net.minecraft.src;

public class GuiMultiplayer extends GuiScreen {
	private GuiScreen updateCounter;
	private int parentScreen = 0;
	private String serverAddress = "";

	public GuiMultiplayer(GuiScreen var1) {
		this.updateCounter = var1;
	}

	public void updateScreen() {
		++this.parentScreen;
	}

	public void initGui() {
		this.controlList.clear();
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Connect"));
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
		this.serverAddress = this.mc.gameSettings.field_12259_z.replaceAll("_", ":");
		((GuiButton)this.controlList.get(0)).enabled = this.serverAddress.length() > 0;
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.enabled) {
			if(var1.id == 1) {
				this.mc.displayGuiScreen(this.updateCounter);
			} else if(var1.id == 0) {
				this.mc.gameSettings.field_12259_z = this.serverAddress.replaceAll(":", "_");
				this.mc.gameSettings.saveOptions();
				String[] var2 = this.serverAddress.split(":");
				this.mc.displayGuiScreen(new GuiConnecting(this.mc, var2[0], var2.length > 1 ? this.func_4067_a(var2[1], 25565) : 25565));
			}

		}
	}

	private int func_4067_a(String var1, int var2) {
		try {
			return Integer.parseInt(var1.trim());
		} catch (Exception var4) {
			return var2;
		}
	}

	protected void keyTyped(char var1, int var2) {
		if(var1 == 22) {
			String var3 = GuiScreen.getClipboardString();
			if(var3 == null) {
				var3 = "";
			}

			int var4 = 32 - this.serverAddress.length();
			if(var4 > var3.length()) {
				var4 = var3.length();
			}

			if(var4 > 0) {
				this.serverAddress = this.serverAddress + var3.substring(0, var4);
			}
		}

		if(var1 == 13) {
			this.actionPerformed((GuiButton)this.controlList.get(0));
		}

		if(var2 == 14 && this.serverAddress.length() > 0) {
			this.serverAddress = this.serverAddress.substring(0, this.serverAddress.length() - 1);
		}

		if(" !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb".indexOf(var1) >= 0 && this.serverAddress.length() < 32) {
			this.serverAddress = this.serverAddress + var1;
		}

		((GuiButton)this.controlList.get(0)).enabled = this.serverAddress.length() > 0;
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, "Play Multiplayer", this.width / 2, this.height / 4 - 60 + 20, 16777215);
		this.drawString(this.fontRenderer, "Minecraft Multiplayer is currently not finished, but there", this.width / 2 - 140, this.height / 4 - 60 + 60 + 0, 10526880);
		this.drawString(this.fontRenderer, "is some buggy early testing going on.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 9, 10526880);
		this.drawString(this.fontRenderer, "Enter the IP of a server to connect to it:", this.width / 2 - 140, this.height / 4 - 60 + 60 + 36, 10526880);
		int var4 = this.width / 2 - 100;
		int var5 = this.height / 4 - 10 + 50 + 18;
		short var6 = 200;
		byte var7 = 20;
		this.drawRect(var4 - 1, var5 - 1, var4 + var6 + 1, var5 + var7 + 1, -6250336);
		this.drawRect(var4, var5, var4 + var6, var5 + var7, -16777216);
		this.drawString(this.fontRenderer, this.serverAddress + (this.parentScreen / 6 % 2 == 0 ? "_" : ""), var4 + 4, var5 + (var7 - 8) / 2, 14737632);
		super.drawScreen(var1, var2, var3);
	}
}
