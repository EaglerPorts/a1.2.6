package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import net.lax1dude.eaglercraft.internal.vfs2.VFile2;

public class World implements IBlockAccess {
	public boolean field_4214_a;
	private List field_1051_z;
	public List loadedEntityList;
	private List field_1024_A;
	private TreeSet scheduledTickTreeSet;
	private Set scheduledTickSet;
	public List loadedTileEntityList;
	public List playerEntities;
	public long worldTime;
	private long field_1019_F;
	public int skylightSubtracted;
	protected int field_9437_g;
	protected int field_9436_h;
	public boolean field_1043_h;
	private long field_1054_E;
	protected int autosavePeriod;
	public int difficultySetting;
	public Random rand;
	public int spawnX;
	public int spawnY;
	public int spawnZ;
	public boolean field_1033_r;
	public final WorldProvider worldProvider;
	protected List worldAccesses;
	private IChunkProvider chunkProvider;
	public VFile2 field_9433_s;
	public VFile2 field_9432_t;
	public long randomSeed;
	private NBTTagCompound nbtCompoundPlayer;
	public long sizeOnDisk;
	public final String field_9431_w;
	public boolean field_9430_x;
	private ArrayList field_9428_I;
	private int field_4204_J;
	static int field_9429_y = 0;
	private Set field_9427_K;
	private int field_9426_L;
	private List field_1012_M;
	public boolean multiplayerWorld;

	public static NBTTagCompound func_629_a(VFile2 var0, String var1) {
		VFile2 var2 = new VFile2(var0, "saves");
		VFile2 var3 = new VFile2(var2, var1);
		VFile2 var4 = new VFile2(var3, "level.dat");
		if(var4.exists()) {
			try {
				NBTTagCompound var5 = CompressedStreamTools.func_1138_a(var4.getInputStream());
				NBTTagCompound var6 = var5.getCompoundTag("Data");
				return var6;
			} catch (Exception var7) {
				var7.printStackTrace();
			}
		}

		return null;
	}

	public static void deleteWorld(VFile2 var0, String var1) {
		VFile2 var2 = new VFile2(var0, "saves");
		VFile2 var3 = new VFile2(var2, var1);
		VFile2 var4 = new VFile2(var3, "level.dat");
		if(var4.exists()) {
			deleteFiles(var3.listFiles());
			var3.delete();
		}
	}

	private static void deleteFiles(VFile2[] var0) {
		for(int var1 = 0; var1 < var0.length; ++var1) {
			var0[var1].delete();
		}

	}

	public WorldChunkManager func_4075_a() {
		return this.worldProvider.worldChunkMgr;
	}

	public World(VFile2 var1, String var2) {
		this(var1, var2, (new Random()).nextLong());
	}

	public World(String var1, WorldProvider var2, long var3) {
		this.field_4214_a = false;
		this.field_1051_z = new ArrayList();
		this.loadedEntityList = new ArrayList();
		this.field_1024_A = new ArrayList();
		this.scheduledTickTreeSet = new TreeSet();
		this.scheduledTickSet = new HashSet();
		this.loadedTileEntityList = new ArrayList();
		this.playerEntities = new ArrayList();
		this.worldTime = 0L;
		this.field_1019_F = 16777215L;
		this.skylightSubtracted = 0;
		this.field_9437_g = (new Random()).nextInt();
		this.field_9436_h = 1013904223;
		this.field_1043_h = false;
		this.field_1054_E = System.currentTimeMillis();
		this.autosavePeriod = 40;
		this.rand = new Random();
		this.field_1033_r = false;
		this.worldAccesses = new ArrayList();
		this.randomSeed = 0L;
		this.sizeOnDisk = 0L;
		this.field_9428_I = new ArrayList();
		this.field_4204_J = 0;
		this.field_9427_K = new HashSet();
		this.field_9426_L = this.rand.nextInt(12000);
		this.field_1012_M = new ArrayList();
		this.multiplayerWorld = false;
		this.field_9431_w = var1;
		this.randomSeed = var3;
		this.worldProvider = var2;
		var2.registerWorld(this);
		this.chunkProvider = this.func_4081_a(this.field_9432_t);
		this.calculateInitialSkylight();
	}

	public World(World var1, WorldProvider var2) {
		this.field_4214_a = false;
		this.field_1051_z = new ArrayList();
		this.loadedEntityList = new ArrayList();
		this.field_1024_A = new ArrayList();
		this.scheduledTickTreeSet = new TreeSet();
		this.scheduledTickSet = new HashSet();
		this.loadedTileEntityList = new ArrayList();
		this.playerEntities = new ArrayList();
		this.worldTime = 0L;
		this.field_1019_F = 16777215L;
		this.skylightSubtracted = 0;
		this.field_9437_g = (new Random()).nextInt();
		this.field_9436_h = 1013904223;
		this.field_1043_h = false;
		this.field_1054_E = System.currentTimeMillis();
		this.autosavePeriod = 40;
		this.rand = new Random();
		this.field_1033_r = false;
		this.worldAccesses = new ArrayList();
		this.randomSeed = 0L;
		this.sizeOnDisk = 0L;
		this.field_9428_I = new ArrayList();
		this.field_4204_J = 0;
		this.field_9427_K = new HashSet();
		this.field_9426_L = this.rand.nextInt(12000);
		this.field_1012_M = new ArrayList();
		this.multiplayerWorld = false;
		this.field_1054_E = var1.field_1054_E;
		this.field_9433_s = var1.field_9433_s;
		this.field_9432_t = var1.field_9432_t;
		this.field_9431_w = var1.field_9431_w;
		this.randomSeed = var1.randomSeed;
		this.worldTime = var1.worldTime;
		this.spawnX = var1.spawnX;
		this.spawnY = var1.spawnY;
		this.spawnZ = var1.spawnZ;
		this.sizeOnDisk = var1.sizeOnDisk;
		this.worldProvider = var2;
		var2.registerWorld(this);
		this.chunkProvider = this.func_4081_a(this.field_9432_t);
		this.calculateInitialSkylight();
	}

	public World(VFile2 var1, String var2, long var3) {
		this(var1, var2, var3, (WorldProvider)null);
	}

	public World(VFile2 var1, String var2, long var3, WorldProvider var5) {
		this.field_4214_a = false;
		this.field_1051_z = new ArrayList();
		this.loadedEntityList = new ArrayList();
		this.field_1024_A = new ArrayList();
		this.scheduledTickTreeSet = new TreeSet();
		this.scheduledTickSet = new HashSet();
		this.loadedTileEntityList = new ArrayList();
		this.playerEntities = new ArrayList();
		this.worldTime = 0L;
		this.field_1019_F = 16777215L;
		this.skylightSubtracted = 0;
		this.field_9437_g = (new Random()).nextInt();
		this.field_9436_h = 1013904223;
		this.field_1043_h = false;
		this.field_1054_E = System.currentTimeMillis();
		this.autosavePeriod = 40;
		this.rand = new Random();
		this.field_1033_r = false;
		this.worldAccesses = new ArrayList();
		this.randomSeed = 0L;
		this.sizeOnDisk = 0L;
		this.field_9428_I = new ArrayList();
		this.field_4204_J = 0;
		this.field_9427_K = new HashSet();
		this.field_9426_L = this.rand.nextInt(12000);
		this.field_1012_M = new ArrayList();
		this.multiplayerWorld = false;
		this.field_9433_s = var1;
		this.field_9431_w = var2;
		this.field_9432_t = new VFile2(var1, var2);

		try {
			VFile2 var6 = new VFile2(this.field_9432_t, "session.lock");
			DataOutputStream var7 = new DataOutputStream(var6.getOutputStream());

			try {
				var7.writeLong(this.field_1054_E);
			} finally {
				var7.close();
			}
		} catch (IOException var16) {
			var16.printStackTrace();
			throw new RuntimeException("Failed to check session lock, aborting");
		}

		Object var17 = new WorldProvider();
		VFile2 var18 = new VFile2(this.field_9432_t, "level.dat");
		this.field_1033_r = !var18.exists();
		if(var18.exists()) {
			try {
				NBTTagCompound var8 = CompressedStreamTools.func_1138_a(var18.getInputStream());
				NBTTagCompound var9 = var8.getCompoundTag("Data");
				this.randomSeed = var9.getLong("RandomSeed");
				this.spawnX = var9.getInteger("SpawnX");
				this.spawnY = var9.getInteger("SpawnY");
				this.spawnZ = var9.getInteger("SpawnZ");
				this.worldTime = var9.getLong("Time");
				this.sizeOnDisk = var9.getLong("SizeOnDisk");
				if(var9.hasKey("Player")) {
					this.nbtCompoundPlayer = var9.getCompoundTag("Player");
					int var10 = this.nbtCompoundPlayer.getInteger("Dimension");
					if(var10 == -1) {
						var17 = new WorldProviderHell();
					}
				}
			} catch (Exception var14) {
				var14.printStackTrace();
			}
		}

		if(var5 != null) {
			var17 = var5;
		}

		boolean var19 = false;
		if(this.randomSeed == 0L) {
			this.randomSeed = var3;
			var19 = true;
		}

		this.worldProvider = (WorldProvider)var17;
		this.worldProvider.registerWorld(this);
		this.chunkProvider = this.func_4081_a(this.field_9432_t);
		if(var19) {
			this.field_9430_x = true;
			this.spawnX = 0;
			this.spawnY = 64;

			for(this.spawnZ = 0; !this.worldProvider.canCoordinateBeSpawn(this.spawnX, this.spawnZ); this.spawnZ += this.rand.nextInt(64) - this.rand.nextInt(64)) {
				this.spawnX += this.rand.nextInt(64) - this.rand.nextInt(64);
			}

			this.field_9430_x = false;
		}

		this.calculateInitialSkylight();
	}

