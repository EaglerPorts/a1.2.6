package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet59ComplexEntity extends Packet {
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte[] entityData;
	public NBTTagCompound entityNBT;

	public Packet59ComplexEntity() {
		this.isChunkDataPacket = true;
	}

	public Packet59ComplexEntity(int var1, int var2, int var3, TileEntity var4) {
		this.isChunkDataPacket = true;
		this.xPosition = var1;
		this.yPosition = var2;
		this.zPosition = var3;
		this.entityNBT = new NBTTagCompound();
		var4.writeToNBT(this.entityNBT);

		try {
			this.entityData = CompressedStreamTools.func_1142_a(this.entityNBT);
		} catch (IOException var6) {
			var6.printStackTrace();
		}

	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.xPosition = var1.readInt();
		this.yPosition = var1.readShort();
		this.zPosition = var1.readInt();
		int var2 = var1.readShort() & '\uffff';
		this.entityData = new byte[var2];
		var1.readFully(this.entityData);
		this.entityNBT = CompressedStreamTools.func_1140_a(this.entityData);
	}

	public void writePacketData(DataOutputStream var1) throws IOException {
		var1.writeInt(this.xPosition);
		var1.writeShort(this.yPosition);
		var1.writeInt(this.zPosition);
		var1.writeShort((short)this.entityData.length);
		var1.write(this.entityData);
	}

	public void processPacket(NetHandler var1) {
		var1.handleComplexEntity(this);
	}

	public int getPacketSize() {
		return this.entityData.length + 2 + 10;
	}
}
