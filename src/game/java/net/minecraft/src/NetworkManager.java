package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.lax1dude.eaglercraft.EaglerOutputStream;
import net.lax1dude.eaglercraft.internal.EnumEaglerConnectionState;
import net.lax1dude.eaglercraft.internal.IWebSocketClient;
import net.lax1dude.eaglercraft.internal.IWebSocketFrame;

public class NetworkManager {
	private boolean isRunning = true;
	private NetHandler netHandler;
	private List<Packet> readPackets = new ArrayList<Packet>();
	private boolean isServerTerminating = false;
	private boolean isTerminating = false;
	private String terminationReason = "";
	private int timeSinceLastRead = 0;
	private int sendQueueByteLength = 0;

	private IWebSocketClient webSocket;

	public NetworkManager(NetHandler var3) {
		this.netHandler = var3;
	}

	public void setWebSocket(IWebSocketClient webSocket) {
		this.webSocket = webSocket;
	}

	private EaglerOutputStream sendBuffer = new EaglerOutputStream();

	public void addToSendQueue(Packet var1) {
		if(!this.isServerTerminating) {
			if(isOpen()) {
				sendBuffer.reset();
				try (DataOutputStream dos = new DataOutputStream(sendBuffer)) {
					Packet.writePacket(var1, dos);
					webSocket.send(sendBuffer.toByteArray());
				} catch(Exception e) {
					this.onNetworkError(e);
				}
			} else {
				this.networkShutdown("Connection closed");
			}
		}
	}

	public void readPacket() {
		IWebSocketFrame frame;
		while((frame = webSocket.getNextBinaryFrame()) != null) {
			byte[] arr = frame.getByteArray();
			if(arr != null) {
				try(ByteArrayInputStream bais = new ByteArrayInputStream(arr); DataInputStream packetStream = new DataInputStream(bais)) {
					Packet pkt = Packet.readPacket(packetStream);
					if(pkt != null) {
						this.readPackets.add(pkt);
					} else {
						this.networkShutdown("End of stream");
					}
				} catch(IOException e) {
					if(!this.isTerminating) {
						this.onNetworkError(e);
					}
				}
			}
		}
	}

	private void onNetworkError(Exception var1) {
		var1.printStackTrace();
		this.networkShutdown("Internal exception: " + var1.toString());
	}

	public void networkShutdown(String var1) {
		if(this.isRunning) {
			this.isTerminating = true;
			this.terminationReason = var1;
			this.isRunning = false;

			try {
				this.webSocket.close();
				this.webSocket = null;
			} catch (Throwable var3) {
			}

		}
	}

	public void processReadPackets() {
		if(this.sendQueueByteLength > 1048576) {
			this.networkShutdown("Send buffer overflow");
		}

		if(this.readPackets.isEmpty()) {
			if(this.timeSinceLastRead++ == 1200) {
				this.networkShutdown("Timed out");
			}
		} else {
			this.timeSinceLastRead = 0;
		}

		int var1 = 100;

		while(!this.readPackets.isEmpty() && var1-- >= 0) {
			Packet var2 = (Packet)this.readPackets.remove(0);
			var2.processPacket(this.netHandler);
		}

		if(this.isTerminating && this.readPackets.isEmpty()) {
			this.netHandler.handleErrorMessage(this.terminationReason);
		}

	}

	public boolean isOpen() {
		return this.webSocket != null && this.webSocket.getState() == EnumEaglerConnectionState.CONNECTED && this.webSocket.isOpen();
	}
}