	protected IChunkProvider func_4081_a(VFile2 var1) {
		return new ChunkProviderLoadOrGenerate(this, this.worldProvider.getChunkLoader(var1), this.worldProvider.getChunkProvider());
	}

	public void func_4076_b() {
		if(this.spawnY <= 0) {
			this.spawnY = 64;
		}

		while(this.func_614_g(this.spawnX, this.spawnZ) == 0) {
			this.spawnX += this.rand.nextInt(8) - this.rand.nextInt(8);
			this.spawnZ += this.rand.nextInt(8) - this.rand.nextInt(8);
		}

	}

	public int func_614_g(int var1, int var2) {
		int var3;
		for(var3 = 63; this.getBlockId(var1, var3 + 1, var2) != 0; ++var3) {
		}

		return this.getBlockId(var1, var3, var2);
	}

	public void func_6464_c() {
	}

	public void func_608_a(EntityPlayer var1) {
		try {
			if(this.nbtCompoundPlayer != null) {
				var1.readFromNBT(this.nbtCompoundPlayer);
				this.nbtCompoundPlayer = null;
			}

			this.entityJoinedWorld(var1);
		} catch (Exception var3) {
			var3.printStackTrace();
		}

	}

	public void saveWorld(boolean var1, IProgressUpdate var2) {
		if(this.chunkProvider.func_536_b()) {
			if(var2 != null) {
				var2.func_594_b("Saving level");
			}

			this.saveLevel();
			if(var2 != null) {
				var2.displayLoadingString("Saving chunks");
			}

			this.chunkProvider.saveChunks(var1, var2);
		}
	}

	private void saveLevel() {
		this.func_663_l();
		NBTTagCompound var1 = new NBTTagCompound();
		var1.setLong("RandomSeed", this.randomSeed);
		var1.setInteger("SpawnX", this.spawnX);
		var1.setInteger("SpawnY", this.spawnY);
		var1.setInteger("SpawnZ", this.spawnZ);
		var1.setLong("Time", this.worldTime);
		var1.setLong("SizeOnDisk", this.sizeOnDisk);
		var1.setLong("LastPlayed", System.currentTimeMillis());
		EntityPlayer var2 = null;
		if(this.playerEntities.size() > 0) {
			var2 = (EntityPlayer)this.playerEntities.get(0);
		}

		NBTTagCompound var3;
		if(var2 != null) {
			var3 = new NBTTagCompound();
			var2.writeToNBT(var3);
			var1.setCompoundTag("Player", var3);
		}

		var3 = new NBTTagCompound();
		var3.setTag("Data", var1);

		try {
			VFile2 var4 = new VFile2(this.field_9432_t, "level.dat_new");
			VFile2 var5 = new VFile2(this.field_9432_t, "level.dat_old");
			VFile2 var6 = new VFile2(this.field_9432_t, "level.dat");
			CompressedStreamTools.writeGzippedCompoundToOutputStream(var3, var4.getOutputStream());
			if(var5.exists()) {
				var5.delete();
			}

			var6.renameTo(var5);
			if(var6.exists()) {
				var6.delete();
			}

			var4.renameTo(var6);
			if(var4.exists()) {
				var4.delete();
			}
		} catch (Exception var7) {
			var7.printStackTrace();
		}

	}

	public boolean func_650_a(int var1) {
		if(!this.chunkProvider.func_536_b()) {
			return true;
		} else {
			if(var1 == 0) {
				this.saveLevel();
			}

			return this.chunkProvider.saveChunks(false, (IProgressUpdate)null);
		}
	}

