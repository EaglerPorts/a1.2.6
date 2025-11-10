package net.minecraft.src;

import net.lax1dude.eaglercraft.internal.EnumEaglerConnectionState;
import net.lax1dude.eaglercraft.internal.IWebSocketClient;
import net.lax1dude.eaglercraft.internal.PlatformNetworking;
import net.lax1dude.eaglercraft.socket.AddressResolver;
import net.minecraft.client.Minecraft;

public class GuiConnecting extends GuiScreen {
	private NetClientHandler clientHandler;
	private boolean cancelled = false;

	private boolean successful = false;
	private boolean connected = false;
	private String currentAddress;
	private IWebSocketClient webSocket;
	private int timer = 0;

	public GuiConnecting(Minecraft var1, String var2) {
		var1.func_6261_a((World) null);
		this.currentAddress = AddressResolver.resolveURI(var2);
		this.clientHandler = new NetClientHandler(var1);
	}

	public void updateScreen() {
		++timer;
		if (timer > 1) {
			if (this.webSocket == null) {
				this.webSocket = PlatformNetworking.openWebSocket(this.currentAddress);
				if (this.webSocket.getState() == EnumEaglerConnectionState.FAILED
						|| this.webSocket.getState() == EnumEaglerConnectionState.CLOSED) {
					this.mc.displayGuiScreen(new GuiConnectFailed("Failed to connect to the server",
							"Could not open websocket to\"" + this.currentAddress + "\"!"));
				}
			} else {
				if (this.webSocket.getState() == EnumEaglerConnectionState.CONNECTED) {
					if (!this.successful) {
						this.clientHandler.getNetManager().setWebSocket(this.webSocket);
						this.clientHandler.addToSendQueue(new Packet2Handshake(this.mc.field_6320_i.inventory));
						this.successful = true;
						this.connected = true;
					} else {
						this.clientHandler.processReadPackets();
					}
				} else if (this.webSocket.getState() == EnumEaglerConnectionState.FAILED) {
					if (this.webSocket != null) {
						this.webSocket.close();
						this.webSocket = null;
					}
					this.mc.displayGuiScreen(new GuiConnectFailed("Failed to connect to the server", "Connection Refused!"));
				}
			}
			if (timer > 200 && !this.clientHandler.getNetManager().isOpen()) {
				if (this.webSocket != null) {
					this.webSocket.close();
					this.webSocket = null;
				}
				this.mc.displayGuiScreen(new GuiConnectFailed("Failed to connect to the server", "Connection timed out"));
			}
		}

	}

	protected void keyTyped(char var1, int var2) {
	}

	public void initGui() {
		this.controlList.clear();
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
	}

	protected void actionPerformed(GuiButton var1) {
		if (var1.id == 0) {
			this.cancelled = true;
			if (this.clientHandler != null) {
				this.clientHandler.disconnect();
			}

			this.mc.displayGuiScreen(this.mc.menu);
		}

	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		if (this.clientHandler == null) {
			this.drawCenteredString(this.fontRenderer, "Connecting to the server...", this.width / 2, this.height / 2 - 50,
					16777215);
			this.drawCenteredString(this.fontRenderer, "", this.width / 2, this.height / 2 - 10, 16777215);
		} else {
			this.drawCenteredString(this.fontRenderer, "Logging in...", this.width / 2, this.height / 2 - 50, 16777215);
			this.drawCenteredString(this.fontRenderer, this.clientHandler.field_1209_a, this.width / 2, this.height / 2 - 10,
					16777215);
		}

		super.drawScreen(var1, var2, var3);
	}

	static NetClientHandler setNetClientHandler(GuiConnecting var0, NetClientHandler var1) {
		return var0.clientHandler = var1;
	}

	static boolean isCancelled(GuiConnecting var0) {
		return var0.cancelled;
	}

	static NetClientHandler getNetClientHandler(GuiConnecting var0) {
		return var0.clientHandler;
	}
}
