package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet16BlockItemSwitch extends Packet {
	public int unused;
	public int id;

	public Packet16BlockItemSwitch() {
	}

	public Packet16BlockItemSwitch(int var1, int var2) {
		this.unused = var1;
		this.id = var2;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.unused = var1.readInt();
		this.id = var1.readShort();
	}

	public void writePacketData(DataOutputStream var1) throws IOException {
		var1.writeInt(this.unused);
		var1.writeShort(this.id);
	}

	public void processPacket(NetHandler var1) {
		var1.handleBlockItemSwitch(this);
	}

	public int getPacketSize() {
		return 6;
	}
}