	public int getBlockId(int var1, int var2, int var3) {
		return var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000 ? (var2 < 0 ? 0 : (var2 >= 128 ? 0 : this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4).getBlockID(var1 & 15, var2, var3 & 15))) : 0;
	}

	public boolean blockExists(int var1, int var2, int var3) {
		return var2 >= 0 && var2 < 128 ? this.chunkExists(var1 >> 4, var3 >> 4) : false;
	}

	public boolean checkChunksExist(int var1, int var2, int var3, int var4, int var5, int var6) {
		if(var5 >= 0 && var2 < 128) {
			var1 >>= 4;
			var2 >>= 4;
			var3 >>= 4;
			var4 >>= 4;
			var5 >>= 4;
			var6 >>= 4;

			for(int var7 = var1; var7 <= var4; ++var7) {
				for(int var8 = var3; var8 <= var6; ++var8) {
					if(!this.chunkExists(var7, var8)) {
						return false;
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}

	private boolean chunkExists(int var1, int var2) {
		return this.chunkProvider.chunkExists(var1, var2);
	}

	public Chunk getChunkFromBlockCoords(int var1, int var2) {
		return this.getChunkFromChunkCoords(var1 >> 4, var2 >> 4);
	}

	public Chunk getChunkFromChunkCoords(int var1, int var2) {
		return this.chunkProvider.provideChunk(var1, var2);
	}

	public boolean setBlockAndMetadata(int var1, int var2, int var3, int var4, int var5) {
		if(var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
			if(var2 < 0) {
				return false;
			} else if(var2 >= 128) {
				return false;
			} else {
				Chunk var6 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
				return var6.setBlockIDWithMetadata(var1 & 15, var2, var3 & 15, var4, var5);
			}
		} else {
			return false;
		}
	}

	public boolean setBlock(int var1, int var2, int var3, int var4) {
		if(var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
			if(var2 < 0) {
				return false;
			} else if(var2 >= 128) {
				return false;
			} else {
				Chunk var5 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
				return var5.setBlockID(var1 & 15, var2, var3 & 15, var4);
			}
		} else {
			return false;
		}
	}

	public Material getBlockMaterial(int var1, int var2, int var3) {
		int var4 = this.getBlockId(var1, var2, var3);
		return var4 == 0 ? Material.air : Block.blocksList[var4].blockMaterial;
	}

	public int getBlockMetadata(int var1, int var2, int var3) {
		if(var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
			if(var2 < 0) {
				return 0;
			} else if(var2 >= 128) {
				return 0;
			} else {
				Chunk var4 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
				var1 &= 15;
				var3 &= 15;
				return var4.getBlockMetadata(var1, var2, var3);
			}
		} else {
			return 0;
		}
	}

	public void setBlockMetadataWithNotify(int var1, int var2, int var3, int var4) {
		if(this.setBlockMetadata(var1, var2, var3, var4)) {
			this.notifyBlockChange(var1, var2, var3, this.getBlockId(var1, var2, var3));
		}

	}

	public boolean setBlockMetadata(int var1, int var2, int var3, int var4) {
		if(var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
			if(var2 < 0) {
				return false;
			} else if(var2 >= 128) {
				return false;
			} else {
				Chunk var5 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
				var1 &= 15;
				var3 &= 15;
				var5.setBlockMetadata(var1, var2, var3, var4);
				return true;
			}
		} else {
			return false;
		}
	}

	public boolean setBlockWithNotify(int var1, int var2, int var3, int var4) {
		if(this.setBlock(var1, var2, var3, var4)) {
			this.notifyBlockChange(var1, var2, var3, var4);
			return true;
		} else {
			return false;
		}
	}

	public boolean setBlockAndMetadataWithNotify(int var1, int var2, int var3, int var4, int var5) {
		if(this.setBlockAndMetadata(var1, var2, var3, var4, var5)) {
			this.notifyBlockChange(var1, var2, var3, var4);
			return true;
		} else {
			return false;
		}
	}

	public void func_665_h(int var1, int var2, int var3) {
		for(int var4 = 0; var4 < this.worldAccesses.size(); ++var4) {
			((IWorldAccess)this.worldAccesses.get(var4)).func_934_a(var1, var2, var3);
		}

	}

	protected void notifyBlockChange(int var1, int var2, int var3, int var4) {
		this.func_665_h(var1, var2, var3);
		this.notifyBlocksOfNeighborChange(var1, var2, var3, var4);
	}

	public void func_680_f(int var1, int var2, int var3, int var4) {
		if(var3 > var4) {
			int var5 = var4;
			var4 = var3;
			var3 = var5;
		}

		this.func_701_b(var1, var3, var2, var1, var4, var2);
	}

	public void func_701_b(int var1, int var2, int var3, int var4, int var5, int var6) {
		for(int var7 = 0; var7 < this.worldAccesses.size(); ++var7) {
			((IWorldAccess)this.worldAccesses.get(var7)).func_937_b(var1, var2, var3, var4, var5, var6);
		}

	}

	public void notifyBlocksOfNeighborChange(int var1, int var2, int var3, int var4) {
		this.notifyBlockOfNeighborChange(var1 - 1, var2, var3, var4);
		this.notifyBlockOfNeighborChange(var1 + 1, var2, var3, var4);
		this.notifyBlockOfNeighborChange(var1, var2 - 1, var3, var4);
		this.notifyBlockOfNeighborChange(var1, var2 + 1, var3, var4);
		this.notifyBlockOfNeighborChange(var1, var2, var3 - 1, var4);
		this.notifyBlockOfNeighborChange(var1, var2, var3 + 1, var4);
	}

	private void notifyBlockOfNeighborChange(int var1, int var2, int var3, int var4) {
		if(!this.field_1043_h && !this.multiplayerWorld) {
			Block var5 = Block.blocksList[this.getBlockId(var1, var2, var3)];
			if(var5 != null) {
				var5.onNeighborBlockChange(this, var1, var2, var3, var4);
			}

		}
	}

	public boolean canBlockSeeTheSky(int var1, int var2, int var3) {
		return this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4).canBlockSeeTheSky(var1 & 15, var2, var3 & 15);
	}

	public int getBlockLightValue(int var1, int var2, int var3) {
		return this.getBlockLightValue(var1, var2, var3, true);
	}

	public int getBlockLightValue(int var1, int var2, int var3, boolean var4) {
		if(var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
			int var5;
			if(var4) {
				var5 = this.getBlockId(var1, var2, var3);
				if(var5 == Block.stairSingle.blockID || var5 == Block.tilledField.blockID) {
					int var6 = this.getBlockLightValue(var1, var2 + 1, var3, false);
					int var7 = this.getBlockLightValue(var1 + 1, var2, var3, false);
					int var8 = this.getBlockLightValue(var1 - 1, var2, var3, false);
					int var9 = this.getBlockLightValue(var1, var2, var3 + 1, false);
					int var10 = this.getBlockLightValue(var1, var2, var3 - 1, false);
					if(var7 > var6) {
						var6 = var7;
					}

					if(var8 > var6) {
						var6 = var8;
					}

					if(var9 > var6) {
						var6 = var9;
					}

					if(var10 > var6) {
						var6 = var10;
					}

					return var6;
				}
			}

			if(var2 < 0) {
				return 0;
			} else if(var2 >= 128) {
				var5 = 15 - this.skylightSubtracted;
				if(var5 < 0) {
					var5 = 0;
				}

				return var5;
			} else {
				Chunk var11 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
				var1 &= 15;
				var3 &= 15;
				return var11.getBlockLightValue(var1, var2, var3, this.skylightSubtracted);
			}
		} else {
			return 15;
		}
	}

	public boolean canExistingBlockSeeTheSky(int var1, int var2, int var3) {
		if(var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
			if(var2 < 0) {
				return false;
			} else if(var2 >= 128) {
				return true;
			} else if(!this.chunkExists(var1 >> 4, var3 >> 4)) {
				return false;
			} else {
				Chunk var4 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
				var1 &= 15;
				var3 &= 15;
				return var4.canBlockSeeTheSky(var1, var2, var3);
			}
		} else {
			return false;
		}
	}

	public int getHeightValue(int var1, int var2) {
		if(var1 >= -32000000 && var2 >= -32000000 && var1 < 32000000 && var2 <= 32000000) {
			if(!this.chunkExists(var1 >> 4, var2 >> 4)) {
				return 0;
			} else {
				Chunk var3 = this.getChunkFromChunkCoords(var1 >> 4, var2 >> 4);
				return var3.getHeightValue(var1 & 15, var2 & 15);
			}
		} else {
			return 0;
		}
	}

	public void neighborLightPropagationChanged(EnumSkyBlock var1, int var2, int var3, int var4, int var5) {
		if(!this.worldProvider.field_6478_e || var1 != EnumSkyBlock.Sky) {
			if(this.blockExists(var2, var3, var4)) {
				if(var1 == EnumSkyBlock.Sky) {
					if(this.canExistingBlockSeeTheSky(var2, var3, var4)) {
						var5 = 15;
					}
				} else if(var1 == EnumSkyBlock.Block) {
					int var6 = this.getBlockId(var2, var3, var4);
					if(Block.lightValue[var6] > var5) {
						var5 = Block.lightValue[var6];
					}
				}

				if(this.getSavedLightValue(var1, var2, var3, var4) != var5) {
					this.func_616_a(var1, var2, var3, var4, var2, var3, var4);
				}

			}
		}
	}

	public int getSavedLightValue(EnumSkyBlock var1, int var2, int var3, int var4) {
		if(var3 >= 0 && var3 < 128 && var2 >= -32000000 && var4 >= -32000000 && var2 < 32000000 && var4 <= 32000000) {
			int var5 = var2 >> 4;
			int var6 = var4 >> 4;
			if(!this.chunkExists(var5, var6)) {
				return 0;
			} else {
				Chunk var7 = this.getChunkFromChunkCoords(var5, var6);
				return var7.getSavedLightValue(var1, var2 & 15, var3, var4 & 15);
			}
		} else {
			return var1.field_1722_c;
		}
	}

	public void setLightValue(EnumSkyBlock var1, int var2, int var3, int var4, int var5) {
		if(var2 >= -32000000 && var4 >= -32000000 && var2 < 32000000 && var4 <= 32000000) {
			if(var3 >= 0) {
				if(var3 < 128) {
					if(this.chunkExists(var2 >> 4, var4 >> 4)) {
						Chunk var6 = this.getChunkFromChunkCoords(var2 >> 4, var4 >> 4);
						var6.setLightValue(var1, var2 & 15, var3, var4 & 15, var5);

						for(int var7 = 0; var7 < this.worldAccesses.size(); ++var7) {
							((IWorldAccess)this.worldAccesses.get(var7)).func_934_a(var2, var3, var4);
						}

					}
				}
			}
		}
	}

	public float getLightBrightness(int var1, int var2, int var3) {
		return this.worldProvider.lightBrightnessTable[this.getBlockLightValue(var1, var2, var3)];
	}

	public boolean isDaytime() {
		return this.skylightSubtracted < 4;
	}

	public MovingObjectPosition rayTraceBlocks(Vec3D var1, Vec3D var2) {
		return this.rayTraceBlocks(var1, var2, false);
	}

	public MovingObjectPosition rayTraceBlocks(Vec3D var1, Vec3D var2, boolean var3) {
		if(!Double.isNaN(var1.xCoord) && !Double.isNaN(var1.yCoord) && !Double.isNaN(var1.zCoord)) {
			if(!Double.isNaN(var2.xCoord) && !Double.isNaN(var2.yCoord) && !Double.isNaN(var2.zCoord)) {
				int var4 = MathHelper.floor_double(var2.xCoord);
				int var5 = MathHelper.floor_double(var2.yCoord);
				int var6 = MathHelper.floor_double(var2.zCoord);
				int var7 = MathHelper.floor_double(var1.xCoord);
				int var8 = MathHelper.floor_double(var1.yCoord);
				int var9 = MathHelper.floor_double(var1.zCoord);
				int var10 = 200;

				while(var10-- >= 0) {
					if(Double.isNaN(var1.xCoord) || Double.isNaN(var1.yCoord) || Double.isNaN(var1.zCoord)) {
						return null;
					}

					if(var7 == var4 && var8 == var5 && var9 == var6) {
						return null;
					}

					double var11 = 999.0D;
					double var13 = 999.0D;
					double var15 = 999.0D;
					if(var4 > var7) {
						var11 = (double)var7 + 1.0D;
					}

					if(var4 < var7) {
						var11 = (double)var7 + 0.0D;
					}

					if(var5 > var8) {
						var13 = (double)var8 + 1.0D;
					}

					if(var5 < var8) {
						var13 = (double)var8 + 0.0D;
					}

					if(var6 > var9) {
						var15 = (double)var9 + 1.0D;
					}

					if(var6 < var9) {
						var15 = (double)var9 + 0.0D;
					}

					double var17 = 999.0D;
					double var19 = 999.0D;
					double var21 = 999.0D;
					double var23 = var2.xCoord - var1.xCoord;
					double var25 = var2.yCoord - var1.yCoord;
					double var27 = var2.zCoord - var1.zCoord;
					if(var11 != 999.0D) {
						var17 = (var11 - var1.xCoord) / var23;
					}

					if(var13 != 999.0D) {
						var19 = (var13 - var1.yCoord) / var25;
					}

					if(var15 != 999.0D) {
						var21 = (var15 - var1.zCoord) / var27;
					}

					boolean var29 = false;
					byte var35;
					if(var17 < var19 && var17 < var21) {
						if(var4 > var7) {
							var35 = 4;
						} else {
							var35 = 5;
						}

						var1.xCoord = var11;
						var1.yCoord += var25 * var17;
						var1.zCoord += var27 * var17;
					} else if(var19 < var21) {
						if(var5 > var8) {
							var35 = 0;
						} else {
							var35 = 1;
						}

						var1.xCoord += var23 * var19;
						var1.yCoord = var13;
						var1.zCoord += var27 * var19;
					} else {
						if(var6 > var9) {
							var35 = 2;
						} else {
							var35 = 3;
						}

						var1.xCoord += var23 * var21;
						var1.yCoord += var25 * var21;
						var1.zCoord = var15;
					}

					Vec3D var30 = Vec3D.createVector(var1.xCoord, var1.yCoord, var1.zCoord);
					var7 = (int)(var30.xCoord = (double)MathHelper.floor_double(var1.xCoord));
					if(var35 == 5) {
						--var7;
						++var30.xCoord;
					}

					var8 = (int)(var30.yCoord = (double)MathHelper.floor_double(var1.yCoord));
					if(var35 == 1) {
						--var8;
						++var30.yCoord;
					}

					var9 = (int)(var30.zCoord = (double)MathHelper.floor_double(var1.zCoord));
					if(var35 == 3) {
						--var9;
						++var30.zCoord;
					}

					int var31 = this.getBlockId(var7, var8, var9);
					int var32 = this.getBlockMetadata(var7, var8, var9);
					Block var33 = Block.blocksList[var31];
					if(var31 > 0 && var33.canCollideCheck(var32, var3)) {
						MovingObjectPosition var34 = var33.collisionRayTrace(this, var7, var8, var9, var1, var2);
						if(var34 != null) {
							return var34;
						}
					}
				}

				return null;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public void playSoundAtEntity(Entity var1, String var2, float var3, float var4) {
		for(int var5 = 0; var5 < this.worldAccesses.size(); ++var5) {
			((IWorldAccess)this.worldAccesses.get(var5)).playSound(var2, var1.posX, var1.posY - (double)var1.yOffset, var1.posZ, var3, var4);
		}

	}

	public void playSoundEffect(double var1, double var3, double var5, String var7, float var8, float var9) {
		for(int var10 = 0; var10 < this.worldAccesses.size(); ++var10) {
			((IWorldAccess)this.worldAccesses.get(var10)).playSound(var7, var1, var3, var5, var8, var9);
		}

	}

	public void playRecord(String var1, int var2, int var3, int var4) {
		for(int var5 = 0; var5 < this.worldAccesses.size(); ++var5) {
			((IWorldAccess)this.worldAccesses.get(var5)).playRecord(var1, var2, var3, var4);
		}

	}

	public void spawnParticle(String var1, double var2, double var4, double var6, double var8, double var10, double var12) {
		for(int var14 = 0; var14 < this.worldAccesses.size(); ++var14) {
			((IWorldAccess)this.worldAccesses.get(var14)).spawnParticle(var1, var2, var4, var6, var8, var10, var12);
		}

	}

	public boolean entityJoinedWorld(Entity var1) {
		int var2 = MathHelper.floor_double(var1.posX / 16.0D);
		int var3 = MathHelper.floor_double(var1.posZ / 16.0D);
		boolean var4 = false;
		if(var1 instanceof EntityPlayer) {
			var4 = true;
		}

		if(!var4 && !this.chunkExists(var2, var3)) {
			return false;
		} else {
			if(var1 instanceof EntityPlayer) {
				this.playerEntities.add((EntityPlayer)var1);
				System.out.println("Player count: " + this.playerEntities.size());
			}

			this.getChunkFromChunkCoords(var2, var3).addEntity(var1);
			this.loadedEntityList.add(var1);
			this.obtainEntitySkin(var1);
			return true;
		}
	}

	protected void obtainEntitySkin(Entity var1) {
		for(int var2 = 0; var2 < this.worldAccesses.size(); ++var2) {
			((IWorldAccess)this.worldAccesses.get(var2)).obtainEntitySkin(var1);
		}

	}

	protected void releaseEntitySkin(Entity var1) {
		for(int var2 = 0; var2 < this.worldAccesses.size(); ++var2) {
			((IWorldAccess)this.worldAccesses.get(var2)).releaseEntitySkin(var1);
		}

	}

	public void setEntityDead(Entity var1) {
		var1.setEntityDead();
		if(var1 instanceof EntityPlayer) {
			this.playerEntities.remove((EntityPlayer)var1);
		}

	}

	public void addWorldAccess(IWorldAccess var1) {
		this.worldAccesses.add(var1);
	}

	public void removeWorldAccess(IWorldAccess var1) {
		this.worldAccesses.remove(var1);
	}

	public List getCollidingBoundingBoxes(Entity var1, AxisAlignedBB var2) {
		this.field_9428_I.clear();
		int var3 = MathHelper.floor_double(var2.minX);
		int var4 = MathHelper.floor_double(var2.maxX + 1.0D);
		int var5 = MathHelper.floor_double(var2.minY);
		int var6 = MathHelper.floor_double(var2.maxY + 1.0D);
		int var7 = MathHelper.floor_double(var2.minZ);
		int var8 = MathHelper.floor_double(var2.maxZ + 1.0D);

		for(int var9 = var3; var9 < var4; ++var9) {
			for(int var10 = var7; var10 < var8; ++var10) {
				if(this.blockExists(var9, 64, var10)) {
					for(int var11 = var5 - 1; var11 < var6; ++var11) {
						Block var12 = Block.blocksList[this.getBlockId(var9, var11, var10)];
						if(var12 != null) {
							var12.getCollidingBoundingBoxes(this, var9, var11, var10, var2, this.field_9428_I);
						}
					}
				}
			}
		}

		double var14 = 0.25D;
		List var15 = this.getEntitiesWithinAABBExcludingEntity(var1, var2.expands(var14, var14, var14));

		for(int var16 = 0; var16 < var15.size(); ++var16) {
			AxisAlignedBB var13 = ((Entity)var15.get(var16)).func_372_f_();
			if(var13 != null && var13.intersectsWith(var2)) {
				this.field_9428_I.add(var13);
			}

			var13 = var1.func_383_b_((Entity)var15.get(var16));
			if(var13 != null && var13.intersectsWith(var2)) {
				this.field_9428_I.add(var13);
			}
		}

		return this.field_9428_I;
	}

	public int calculateSkylightSubtracted(float var1) {
		float var2 = this.getCelestialAngle(var1);
		float var3 = 1.0F - (MathHelper.cos(var2 * (float)Math.PI * 2.0F) * 2.0F + 0.5F);
		if(var3 < 0.0F) {
			var3 = 0.0F;
		}

		if(var3 > 1.0F) {
			var3 = 1.0F;
		}

		return (int)(var3 * 11.0F);
	}

	public Vec3D func_4079_a(Entity var1, float var2) {
		float var3 = this.getCelestialAngle(var2);
		float var4 = MathHelper.cos(var3 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;
		if(var4 < 0.0F) {
			var4 = 0.0F;
		}

		if(var4 > 1.0F) {
			var4 = 1.0F;
		}

		int var5 = MathHelper.floor_double(var1.posX);
		int var6 = MathHelper.floor_double(var1.posZ);
		float var7 = (float)this.func_4075_a().func_4072_b(var5, var6);
		int var8 = this.func_4075_a().func_4073_a(var5, var6).getSkyColorByTemp(var7);
		float var9 = (float)(var8 >> 16 & 255) / 255.0F;
		float var10 = (float)(var8 >> 8 & 255) / 255.0F;
		float var11 = (float)(var8 & 255) / 255.0F;
		var9 *= var4;
		var10 *= var4;
		var11 *= var4;
		return Vec3D.createVector((double)var9, (double)var10, (double)var11);
	}

	public float getCelestialAngle(float var1) {
		return this.worldProvider.calculateCelestialAngle(this.worldTime, var1);
	}

	public Vec3D func_628_d(float var1) {
		float var2 = this.getCelestialAngle(var1);
		float var3 = MathHelper.cos(var2 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;
		if(var3 < 0.0F) {
			var3 = 0.0F;
		}

		if(var3 > 1.0F) {
			var3 = 1.0F;
		}

		float var4 = (float)(this.field_1019_F >> 16 & 255L) / 255.0F;
		float var5 = (float)(this.field_1019_F >> 8 & 255L) / 255.0F;
		float var6 = (float)(this.field_1019_F & 255L) / 255.0F;
		var4 *= var3 * 0.9F + 0.1F;
		var5 *= var3 * 0.9F + 0.1F;
		var6 *= var3 * 0.85F + 0.15F;
		return Vec3D.createVector((double)var4, (double)var5, (double)var6);
	}

	public Vec3D func_4082_d(float var1) {
		float var2 = this.getCelestialAngle(var1);
		return this.worldProvider.func_4096_a(var2, var1);
	}

	public int func_4083_e(int var1, int var2) {
		Chunk var3 = this.getChunkFromBlockCoords(var1, var2);

		int var4;
		for(var4 = 127; this.getBlockMaterial(var1, var4, var2).func_880_c() && var4 > 0; --var4) {
		}

		var1 &= 15;

		for(var2 &= 15; var4 > 0; --var4) {
			int var5 = var3.getBlockID(var1, var4, var2);
			if(var5 != 0 && (Block.blocksList[var5].blockMaterial.func_880_c() || Block.blocksList[var5].blockMaterial.getIsLiquid())) {
				return var4 + 1;
			}
		}

		return -1;
	}

	public int func_696_e(int var1, int var2) {
		return this.getChunkFromBlockCoords(var1, var2).getHeightValue(var1 & 15, var2 & 15);
	}

	public float func_679_f(float var1) {
		float var2 = this.getCelestialAngle(var1);
		float var3 = 1.0F - (MathHelper.cos(var2 * (float)Math.PI * 2.0F) * 2.0F + 12.0F / 16.0F);
		if(var3 < 0.0F) {
			var3 = 0.0F;
		}

		if(var3 > 1.0F) {
			var3 = 1.0F;
		}

		return var3 * var3 * 0.5F;
	}

	public void scheduleBlockUpdate(int var1, int var2, int var3, int var4) {
		NextTickListEntry var5 = new NextTickListEntry(var1, var2, var3, var4);
		byte var6 = 8;
		if(this.field_4214_a) {
			if(this.checkChunksExist(var5.xCoord - var6, var5.yCoord - var6, var5.zCoord - var6, var5.xCoord + var6, var5.yCoord + var6, var5.zCoord + var6)) {
				int var7 = this.getBlockId(var5.xCoord, var5.yCoord, var5.zCoord);
				if(var7 == var5.blockID && var7 > 0) {
					Block.blocksList[var7].updateTick(this, var5.xCoord, var5.yCoord, var5.zCoord, this.rand);
				}
			}

		} else {
			if(this.checkChunksExist(var1 - var6, var2 - var6, var3 - var6, var1 + var6, var2 + var6, var3 + var6)) {
				if(var4 > 0) {
					var5.setScheduledTime((long)Block.blocksList[var4].tickRate() + this.worldTime);
				}

				if(!this.scheduledTickSet.contains(var5)) {
					this.scheduledTickSet.add(var5);
					this.scheduledTickTreeSet.add(var5);
				}
			}

		}
	}

	public void func_633_c() {
		this.loadedEntityList.removeAll(this.field_1024_A);

		int var1;
		Entity var2;
		int var3;
		int var4;
		for(var1 = 0; var1 < this.field_1024_A.size(); ++var1) {
			var2 = (Entity)this.field_1024_A.get(var1);
			var3 = var2.field_657_ba;
			var4 = var2.field_654_bc;
			if(var2.field_621_aZ && this.chunkExists(var3, var4)) {
				this.getChunkFromChunkCoords(var3, var4).func_1015_b(var2);
			}
		}

		for(var1 = 0; var1 < this.field_1024_A.size(); ++var1) {
			this.releaseEntitySkin((Entity)this.field_1024_A.get(var1));
		}

		this.field_1024_A.clear();

		for(var1 = 0; var1 < this.loadedEntityList.size(); ++var1) {
			var2 = (Entity)this.loadedEntityList.get(var1);
			if(var2.ridingEntity != null) {
				if(!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2) {
					continue;
				}

				var2.ridingEntity.riddenByEntity = null;
				var2.ridingEntity = null;
			}

			if(!var2.isDead) {
				this.func_667_e(var2);
			}

			if(var2.isDead) {
				var3 = var2.field_657_ba;
				var4 = var2.field_654_bc;
				if(var2.field_621_aZ && this.chunkExists(var3, var4)) {
					this.getChunkFromChunkCoords(var3, var4).func_1015_b(var2);
				}

				this.loadedEntityList.remove(var1--);
				this.releaseEntitySkin(var2);
			}
		}

		for(var1 = 0; var1 < this.loadedTileEntityList.size(); ++var1) {
			TileEntity var5 = (TileEntity)this.loadedTileEntityList.get(var1);
			var5.updateEntity();
		}

	}

	public void func_667_e(Entity var1) {
		this.func_4084_a(var1, true);
	}

	public void func_4084_a(Entity var1, boolean var2) {
		int var3 = MathHelper.floor_double(var1.posX);
		int var4 = MathHelper.floor_double(var1.posZ);
		byte var5 = 16;
		if(var2 || this.checkChunksExist(var3 - var5, 0, var4 - var5, var3 + var5, 128, var4 + var5)) {
			var1.lastTickPosX = var1.posX;
			var1.lastTickPosY = var1.posY;
			var1.lastTickPosZ = var1.posZ;
			var1.prevRotationYaw = var1.rotationYaw;
			var1.prevRotationPitch = var1.rotationPitch;
			if(var2 && var1.field_621_aZ) {
				if(var1.ridingEntity != null) {
					var1.func_350_p();
				} else {
					var1.onUpdate();
				}
			}

			if(Double.isNaN(var1.posX) || Double.isInfinite(var1.posX)) {
				var1.posX = var1.lastTickPosX;
			}

			if(Double.isNaN(var1.posY) || Double.isInfinite(var1.posY)) {
				var1.posY = var1.lastTickPosY;
			}

			if(Double.isNaN(var1.posZ) || Double.isInfinite(var1.posZ)) {
				var1.posZ = var1.lastTickPosZ;
			}

			if(Double.isNaN((double)var1.rotationPitch) || Double.isInfinite((double)var1.rotationPitch)) {
				var1.rotationPitch = var1.prevRotationPitch;
			}

			if(Double.isNaN((double)var1.rotationYaw) || Double.isInfinite((double)var1.rotationYaw)) {
				var1.rotationYaw = var1.prevRotationYaw;
			}

			int var6 = MathHelper.floor_double(var1.posX / 16.0D);
			int var7 = MathHelper.floor_double(var1.posY / 16.0D);
			int var8 = MathHelper.floor_double(var1.posZ / 16.0D);
			if(!var1.field_621_aZ || var1.field_657_ba != var6 || var1.field_656_bb != var7 || var1.field_654_bc != var8) {
				if(var1.field_621_aZ && this.chunkExists(var1.field_657_ba, var1.field_654_bc)) {
					this.getChunkFromChunkCoords(var1.field_657_ba, var1.field_654_bc).func_1016_a(var1, var1.field_656_bb);
				}

				if(this.chunkExists(var6, var8)) {
					var1.field_621_aZ = true;
					this.getChunkFromChunkCoords(var6, var8).addEntity(var1);
				} else {
					var1.field_621_aZ = false;
				}
			}

			if(var2 && var1.field_621_aZ && var1.riddenByEntity != null) {
				if(!var1.riddenByEntity.isDead && var1.riddenByEntity.ridingEntity == var1) {
					this.func_667_e(var1.riddenByEntity);
				} else {
					var1.riddenByEntity.ridingEntity = null;
					var1.riddenByEntity = null;
				}
			}

		}
	}

	public boolean checkIfAABBIsClear(AxisAlignedBB var1) {
		List var2 = this.getEntitiesWithinAABBExcludingEntity((Entity)null, var1);

		for(int var3 = 0; var3 < var2.size(); ++var3) {
			Entity var4 = (Entity)var2.get(var3);
			if(!var4.isDead && var4.field_618_ad) {
				return false;
			}
		}

		return true;
	}

	public boolean getIsAnyLiquid(AxisAlignedBB var1) {
		int var2 = MathHelper.floor_double(var1.minX);
		int var3 = MathHelper.floor_double(var1.maxX + 1.0D);
		int var4 = MathHelper.floor_double(var1.minY);
		int var5 = MathHelper.floor_double(var1.maxY + 1.0D);
		int var6 = MathHelper.floor_double(var1.minZ);
		int var7 = MathHelper.floor_double(var1.maxZ + 1.0D);
		if(var1.minX < 0.0D) {
			--var2;
		}

		if(var1.minY < 0.0D) {
			--var4;
		}

		if(var1.minZ < 0.0D) {
			--var6;
		}

		for(int var8 = var2; var8 < var3; ++var8) {
			for(int var9 = var4; var9 < var5; ++var9) {
				for(int var10 = var6; var10 < var7; ++var10) {
					Block var11 = Block.blocksList[this.getBlockId(var8, var9, var10)];
					if(var11 != null && var11.blockMaterial.getIsLiquid()) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean isBoundingBoxBurning(AxisAlignedBB var1) {
		int var2 = MathHelper.floor_double(var1.minX);
		int var3 = MathHelper.floor_double(var1.maxX + 1.0D);
		int var4 = MathHelper.floor_double(var1.minY);
		int var5 = MathHelper.floor_double(var1.maxY + 1.0D);
		int var6 = MathHelper.floor_double(var1.minZ);
		int var7 = MathHelper.floor_double(var1.maxZ + 1.0D);

		for(int var8 = var2; var8 < var3; ++var8) {
			for(int var9 = var4; var9 < var5; ++var9) {
				for(int var10 = var6; var10 < var7; ++var10) {
					int var11 = this.getBlockId(var8, var9, var10);
					if(var11 == Block.fire.blockID || var11 == Block.lavaStill.blockID || var11 == Block.lavaMoving.blockID) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean func_682_a(AxisAlignedBB var1, Material var2, Entity var3) {
		int var4 = MathHelper.floor_double(var1.minX);
		int var5 = MathHelper.floor_double(var1.maxX + 1.0D);
		int var6 = MathHelper.floor_double(var1.minY);
		int var7 = MathHelper.floor_double(var1.maxY + 1.0D);
		int var8 = MathHelper.floor_double(var1.minZ);
		int var9 = MathHelper.floor_double(var1.maxZ + 1.0D);
		boolean var10 = false;
		Vec3D var11 = Vec3D.createVector(0.0D, 0.0D, 0.0D);

		for(int var12 = var4; var12 < var5; ++var12) {
			for(int var13 = var6; var13 < var7; ++var13) {
				for(int var14 = var8; var14 < var9; ++var14) {
					Block var15 = Block.blocksList[this.getBlockId(var12, var13, var14)];
					if(var15 != null && var15.blockMaterial == var2) {
						double var16 = (double)((float)(var13 + 1) - BlockFluids.func_288_b(this.getBlockMetadata(var12, var13, var14)));
						if((double)var7 >= var16) {
							var10 = true;
							var15.velocityToAddToEntity(this, var12, var13, var14, var3, var11);
						}
					}
				}
			}
		}

		if(var11.lengthVector() > 0.0D) {
			var11 = var11.normalize();
			double var18 = 0.004D;
			var3.motionX += var11.xCoord * var18;
			var3.motionY += var11.yCoord * var18;
			var3.motionZ += var11.zCoord * var18;
		}

		return var10;
	}

	public boolean func_689_a(AxisAlignedBB var1, Material var2) {
		int var3 = MathHelper.floor_double(var1.minX);
		int var4 = MathHelper.floor_double(var1.maxX + 1.0D);
		int var5 = MathHelper.floor_double(var1.minY);
		int var6 = MathHelper.floor_double(var1.maxY + 1.0D);
		int var7 = MathHelper.floor_double(var1.minZ);
		int var8 = MathHelper.floor_double(var1.maxZ + 1.0D);

		for(int var9 = var3; var9 < var4; ++var9) {
			for(int var10 = var5; var10 < var6; ++var10) {
				for(int var11 = var7; var11 < var8; ++var11) {
					Block var12 = Block.blocksList[this.getBlockId(var9, var10, var11)];
					if(var12 != null && var12.blockMaterial == var2) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean func_707_b(AxisAlignedBB var1, Material var2) {
		int var3 = MathHelper.floor_double(var1.minX);
		int var4 = MathHelper.floor_double(var1.maxX + 1.0D);
		int var5 = MathHelper.floor_double(var1.minY);
		int var6 = MathHelper.floor_double(var1.maxY + 1.0D);
		int var7 = MathHelper.floor_double(var1.minZ);
		int var8 = MathHelper.floor_double(var1.maxZ + 1.0D);

		for(int var9 = var3; var9 < var4; ++var9) {
			for(int var10 = var5; var10 < var6; ++var10) {
				for(int var11 = var7; var11 < var8; ++var11) {
					Block var12 = Block.blocksList[this.getBlockId(var9, var10, var11)];
					if(var12 != null && var12.blockMaterial == var2) {
						int var13 = this.getBlockMetadata(var9, var10, var11);
						double var14 = (double)(var10 + 1);
						if(var13 < 8) {
							var14 = (double)(var10 + 1) - (double)var13 / 8.0D;
						}

						if(var14 >= var1.minY) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public Explosion func_12243_a(Entity var1, double var2, double var4, double var6, float var8) {
		return this.func_12244_a(var1, var2, var4, var6, var8, false);
	}

	public Explosion func_12244_a(Entity var1, double var2, double var4, double var6, float var8, boolean var9) {
		Explosion var10 = new Explosion(this, var1, var2, var4, var6, var8);
		var10.field_12257_a = var9;
		var10.func_12248_a();
		var10.func_12247_b();
		return var10;
	}

	public float func_675_a(Vec3D var1, AxisAlignedBB var2) {
		double var3 = 1.0D / ((var2.maxX - var2.minX) * 2.0D + 1.0D);
		double var5 = 1.0D / ((var2.maxY - var2.minY) * 2.0D + 1.0D);
		double var7 = 1.0D / ((var2.maxZ - var2.minZ) * 2.0D + 1.0D);
		int var9 = 0;
		int var10 = 0;

		for(float var11 = 0.0F; var11 <= 1.0F; var11 = (float)((double)var11 + var3)) {
			for(float var12 = 0.0F; var12 <= 1.0F; var12 = (float)((double)var12 + var5)) {
				for(float var13 = 0.0F; var13 <= 1.0F; var13 = (float)((double)var13 + var7)) {
					double var14 = var2.minX + (var2.maxX - var2.minX) * (double)var11;
					double var16 = var2.minY + (var2.maxY - var2.minY) * (double)var12;
					double var18 = var2.minZ + (var2.maxZ - var2.minZ) * (double)var13;
					if(this.rayTraceBlocks(Vec3D.createVector(var14, var16, var18), var1) == null) {
						++var9;
					}

					++var10;
				}
			}
		}

		return (float)var9 / (float)var10;
	}

	public void onBlockHit(int var1, int var2, int var3, int var4) {
		if(var4 == 0) {
			--var2;
		}

		if(var4 == 1) {
			++var2;
		}

		if(var4 == 2) {
			--var3;
		}

		if(var4 == 3) {
			++var3;
		}

		if(var4 == 4) {
			--var1;
		}

		if(var4 == 5) {
			++var1;
		}

		if(this.getBlockId(var1, var2, var3) == Block.fire.blockID) {
			this.playSoundEffect((double)((float)var1 + 0.5F), (double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), "random.fizz", 0.5F, 2.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.8F);
			this.setBlockWithNotify(var1, var2, var3, 0);
		}

	}

	public Entity func_4085_a(Class var1) {
		return null;
	}

	public String func_687_d() {
		return "All: " + this.loadedEntityList.size();
	}

	public TileEntity getBlockTileEntity(int var1, int var2, int var3) {
		Chunk var4 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
		return var4 != null ? var4.getChunkBlockTileEntity(var1 & 15, var2, var3 & 15) : null;
	}

	public void setBlockTileEntity(int var1, int var2, int var3, TileEntity var4) {
		Chunk var5 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
		if(var5 != null) {
			var5.setChunkBlockTileEntity(var1 & 15, var2, var3 & 15, var4);
		}

	}

	public void removeBlockTileEntity(int var1, int var2, int var3) {
		Chunk var4 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
		if(var4 != null) {
			var4.removeChunkBlockTileEntity(var1 & 15, var2, var3 & 15);
		}

	}

	public boolean isBlockOpaqueCube(int var1, int var2, int var3) {
		Block var4 = Block.blocksList[this.getBlockId(var1, var2, var3)];
		return var4 == null ? false : var4.isOpaqueCube();
	}

	public void func_651_a(IProgressUpdate var1) {
		this.saveWorld(true, var1);
	}

	public boolean func_6465_g() {
		if(this.field_4204_J >= 50) {
			return false;
		} else {
			++this.field_4204_J;

			boolean var2;
			try {
				int var1 = 5000;

				while(this.field_1051_z.size() > 0) {
					--var1;
					if(var1 <= 0) {
						var2 = true;
						return var2;
					}

					((MetadataChunkBlock)this.field_1051_z.remove(this.field_1051_z.size() - 1)).func_4127_a(this);
				}

				var2 = false;
			} finally {
				--this.field_4204_J;
			}

			return var2;
		}
	}

	public void func_616_a(EnumSkyBlock var1, int var2, int var3, int var4, int var5, int var6, int var7) {
		this.func_627_a(var1, var2, var3, var4, var5, var6, var7, true);
	}

	public void func_627_a(EnumSkyBlock var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
		if(!this.worldProvider.field_6478_e || var1 != EnumSkyBlock.Sky) {
			++field_9429_y;
			if(field_9429_y == 50) {
				--field_9429_y;
			} else {
				int var9 = (var5 + var2) / 2;
				int var10 = (var7 + var4) / 2;
				if(!this.blockExists(var9, 64, var10)) {
					--field_9429_y;
				} else {
					int var11 = this.field_1051_z.size();
					if(var8) {
						int var12 = 4;
						if(var12 > var11) {
							var12 = var11;
						}

						for(int var13 = 0; var13 < var12; ++var13) {
							MetadataChunkBlock var14 = (MetadataChunkBlock)this.field_1051_z.get(this.field_1051_z.size() - var13 - 1);
							if(var14.field_1299_a == var1 && var14.func_866_a(var2, var3, var4, var5, var6, var7)) {
								--field_9429_y;
								return;
							}
						}
					}

					this.field_1051_z.add(new MetadataChunkBlock(var1, var2, var3, var4, var5, var6, var7));
					if(this.field_1051_z.size() > 100000) {
						this.field_1051_z.clear();
					}

					--field_9429_y;
				}
			}
		}
	}

	public void calculateInitialSkylight() {
		int var1 = this.calculateSkylightSubtracted(1.0F);
		if(var1 != this.skylightSubtracted) {
			this.skylightSubtracted = var1;
		}

	}

	public void tick() {
		SpawnerAnimals.performSpawning(this);
		this.chunkProvider.func_532_a();
		int var1 = this.calculateSkylightSubtracted(1.0F);
		if(var1 != this.skylightSubtracted) {
			this.skylightSubtracted = var1;

			for(int var2 = 0; var2 < this.worldAccesses.size(); ++var2) {
				((IWorldAccess)this.worldAccesses.get(var2)).func_936_e();
			}
		}

		++this.worldTime;
		if(this.worldTime % (long)this.autosavePeriod == 0L) {
			this.saveWorld(false, (IProgressUpdate)null);
		}

		this.TickUpdates(false);
		this.func_4080_j();
	}

	protected void func_4080_j() {
		this.field_9427_K.clear();

		int var3;
		int var4;
		int var6;
		int var7;
		for(int var1 = 0; var1 < this.playerEntities.size(); ++var1) {
			EntityPlayer var2 = (EntityPlayer)this.playerEntities.get(var1);
			var3 = MathHelper.floor_double(var2.posX / 16.0D);
			var4 = MathHelper.floor_double(var2.posZ / 16.0D);
			byte var5 = 9;

			for(var6 = -var5; var6 <= var5; ++var6) {
				for(var7 = -var5; var7 <= var5; ++var7) {
					this.field_9427_K.add(new ChunkCoordIntPair(var6 + var3, var7 + var4));
				}
			}
		}

		if(this.field_9426_L > 0) {
			--this.field_9426_L;
		}

		Iterator var12 = this.field_9427_K.iterator();

		while(var12.hasNext()) {
			ChunkCoordIntPair var13 = (ChunkCoordIntPair)var12.next();
			var3 = var13.chunkXPos * 16;
			var4 = var13.chunkZPos * 16;
			Chunk var14 = this.getChunkFromChunkCoords(var13.chunkXPos, var13.chunkZPos);
			int var8;
			int var9;
			int var10;
			if(this.field_9426_L == 0) {
				this.field_9437_g = this.field_9437_g * 3 + this.field_9436_h;
				var6 = this.field_9437_g >> 2;
				var7 = var6 & 15;
				var8 = var6 >> 8 & 15;
				var9 = var6 >> 16 & 127;
				var10 = var14.getBlockID(var7, var9, var8);
				var7 += var3;
				var8 += var4;
				if(var10 == 0 && this.getBlockLightValue(var7, var9, var8) <= this.rand.nextInt(8) && this.getSavedLightValue(EnumSkyBlock.Sky, var7, var9, var8) <= 0) {
					EntityPlayer var11 = this.getClosestPlayer((double)var7 + 0.5D, (double)var9 + 0.5D, (double)var8 + 0.5D, 8.0D);
					if(var11 != null && var11.getDistanceSq((double)var7 + 0.5D, (double)var9 + 0.5D, (double)var8 + 0.5D) > 4.0D) {
						this.playSoundEffect((double)var7 + 0.5D, (double)var9 + 0.5D, (double)var8 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.rand.nextFloat() * 0.2F);
						this.field_9426_L = this.rand.nextInt(12000) + 6000;
					}
				}
			}

			for(var6 = 0; var6 < 80; ++var6) {
				this.field_9437_g = this.field_9437_g * 3 + this.field_9436_h;
				var7 = this.field_9437_g >> 2;
				var8 = var7 & 15;
				var9 = var7 >> 8 & 15;
				var10 = var7 >> 16 & 127;
				byte var15 = var14.blocks[var8 << 11 | var9 << 7 | var10];
				if(Block.tickOnLoad[var15]) {
					Block.blocksList[var15].updateTick(this, var8 + var3, var10, var9 + var4, this.rand);
				}
			}
		}

	}

	public boolean TickUpdates(boolean var1) {
		int var2 = this.scheduledTickTreeSet.size();
		if(var2 != this.scheduledTickSet.size()) {
			throw new IllegalStateException("TickNextTick list out of synch");
		} else {
			if(var2 > 1000) {
				var2 = 1000;
			}

			for(int var3 = 0; var3 < var2; ++var3) {
				NextTickListEntry var4 = (NextTickListEntry)this.scheduledTickTreeSet.first();
				if(!var1 && var4.scheduledTime > this.worldTime) {
					break;
				}

				this.scheduledTickTreeSet.remove(var4);
				this.scheduledTickSet.remove(var4);
				byte var5 = 8;
				if(this.checkChunksExist(var4.xCoord - var5, var4.yCoord - var5, var4.zCoord - var5, var4.xCoord + var5, var4.yCoord + var5, var4.zCoord + var5)) {
					int var6 = this.getBlockId(var4.xCoord, var4.yCoord, var4.zCoord);
					if(var6 == var4.blockID && var6 > 0) {
						Block.blocksList[var6].updateTick(this, var4.xCoord, var4.yCoord, var4.zCoord, this.rand);
					}
				}
			}

			return this.scheduledTickTreeSet.size() != 0;
		}
	}

	public void randomDisplayUpdates(int var1, int var2, int var3) {
		byte var4 = 16;
		Random var5 = new Random();

		for(int var6 = 0; var6 < 1000; ++var6) {
			int var7 = var1 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
			int var8 = var2 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
			int var9 = var3 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
			int var10 = this.getBlockId(var7, var8, var9);
			if(var10 > 0) {
				Block.blocksList[var10].randomDisplayTick(this, var7, var8, var9, var5);
			}
		}

	}

	public List getEntitiesWithinAABBExcludingEntity(Entity var1, AxisAlignedBB var2) {
		this.field_1012_M.clear();
		int var3 = MathHelper.floor_double((var2.minX - 2.0D) / 16.0D);
		int var4 = MathHelper.floor_double((var2.maxX + 2.0D) / 16.0D);
		int var5 = MathHelper.floor_double((var2.minZ - 2.0D) / 16.0D);
		int var6 = MathHelper.floor_double((var2.maxZ + 2.0D) / 16.0D);

		for(int var7 = var3; var7 <= var4; ++var7) {
			for(int var8 = var5; var8 <= var6; ++var8) {
				if(this.chunkExists(var7, var8)) {
					this.getChunkFromChunkCoords(var7, var8).getEntitiesWithinAABBForEntity(var1, var2, this.field_1012_M);
				}
			}
		}

		return this.field_1012_M;
	}

	public List getEntitiesWithinAABB(Class var1, AxisAlignedBB var2) {
		int var3 = MathHelper.floor_double((var2.minX - 2.0D) / 16.0D);
		int var4 = MathHelper.floor_double((var2.maxX + 2.0D) / 16.0D);
		int var5 = MathHelper.floor_double((var2.minZ - 2.0D) / 16.0D);
		int var6 = MathHelper.floor_double((var2.maxZ + 2.0D) / 16.0D);
		ArrayList var7 = new ArrayList();

		for(int var8 = var3; var8 <= var4; ++var8) {
			for(int var9 = var5; var9 <= var6; ++var9) {
				if(this.chunkExists(var8, var9)) {
					this.getChunkFromChunkCoords(var8, var9).getEntitiesOfTypeWithinAAAB(var1, var2, var7);
				}
			}
		}

		return var7;
	}

	public List func_658_i() {
		return this.loadedEntityList;
	}

	public void func_698_b(int var1, int var2, int var3, TileEntity var4) {
		if(this.blockExists(var1, var2, var3)) {
			this.getChunkFromBlockCoords(var1, var3).setChunkModified();
		}

		for(int var5 = 0; var5 < this.worldAccesses.size(); ++var5) {
			((IWorldAccess)this.worldAccesses.get(var5)).func_935_a(var1, var2, var3, var4);
		}

	}

	public int countEntities(Class var1) {
		int var2 = 0;

		for(int var3 = 0; var3 < this.loadedEntityList.size(); ++var3) {
			Entity var4 = (Entity)this.loadedEntityList.get(var3);
			if(var1.isAssignableFrom(var4.getClass())) {
				++var2;
			}
		}

		return var2;
	}

	public void func_636_a(List var1) {
		this.loadedEntityList.addAll(var1);

		for(int var2 = 0; var2 < var1.size(); ++var2) {
			this.obtainEntitySkin((Entity)var1.get(var2));
		}

	}

	public void func_632_b(List var1) {
		this.field_1024_A.addAll(var1);
	}

	public void func_656_j() {
		while(this.chunkProvider.func_532_a()) {
		}

	}

	public boolean canBlockBePlacedAt(int var1, int var2, int var3, int var4, boolean var5) {
		int var6 = this.getBlockId(var2, var3, var4);
		Block var7 = Block.blocksList[var6];
		Block var8 = Block.blocksList[var1];
		AxisAlignedBB var9 = var8.getCollisionBoundingBoxFromPool(this, var2, var3, var4);
		if(var5) {
			var9 = null;
		}

		return var9 != null && !this.checkIfAABBIsClear(var9) ? false : (var7 != Block.waterStill && var7 != Block.waterMoving && var7 != Block.lavaStill && var7 != Block.lavaMoving && var7 != Block.fire && var7 != Block.snow ? var1 > 0 && var7 == null && var8.canPlaceBlockAt(this, var2, var3, var4) : true);
	}

	public PathEntity getPathToEntity(Entity var1, Entity var2, float var3) {
		int var4 = MathHelper.floor_double(var1.posX);
		int var5 = MathHelper.floor_double(var1.posY);
		int var6 = MathHelper.floor_double(var1.posZ);
		int var7 = (int)(var3 + 16.0F);
		int var8 = var4 - var7;
		int var9 = var5 - var7;
		int var10 = var6 - var7;
		int var11 = var4 + var7;
		int var12 = var5 + var7;
		int var13 = var6 + var7;
		ChunkCache var14 = new ChunkCache(this, var8, var9, var10, var11, var12, var13);
		return (new Pathfinder(var14)).createEntityPathTo(var1, var2, var3);
	}

	public PathEntity getEntityPathToXYZ(Entity var1, int var2, int var3, int var4, float var5) {
		int var6 = MathHelper.floor_double(var1.posX);
		int var7 = MathHelper.floor_double(var1.posY);
		int var8 = MathHelper.floor_double(var1.posZ);
		int var9 = (int)(var5 + 8.0F);
		int var10 = var6 - var9;
		int var11 = var7 - var9;
		int var12 = var8 - var9;
		int var13 = var6 + var9;
		int var14 = var7 + var9;
		int var15 = var8 + var9;
		ChunkCache var16 = new ChunkCache(this, var10, var11, var12, var13, var14, var15);
		return (new Pathfinder(var16)).createEntityPathTo(var1, var2, var3, var4, var5);
	}

	public boolean isBlockProvidingPowerTo(int var1, int var2, int var3, int var4) {
		int var5 = this.getBlockId(var1, var2, var3);
		return var5 == 0 ? false : Block.blocksList[var5].isIndirectlyPoweringTo(this, var1, var2, var3, var4);
	}

	public boolean isBlockGettingPowered(int var1, int var2, int var3) {
		return this.isBlockProvidingPowerTo(var1, var2 - 1, var3, 0) ? true : (this.isBlockProvidingPowerTo(var1, var2 + 1, var3, 1) ? true : (this.isBlockProvidingPowerTo(var1, var2, var3 - 1, 2) ? true : (this.isBlockProvidingPowerTo(var1, var2, var3 + 1, 3) ? true : (this.isBlockProvidingPowerTo(var1 - 1, var2, var3, 4) ? true : this.isBlockProvidingPowerTo(var1 + 1, var2, var3, 5)))));
	}

	public boolean isBlockIndirectlyProvidingPowerTo(int var1, int var2, int var3, int var4) {
		if(this.isBlockOpaqueCube(var1, var2, var3)) {
			return this.isBlockGettingPowered(var1, var2, var3);
		} else {
			int var5 = this.getBlockId(var1, var2, var3);
			return var5 == 0 ? false : Block.blocksList[var5].isPoweringTo(this, var1, var2, var3, var4);
		}
	}

	public boolean isBlockIndirectlyGettingPowered(int var1, int var2, int var3) {
		return this.isBlockIndirectlyProvidingPowerTo(var1, var2 - 1, var3, 0) ? true : (this.isBlockIndirectlyProvidingPowerTo(var1, var2 + 1, var3, 1) ? true : (this.isBlockIndirectlyProvidingPowerTo(var1, var2, var3 - 1, 2) ? true : (this.isBlockIndirectlyProvidingPowerTo(var1, var2, var3 + 1, 3) ? true : (this.isBlockIndirectlyProvidingPowerTo(var1 - 1, var2, var3, 4) ? true : this.isBlockIndirectlyProvidingPowerTo(var1 + 1, var2, var3, 5)))));
	}

	public EntityPlayer getClosestPlayerToEntity(Entity var1, double var2) {
		return this.getClosestPlayer(var1.posX, var1.posY, var1.posZ, var2);
	}

	public EntityPlayer getClosestPlayer(double var1, double var3, double var5, double var7) {
		double var9 = -1.0D;
		EntityPlayer var11 = null;

		for(int var12 = 0; var12 < this.playerEntities.size(); ++var12) {
			EntityPlayer var13 = (EntityPlayer)this.playerEntities.get(var12);
			double var14 = var13.getDistanceSq(var1, var3, var5);
			if((var7 < 0.0D || var14 < var7 * var7) && (var9 == -1.0D || var14 < var9)) {
				var9 = var14;
				var11 = var13;
			}
		}

		return var11;
	}

	public void func_693_a(int var1, int var2, int var3, int var4, int var5, int var6, byte[] var7) {
		int var8 = var1 >> 4;
		int var9 = var3 >> 4;
		int var10 = var1 + var4 - 1 >> 4;
		int var11 = var3 + var6 - 1 >> 4;
		int var12 = 0;
		int var13 = var2;
		int var14 = var2 + var5;
		if(var2 < 0) {
			var13 = 0;
		}

		if(var14 > 128) {
			var14 = 128;
		}

		for(int var15 = var8; var15 <= var10; ++var15) {
			int var16 = var1 - var15 * 16;
			int var17 = var1 + var4 - var15 * 16;
			if(var16 < 0) {
				var16 = 0;
			}

			if(var17 > 16) {
				var17 = 16;
			}

			for(int var18 = var9; var18 <= var11; ++var18) {
				int var19 = var3 - var18 * 16;
				int var20 = var3 + var6 - var18 * 16;
				if(var19 < 0) {
					var19 = 0;
				}

				if(var20 > 16) {
					var20 = 16;
				}

				var12 = this.getChunkFromChunkCoords(var15, var18).func_1004_a(var7, var16, var13, var19, var17, var14, var20, var12);
				this.func_701_b(var15 * 16 + var16, var13, var18 * 16 + var19, var15 * 16 + var17, var14, var18 * 16 + var20);
			}
		}

	}

	public void sendQuittingDisconnectingPacket() {
	}

	public void func_663_l() {
		try {
			VFile2 var1 = new VFile2(this.field_9432_t, "session.lock");
			DataInputStream var2 = new DataInputStream(var1.getInputStream());

			try {
				if(var2.readLong() != this.field_1054_E) {
					throw new MinecraftException("The save is being accessed from another location, aborting");
				}
			} finally {
				var2.close();
			}

		} catch (IOException var7) {
			throw new MinecraftException("Failed to check session lock, aborting");
		}
	}

	public void setWorldTime(long var1) {
		this.worldTime = var1;
	}

	public void func_705_f(Entity var1) {
		int var2 = MathHelper.floor_double(var1.posX / 16.0D);
		int var3 = MathHelper.floor_double(var1.posZ / 16.0D);
		byte var4 = 2;

		for(int var5 = var2 - var4; var5 <= var2 + var4; ++var5) {
			for(int var6 = var3 - var4; var6 <= var3 + var4; ++var6) {
				this.getChunkFromChunkCoords(var5, var6);
			}
		}

		if(!this.loadedEntityList.contains(var1)) {
			this.loadedEntityList.add(var1);
		}

	}

	public boolean func_6466_a(EntityPlayer var1, int var2, int var3, int var4) {
		return true;
	}

	public void func_9425_a(Entity var1, byte var2) {
	}

	public void func_9424_o() {
		this.loadedEntityList.removeAll(this.field_1024_A);

		int var1;
		Entity var2;
		int var3;
		int var4;
		for(var1 = 0; var1 < this.field_1024_A.size(); ++var1) {
			var2 = (Entity)this.field_1024_A.get(var1);
			var3 = var2.field_657_ba;
			var4 = var2.field_654_bc;
			if(var2.field_621_aZ && this.chunkExists(var3, var4)) {
				this.getChunkFromChunkCoords(var3, var4).func_1015_b(var2);
			}
		}

		for(var1 = 0; var1 < this.field_1024_A.size(); ++var1) {
			this.releaseEntitySkin((Entity)this.field_1024_A.get(var1));
		}

		this.field_1024_A.clear();

		for(var1 = 0; var1 < this.loadedEntityList.size(); ++var1) {
			var2 = (Entity)this.loadedEntityList.get(var1);
			if(var2.ridingEntity != null) {
				if(!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2) {
					continue;
				}

				var2.ridingEntity.riddenByEntity = null;
				var2.ridingEntity = null;
			}

			if(var2.isDead) {
				var3 = var2.field_657_ba;
				var4 = var2.field_654_bc;
				if(var2.field_621_aZ && this.chunkExists(var3, var4)) {
					this.getChunkFromChunkCoords(var3, var4).func_1015_b(var2);
				}

				this.loadedEntityList.remove(var1--);
				this.releaseEntitySkin(var2);
			}
		}

	}
}
