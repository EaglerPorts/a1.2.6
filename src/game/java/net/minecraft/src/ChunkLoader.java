package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import net.lax1dude.eaglercraft.internal.vfs2.VFile2;

public class ChunkLoader implements IChunkLoader {
	private VFile2 saveDir;
	private boolean createIfNecessary;

	public ChunkLoader(VFile2 var1, boolean var2) {
		this.saveDir = var1;
		this.createIfNecessary = var2;
	}

	private VFile2 chunkFileForXZ(int var1, int var2) {
		String var3 = "c." + Integer.toString(var1, 36) + "." + Integer.toString(var2, 36) + ".dat";
		String var4 = Integer.toString(var1 & 63, 36);
		String var5 = Integer.toString(var2 & 63, 36);
		VFile2 var6 = new VFile2(this.saveDir, var4);
		if(!var6.exists()) {
			if(!this.createIfNecessary) {
				return null;
			}
		}

		var6 = new VFile2(var6, var5);
		if(!var6.exists()) {
			if(!this.createIfNecessary) {
				return null;
			}
		}

		var6 = new VFile2(var6, var3);
		return !var6.exists() && !this.createIfNecessary ? null : var6;
	}

	public Chunk loadChunk(World var1, int var2, int var3) throws IOException {
		VFile2 var4 = this.chunkFileForXZ(var2, var3);
		if(var4 != null && var4.exists()) {
			try {
				InputStream var5 = var4.getInputStream();
				NBTTagCompound var6 = CompressedStreamTools.func_1138_a(var5);
				if(!var6.hasKey("Level")) {
					System.out.println("Chunk file at " + var2 + "," + var3 + " is missing level data, skipping");
					return null;
				}

				if(!var6.getCompoundTag("Level").hasKey("Blocks")) {
					System.out.println("Chunk file at " + var2 + "," + var3 + " is missing block data, skipping");
					return null;
				}

				Chunk var7 = loadChunkIntoWorldFromCompound(var1, var6.getCompoundTag("Level"));
				if(!var7.isAtLocation(var2, var3)) {
					System.out.println("Chunk file at " + var2 + "," + var3 + " is in the wrong location; relocating. (Expected " + var2 + ", " + var3 + ", got " + var7.xPosition + ", " + var7.zPosition + ")");
					var6.setInteger("xPos", var2);
					var6.setInteger("zPos", var3);
					var7 = loadChunkIntoWorldFromCompound(var1, var6.getCompoundTag("Level"));
				}

				return var7;
			} catch (Exception var8) {
				var8.printStackTrace();
			}
		}

		return null;
	}

	public void saveChunk(World var1, Chunk var2) throws IOException {
		var1.func_663_l();
		VFile2 var3 = this.chunkFileForXZ(var2.xPosition, var2.zPosition);
		if(var3.exists()) {
			var1.sizeOnDisk -= var3.length();
		}

		try {
			VFile2 var4 = new VFile2(this.saveDir, "tmp_chunk.dat");
			OutputStream var5 = var4.getOutputStream();
			NBTTagCompound var6 = new NBTTagCompound();
			NBTTagCompound var7 = new NBTTagCompound();
			var6.setTag("Level", var7);
			this.storeChunkInCompound(var2, var1, var7);
			CompressedStreamTools.writeGzippedCompoundToOutputStream(var6, var5);
			var5.close();
			if(var3.exists()) {
				var3.delete();
			}

			var4.renameTo(var3);
			var1.sizeOnDisk += var3.length();
		} catch (Exception var8) {
			var8.printStackTrace();
		}

	}

	public void storeChunkInCompound(Chunk var1, World var2, NBTTagCompound var3) {
		var2.func_663_l();
		var3.setInteger("xPos", var1.xPosition);
		var3.setInteger("zPos", var1.zPosition);
		var3.setLong("LastUpdate", var2.worldTime);
		var3.setByteArray("Blocks", var1.blocks);
		var3.setByteArray("Data", var1.data.data);
		var3.setByteArray("SkyLight", var1.skylightMap.data);
		var3.setByteArray("BlockLight", var1.blocklightMap.data);
		var3.setByteArray("HeightMap", var1.heightMap);
		var3.setBoolean("TerrainPopulated", var1.isTerrainPopulated);
		var1.hasEntities = false;
		NBTTagList var4 = new NBTTagList();

		Iterator var6;
		NBTTagCompound var8;
		for(int var5 = 0; var5 < var1.entities.length; ++var5) {
			var6 = var1.entities[var5].iterator();

			while(var6.hasNext()) {
				Entity var7 = (Entity)var6.next();
				var1.hasEntities = true;
				var8 = new NBTTagCompound();
				if(var7.func_358_c(var8)) {
					var4.setTag(var8);
				}
			}
		}

		var3.setTag("Entities", var4);
		NBTTagList var9 = new NBTTagList();
		var6 = var1.chunkTileEntityMap.values().iterator();

		while(var6.hasNext()) {
			TileEntity var10 = (TileEntity)var6.next();
			var8 = new NBTTagCompound();
			var10.writeToNBT(var8);
			var9.setTag(var8);
		}

		var3.setTag("TileEntities", var9);
	}

	public static Chunk loadChunkIntoWorldFromCompound(World var0, NBTTagCompound var1) {
		int var2 = var1.getInteger("xPos");
		int var3 = var1.getInteger("zPos");
		Chunk var4 = new Chunk(var0, var2, var3);
		var4.blocks = var1.getByteArray("Blocks");
		var4.data = new NibbleArray(var1.getByteArray("Data"));
		var4.skylightMap = new NibbleArray(var1.getByteArray("SkyLight"));
		var4.blocklightMap = new NibbleArray(var1.getByteArray("BlockLight"));
		var4.heightMap = var1.getByteArray("HeightMap");
		var4.isTerrainPopulated = var1.getBoolean("TerrainPopulated");
		if(!var4.data.isValid()) {
			var4.data = new NibbleArray(var4.blocks.length);
		}

		if(var4.heightMap == null || !var4.skylightMap.isValid()) {
			var4.heightMap = new byte[256];
			var4.skylightMap = new NibbleArray(var4.blocks.length);
			var4.func_1024_c();
		}

		if(!var4.blocklightMap.isValid()) {
			var4.blocklightMap = new NibbleArray(var4.blocks.length);
			var4.func_1014_a();
		}

		NBTTagList var5 = var1.getTagList("Entities");
		if(var5 != null) {
			for(int var6 = 0; var6 < var5.tagCount(); ++var6) {
				NBTTagCompound var7 = (NBTTagCompound)var5.tagAt(var6);
				Entity var8 = EntityList.createEntityFromNBT(var7, var0);
				var4.hasEntities = true;
				if(var8 != null) {
					var4.addEntity(var8);
				}
			}
		}

		NBTTagList var10 = var1.getTagList("TileEntities");
		if(var10 != null) {
			for(int var11 = 0; var11 < var10.tagCount(); ++var11) {
				NBTTagCompound var12 = (NBTTagCompound)var10.tagAt(var11);
				TileEntity var9 = TileEntity.createAndLoadEntity(var12);
				if(var9 != null) {
					var4.func_1001_a(var9);
				}
			}
		}

		return var4;
	}

	public void func_814_a() {
	}

	public void saveExtraData() {
	}

	public void saveExtraChunkData(World var1, Chunk var2) throws IOException {
	}
}
