package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.lax1dude.eaglercraft.EaglerZLIB;

public class CompressedStreamTools {
	public static NBTTagCompound func_1138_a(InputStream var0) throws IOException {
		DataInputStream var1 = new DataInputStream(EaglerZLIB.newGZIPInputStream(var0));

		NBTTagCompound var2;
		try {
			var2 = func_1141_a(var1);
		} finally {
			var1.close();
		}

		return var2;
	}

	public static void writeGzippedCompoundToOutputStream(NBTTagCompound var0, OutputStream var1) throws IOException {
		DataOutputStream var2 = new DataOutputStream(EaglerZLIB.newGZIPOutputStream(var1));

		try {
			func_1139_a(var0, var2);
		} finally {
			var2.close();
		}

	}

	public static NBTTagCompound func_1140_a(byte[] var0) throws IOException {
		DataInputStream var1 = new DataInputStream(EaglerZLIB.newGZIPInputStream(new ByteArrayInputStream(var0)));

		NBTTagCompound var2;
		try {
			var2 = func_1141_a(var1);
		} finally {
			var1.close();
		}

		return var2;
	}

	public static byte[] func_1142_a(NBTTagCompound var0) throws IOException {
		ByteArrayOutputStream var1 = new ByteArrayOutputStream();
		DataOutputStream var2 = new DataOutputStream(EaglerZLIB.newGZIPOutputStream(var1));

		try {
			func_1139_a(var0, var2);
		} finally {
			var2.close();
		}

		return var1.toByteArray();
	}

	public static NBTTagCompound func_1141_a(DataInput var0) throws IOException {
		NBTBase var1 = NBTBase.readTag(var0);
		if(var1 instanceof NBTTagCompound) {
			return (NBTTagCompound)var1;
		} else {
			throw new IOException("Root tag must be a named compound tag");
		}
	}

	public static void func_1139_a(NBTTagCompound var0, DataOutput var1) throws IOException {
		NBTBase.writeTag(var0, var1);
	}
}
