package net.minecraft.src;

import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.profile.EaglerProfile;

public class GuiMultiplayer extends GuiScreen {
	private GuiScreen updateCounter;
	private int parentScreen = 0;
	private String serverAddress = "";
	private String username = "";
	private int focus = 0;

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
		this.username = EaglerProfile.getName();
		((GuiButton)this.controlList.get(0)).enabled = this.serverAddress.length() > 0 && this.username.length() > 0;
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.enabled) {
			if(var1.id == 1) {
				this.mc.displayGuiScreen(this.updateCounter);
			} else if(var1.id == 0) {
				this.mc.gameSettings.field_12259_z = this.serverAddress.replaceAll(":", "_");
				this.mc.gameSettings.saveOptions();
				this.mc.field_6320_i.inventory = this.username;
				EaglerProfile.setName(this.username);
				EaglerProfile.save();
				this.mc.displayGuiScreen(new GuiConnecting(this.mc, this.serverAddress));
			}
		}
	}

	private void pasteIntoActive(String var1, int var2) {
		if(var1 == null) var1 = "";
		String var3 = this.focus == 2 ? this.serverAddress : this.username;
		int var4 = var2 - var3.length();
		if(var4 > var1.length()) var4 = var1.length();
		if(var4 > 0) {
			if(this.focus == 2) this.serverAddress = this.serverAddress + var1.substring(0, var4);
			else if(this.focus == 1) this.username = this.username + var1.substring(0, var4);
		}
	}

	protected void keyTyped(char var1, int var2) {
		if(var1 == 9) {
			if(this.focus == 0) this.focus = 1;
			else this.focus = (this.focus == 1) ? 2 : 1;
		} else if(var1 == 22) {
			this.pasteIntoActive(GuiScreen.getClipboardString(), this.focus == 2 ? 32 : 16);
		} else if(var1 == 13) {
			this.actionPerformed((GuiButton)this.controlList.get(0));
		} else if(var2 == 14) {
			if(this.focus == 2 && this.serverAddress.length() > 0) this.serverAddress = this.serverAddress.substring(0, this.serverAddress.length() - 1);
			else if(this.focus == 1 && this.username.length() > 0) this.username = this.username.substring(0, this.username.length() - 1);
		} else {
			String var3 = " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb";
			if(var3.indexOf(var1) >= 0) {
				if(this.focus == 2 && this.serverAddress.length() < 32) this.serverAddress = this.serverAddress + var1;
				else if(this.focus == 1 && this.username.length() < 16) this.username = this.username + var1;
			}
		}
		((GuiButton)this.controlList.get(0)).enabled = this.serverAddress.length() > 0 && this.username.length() > 0;
	}

	protected void mouseClicked(int var1, int var2, int var3) {
		int var4 = this.width / 2 - 100;
		int var5 = this.height / 4 - 10 + 28;
		short var6 = 200;
		byte var7 = 20;
		int var8 = var5 + 18 + 20;
		boolean var9 = var1 >= var4 && var1 <= var4 + var6 && var2 >= var5 && var2 <= var5 + var7;
		boolean var10 = var1 >= var4 && var1 <= var4 + var6 && var2 >= var8 && var2 <= var8 + var7;
		if(var9) this.focus = 1;
		else if(var10) this.focus = 2;
		else this.focus = 0;
		super.mouseClicked(var1, var2, var3);
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, "Play Multiplayer", this.width / 2, this.height / 4 - 60 + 20 + 15, 16777215);
		// this.drawString(this.fontRenderer, "Alpha Multiplayer is currently not finished, but there", this.width / 2 - 140, this.height / 4 - 60 + 40 + 0, 10526880);
		// this.drawString(this.fontRenderer, "is some buggy early testing going on.", this.width / 2 - 140, this.height / 4 - 60 + 40 + 9, 10526880);

		this.drawString(this.fontRenderer, "Username:", this.width / 2 - 100, this.height / 4 - 60 + 60 + 6, 10526880);
		int var4 = this.width / 2 - 100;
		int var5 = this.height / 4 - 10 + 28;
		short var6 = 200;
		byte var7 = 20;
		this.drawRect(var4 - 1, var5 - 1, var4 + var6 + 1, var5 + var7 + 1, -6250336);
		this.drawRect(var4, var5, var4 + var6, var5 + var7, -16777216);
		this.drawString(this.fontRenderer, this.username + (this.focus == 1 && this.parentScreen / 6 % 2 == 0 ? "_" : ""), var4 + 4, var5 + (var7 - 8) / 2, 14737632);

		this.drawString(this.fontRenderer, "Server IP:", this.width / 2 - 100, this.height / 4 - 60 + 60 + 46, 10526880);
		int var8 = this.width / 2 - 100;
		int var9 = this.height / 4 - 10 + 50 + 18;
		this.drawRect(var8 - 1, var9 - 1, var8 + var6 + 1, var9 + var7 + 1, -6250336);
		this.drawRect(var8, var9, var8 + var6, var9 + var7, -16777216);
		this.drawString(this.fontRenderer, this.serverAddress + (this.focus == 2 && this.parentScreen / 6 % 2 == 0 ? "_" : ""), var8 + 4, var9 + (var7 - 8) / 2, 14737632);

		if (EagRuntime.requireSSL()) {
			this.drawCenteredString(this.fontRenderer, "you are on an https: page!", this.width / 2, this.height / 4 + 83,
					0xccccff);
			this.drawCenteredString(this.fontRenderer, "html5 will only allow wss://", this.width / 2, this.height / 4 + 95,
					0xccccff);
		}

		super.drawScreen(var1, var2, var3);
	}
}
