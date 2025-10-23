package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.lax1dude.eaglercraft.internal.buffer.IntBuffer;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class RenderGlobal implements IWorldAccess {
	public List field_1458_a = new ArrayList();
	private World worldObj;
	private RenderEngine renderEngine;
	private List field_1446_m = new ArrayList();
	private WorldRenderer[] field_1445_n;
	private WorldRenderer[] field_1444_o;
	private int field_1443_p;
	private int field_1442_q;
	private int field_1441_r;
	private int field_1440_s;
	private Minecraft mc;
	private RenderBlocks field_1438_u;
	private IntBuffer field_1437_v;
	private int field_1435_x = 0;
	private int field_1434_y;
	private int field_1433_z;
	private int field_1432_A;
	private int field_1431_B;
	private int field_1430_C;
	private int field_1429_D;
	private int field_1428_E;
	private int field_1427_F;
	private int field_1426_G;
	private int field_1425_H = -1;
	private int field_1424_I = 2;
	private int field_1423_J;
	private int field_1422_K;
	private int field_1421_L;
	int[] field_1457_b = new int['\uc350'];
	IntBuffer field_1456_c = GLAllocation.createDirectIntBuffer(64);
	private int field_1420_M;
	private int field_1419_N;
	private int field_1418_O;
	private int field_1417_P;
	private int field_1416_Q;
	private List field_1415_R = new ArrayList();
	private RenderList[] field_1414_S = new RenderList[]{new RenderList(), new RenderList(), new RenderList(), new RenderList()};
	int field_1455_d = 0;
	int field_1454_e = GLAllocation.generateDisplayLists(1);
	double field_1453_f = -9999.0D;
	double field_1452_g = -9999.0D;
	double field_1451_h = -9999.0D;
	public float field_1450_i;
	int field_1449_j = 0;

	public RenderGlobal(Minecraft var1, RenderEngine var2) {
		this.mc = var1;
		this.renderEngine = var2;
		byte var3 = 64;
		this.field_1440_s = GLAllocation.generateDisplayLists(var3 * var3 * var3 * 3);

		this.field_1434_y = GLAllocation.generateDisplayLists(3);
		GL11.glPushMatrix();
		GL11.glNewList(this.field_1434_y, GL11.GL_COMPILE);
		this.func_950_f();
		GL11.glEndList();
		GL11.glPopMatrix();
		Tessellator var4 = Tessellator.instance;
		this.field_1433_z = this.field_1434_y + 1;
		GL11.glNewList(this.field_1433_z, GL11.GL_COMPILE);
		byte var6 = 64;
		int var7 = 256 / var6 + 2;
		float var5 = 16.0F;

		int var8;
		int var9;
		for(var8 = -var6 * var7; var8 <= var6 * var7; var8 += var6) {
			for(var9 = -var6 * var7; var9 <= var6 * var7; var9 += var6) {
				var4.startDrawingQuads();
				var4.addVertex((double)(var8 + 0), (double)var5, (double)(var9 + 0));
				var4.addVertex((double)(var8 + var6), (double)var5, (double)(var9 + 0));
				var4.addVertex((double)(var8 + var6), (double)var5, (double)(var9 + var6));
				var4.addVertex((double)(var8 + 0), (double)var5, (double)(var9 + var6));
				var4.draw();
			}
		}

		GL11.glEndList();
		this.field_1432_A = this.field_1434_y + 2;
		GL11.glNewList(this.field_1432_A, GL11.GL_COMPILE);
		var5 = -16.0F;
		var4.startDrawingQuads();

		for(var8 = -var6 * var7; var8 <= var6 * var7; var8 += var6) {
			for(var9 = -var6 * var7; var9 <= var6 * var7; var9 += var6) {
				var4.addVertex((double)(var8 + var6), (double)var5, (double)(var9 + 0));
				var4.addVertex((double)(var8 + 0), (double)var5, (double)(var9 + 0));
				var4.addVertex((double)(var8 + 0), (double)var5, (double)(var9 + var6));
				var4.addVertex((double)(var8 + var6), (double)var5, (double)(var9 + var6));
			}
		}

		var4.draw();
		GL11.glEndList();
	}

	private void func_950_f() {
		Random var1 = new Random(10842L);
		Tessellator var2 = Tessellator.instance;
		var2.startDrawingQuads();

		for(int var3 = 0; var3 < 1500; ++var3) {
			double var4 = (double)(var1.nextFloat() * 2.0F - 1.0F);
			double var6 = (double)(var1.nextFloat() * 2.0F - 1.0F);
			double var8 = (double)(var1.nextFloat() * 2.0F - 1.0F);
			double var10 = (double)(0.25F + var1.nextFloat() * 0.25F);
			double var12 = var4 * var4 + var6 * var6 + var8 * var8;
			if(var12 < 1.0D && var12 > 0.01D) {
				var12 = 1.0D / Math.sqrt(var12);
				var4 *= var12;
				var6 *= var12;
				var8 *= var12;
				double var14 = var4 * 100.0D;
				double var16 = var6 * 100.0D;
				double var18 = var8 * 100.0D;
				double var20 = Math.atan2(var4, var8);
				double var22 = Math.sin(var20);
				double var24 = Math.cos(var20);
				double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
				double var28 = Math.sin(var26);
				double var30 = Math.cos(var26);
				double var32 = var1.nextDouble() * Math.PI * 2.0D;
				double var34 = Math.sin(var32);
				double var36 = Math.cos(var32);

				for(int var38 = 0; var38 < 4; ++var38) {
					double var39 = 0.0D;
					double var41 = (double)((var38 & 2) - 1) * var10;
					double var43 = (double)((var38 + 1 & 2) - 1) * var10;
					double var47 = var41 * var36 - var43 * var34;
					double var49 = var43 * var36 + var41 * var34;
					double var53 = var47 * var28 + var39 * var30;
					double var55 = var39 * var28 - var47 * var30;
					double var57 = var55 * var22 - var49 * var24;
					double var61 = var49 * var22 + var55 * var24;
					var2.addVertex(var14 + var57, var16 + var53, var18 + var61);
				}
			}
		}

		var2.draw();
	}

	public void func_946_a(World var1) {
		if(this.worldObj != null) {
			this.worldObj.removeWorldAccess(this);
		}

		this.field_1453_f = -9999.0D;
		this.field_1452_g = -9999.0D;
		this.field_1451_h = -9999.0D;
		RenderManager.instance.func_852_a(var1);
		this.worldObj = var1;
		this.field_1438_u = new RenderBlocks(var1);
		if(var1 != null) {
			var1.addWorldAccess(this);
			this.func_958_a();
		}

	}

	public void func_958_a() {
		Block.leaves.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
		this.field_1425_H = this.mc.gameSettings.renderDistance;
		int var1;
		if(this.field_1444_o != null) {
			for(var1 = 0; var1 < this.field_1444_o.length; ++var1) {
				this.field_1444_o[var1].func_1204_c();
			}
		}

		var1 = 64 << 3 - this.field_1425_H;
		if(var1 > 400) {
			var1 = 400;
		}

		this.field_1443_p = var1 / 16 + 1;
		this.field_1442_q = 8;
		this.field_1441_r = var1 / 16 + 1;
		this.field_1444_o = new WorldRenderer[this.field_1443_p * this.field_1442_q * this.field_1441_r];
		this.field_1445_n = new WorldRenderer[this.field_1443_p * this.field_1442_q * this.field_1441_r];
		int var2 = 0;
		int var3 = 0;
		this.field_1431_B = 0;
		this.field_1430_C = 0;
		this.field_1429_D = 0;
		this.field_1428_E = this.field_1443_p;
		this.field_1427_F = this.field_1442_q;
		this.field_1426_G = this.field_1441_r;

		int var4;
		for(var4 = 0; var4 < this.field_1446_m.size(); ++var4) {
			((WorldRenderer)this.field_1446_m.get(var4)).needsUpdate = false;
		}

		this.field_1446_m.clear();
		this.field_1458_a.clear();

		for(var4 = 0; var4 < this.field_1443_p; ++var4) {
			for(int var5 = 0; var5 < this.field_1442_q; ++var5) {
				for(int var6 = 0; var6 < this.field_1441_r; ++var6) {
					this.field_1444_o[(var6 * this.field_1442_q + var5) * this.field_1443_p + var4] = new WorldRenderer(this.worldObj, this.field_1458_a, var4 * 16, var5 * 16, var6 * 16, 16, this.field_1440_s + var2);

					this.field_1444_o[(var6 * this.field_1442_q + var5) * this.field_1443_p + var4].field_1733_y = false;
					this.field_1444_o[(var6 * this.field_1442_q + var5) * this.field_1443_p + var4].field_1734_x = true;
					this.field_1444_o[(var6 * this.field_1442_q + var5) * this.field_1443_p + var4].field_1749_o = true;
					this.field_1444_o[(var6 * this.field_1442_q + var5) * this.field_1443_p + var4].field_1735_w = var3++;
					this.field_1444_o[(var6 * this.field_1442_q + var5) * this.field_1443_p + var4].MarkDirty();
					this.field_1445_n[(var6 * this.field_1442_q + var5) * this.field_1443_p + var4] = this.field_1444_o[(var6 * this.field_1442_q + var5) * this.field_1443_p + var4];
					this.field_1446_m.add(this.field_1444_o[(var6 * this.field_1442_q + var5) * this.field_1443_p + var4]);
					var2 += 3;
				}
			}
		}

		if(this.worldObj != null) {
			EntityPlayerSP var7 = this.mc.thePlayer;
			this.func_956_b(MathHelper.floor_double(var7.posX), MathHelper.floor_double(var7.posY), MathHelper.floor_double(var7.posZ));
			Arrays.sort(this.field_1445_n, new EntitySorter(var7));
		}

		this.field_1424_I = 2;
	}

	public void func_951_a(Vec3D var1, ICamera var2, float var3) {
		if(this.field_1424_I > 0) {
			--this.field_1424_I;
		} else {
			TileEntityRenderer.instance.setRenderingContext(this.worldObj, this.renderEngine, this.mc.fontRenderer, this.mc.thePlayer, var3);
			RenderManager.instance.func_857_a(this.worldObj, this.renderEngine, this.mc.fontRenderer, this.mc.thePlayer, this.mc.gameSettings, var3);
			this.field_1423_J = 0;
			this.field_1422_K = 0;
			this.field_1421_L = 0;
			EntityPlayerSP var4 = this.mc.thePlayer;
			RenderManager.field_1232_b = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)var3;
			RenderManager.field_1231_c = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)var3;
			RenderManager.field_1230_d = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)var3;
			TileEntityRenderer.staticPlayerX = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)var3;
			TileEntityRenderer.staticPlayerY = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)var3;
			TileEntityRenderer.staticPlayerZ = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)var3;
			List var5 = this.worldObj.func_658_i();
			this.field_1423_J = var5.size();

			int var6;
			for(var6 = 0; var6 < var5.size(); ++var6) {
				Entity var7 = (Entity)var5.get(var6);
				if(var7.func_390_a(var1) && var2.func_342_a(var7.boundingBox) && (var7 != this.mc.thePlayer || this.mc.gameSettings.thirdPersonView)) {
					++this.field_1422_K;
					RenderManager.instance.func_854_a(var7, var3);
				}
			}

			for(var6 = 0; var6 < this.field_1458_a.size(); ++var6) {
				TileEntityRenderer.instance.renderTileEntity((TileEntity)this.field_1458_a.get(var6), var3);
			}

		}
	}

	public String func_953_b() {
		return "C: " + this.field_1417_P + "/" + this.field_1420_M + ". F: " + this.field_1419_N + ", O: " + this.field_1418_O + ", E: " + this.field_1416_Q;
	}

	public String func_957_c() {
		return "E: " + this.field_1422_K + "/" + this.field_1423_J + ". B: " + this.field_1421_L + ", I: " + (this.field_1423_J - this.field_1421_L - this.field_1422_K);
	}

	private void func_956_b(int var1, int var2, int var3) {
		var1 -= 8;
		var2 -= 8;
		var3 -= 8;
		this.field_1431_B = Integer.MAX_VALUE;
		this.field_1430_C = Integer.MAX_VALUE;
		this.field_1429_D = Integer.MAX_VALUE;
		this.field_1428_E = Integer.MIN_VALUE;
		this.field_1427_F = Integer.MIN_VALUE;
		this.field_1426_G = Integer.MIN_VALUE;
		int var4 = this.field_1443_p * 16;
		int var5 = var4 / 2;

		for(int var6 = 0; var6 < this.field_1443_p; ++var6) {
			int var7 = var6 * 16;
			int var8 = var7 + var5 - var1;
			if(var8 < 0) {
				var8 -= var4 - 1;
			}

			var8 /= var4;
			var7 -= var8 * var4;
			if(var7 < this.field_1431_B) {
				this.field_1431_B = var7;
			}

			if(var7 > this.field_1428_E) {
				this.field_1428_E = var7;
			}

			for(int var9 = 0; var9 < this.field_1441_r; ++var9) {
				int var10 = var9 * 16;
				int var11 = var10 + var5 - var3;
				if(var11 < 0) {
					var11 -= var4 - 1;
				}

				var11 /= var4;
				var10 -= var11 * var4;
				if(var10 < this.field_1429_D) {
					this.field_1429_D = var10;
				}

				if(var10 > this.field_1426_G) {
					this.field_1426_G = var10;
				}

				for(int var12 = 0; var12 < this.field_1442_q; ++var12) {
					int var13 = var12 * 16;
					if(var13 < this.field_1430_C) {
						this.field_1430_C = var13;
					}

					if(var13 > this.field_1427_F) {
						this.field_1427_F = var13;
					}

					WorldRenderer var14 = this.field_1444_o[(var9 * this.field_1442_q + var12) * this.field_1443_p + var6];
					boolean var15 = var14.needsUpdate;
					var14.func_1197_a(var7, var13, var10);
					if(!var15 && var14.needsUpdate) {
						this.field_1446_m.add(var14);
					}
				}
			}
		}

	}

	public int func_943_a(EntityPlayer var1, int var2, double var3) {
		if(this.mc.gameSettings.renderDistance != this.field_1425_H) {
			this.func_958_a();
		}

		if(var2 == 0) {
			this.field_1420_M = 0;
			this.field_1419_N = 0;
			this.field_1418_O = 0;
			this.field_1417_P = 0;
			this.field_1416_Q = 0;
		}

		double var5 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var3;
		double var7 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var3;
		double var9 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var3;
		double var11 = var1.posX - this.field_1453_f;
		double var13 = var1.posY - this.field_1452_g;
		double var15 = var1.posZ - this.field_1451_h;
		if(var11 * var11 + var13 * var13 + var15 * var15 > 16.0D) {
			this.field_1453_f = var1.posX;
			this.field_1452_g = var1.posY;
			this.field_1451_h = var1.posZ;
			this.func_956_b(MathHelper.floor_double(var1.posX), MathHelper.floor_double(var1.posY), MathHelper.floor_double(var1.posZ));
			Arrays.sort(this.field_1445_n, new EntitySorter(var1));
		}

		byte var17 = 0;
		int var33 = var17 + this.func_952_a(0, this.field_1445_n.length, var2, var3);

		return var33;
	}

	private void func_962_a(int var1, int var2) {
		for(int var3 = var1; var3 < var2; ++var3) {
			if(this.field_1445_n[var3].field_1733_y) {
				this.field_1456_c.clear();
				if(this.field_1456_c.get(0) != 0) {
					this.field_1445_n[var3].field_1733_y = false;
					this.field_1456_c.clear();
					this.field_1445_n[var3].field_1734_x = this.field_1456_c.get(0) != 0;
				}
			}
		}

	}

	private int func_952_a(int var1, int var2, int var3, double var4) {
		this.field_1415_R.clear();
		int var6 = 0;

		for(int var7 = var1; var7 < var2; ++var7) {
			if(var3 == 0) {
				++this.field_1420_M;
				if(this.field_1445_n[var7].field_1748_p[var3]) {
					++this.field_1416_Q;
				} else if(!this.field_1445_n[var7].field_1749_o) {
					++this.field_1419_N;
				} else {
					++this.field_1417_P;
				}
			}

			if(!this.field_1445_n[var7].field_1748_p[var3] && this.field_1445_n[var7].field_1749_o && this.field_1445_n[var7].field_1734_x) {
				int var8 = this.field_1445_n[var7].func_1200_a(var3);
				if(var8 >= 0) {
					this.field_1415_R.add(this.field_1445_n[var7]);
					++var6;
				}
			}
		}

		EntityPlayerSP var19 = this.mc.thePlayer;
		double var20 = var19.lastTickPosX + (var19.posX - var19.lastTickPosX) * var4;
		double var10 = var19.lastTickPosY + (var19.posY - var19.lastTickPosY) * var4;
		double var12 = var19.lastTickPosZ + (var19.posZ - var19.lastTickPosZ) * var4;
		int var14 = 0;

		int var15;
		for(var15 = 0; var15 < this.field_1414_S.length; ++var15) {
			this.field_1414_S[var15].func_859_b();
		}

		for(var15 = 0; var15 < this.field_1415_R.size(); ++var15) {
			WorldRenderer var16 = (WorldRenderer)this.field_1415_R.get(var15);
			int var17 = -1;

			for(int var18 = 0; var18 < var14; ++var18) {
				if(this.field_1414_S[var18].func_862_a(var16.field_1755_i, var16.field_1754_j, var16.field_1753_k)) {
					var17 = var18;
				}
			}

			if(var17 < 0) {
				var17 = var14++;
				this.field_1414_S[var17].func_861_a(var16.field_1755_i, var16.field_1754_j, var16.field_1753_k, var20, var10, var12);
			}

			this.field_1414_S[var17].func_858_a(var16.func_1200_a(var3));
		}

		this.func_944_a(var3, var4);
		return var6;
	}

	public void func_944_a(int var1, double var2) {
		for(int var4 = 0; var4 < this.field_1414_S.length; ++var4) {
			this.field_1414_S[var4].func_860_a();
		}

	}

	public void func_945_d() {
		++this.field_1435_x;
	}

	public void func_4142_a(float var1) {
		if(!this.mc.theWorld.worldProvider.field_4220_c) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			Vec3D var2 = this.worldObj.func_4079_a(this.mc.thePlayer, var1);
			float var3 = (float)var2.xCoord;
			float var4 = (float)var2.yCoord;
			float var5 = (float)var2.zCoord;
			float var7;
			float var8;
			if(this.mc.gameSettings.anaglyph) {
				float var6 = (var3 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
				var7 = (var3 * 30.0F + var4 * 70.0F) / 100.0F;
				var8 = (var3 * 30.0F + var5 * 70.0F) / 100.0F;
				var3 = var6;
				var4 = var7;
				var5 = var8;
			}

			GL11.glColor3f(var3, var4, var5);
			Tessellator var14 = Tessellator.instance;
			GL11.glDepthMask(false);
			GL11.glEnable(GL11.GL_FOG);
			GL11.glColor3f(var3, var4, var5);
			GL11.glCallList(this.field_1433_z);
			GL11.glDisable(GL11.GL_FOG);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			float[] var15 = this.worldObj.worldProvider.func_4097_b(this.worldObj.getCelestialAngle(var1), var1);
			float var11;
			if(var15 != null) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				GL11.glPushMatrix();
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				var8 = this.worldObj.getCelestialAngle(var1);
				GL11.glRotatef(var8 > 0.5F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
				var14.startDrawing(6);
				var14.setColorRGBA_F(var15[0], var15[1], var15[2], var15[3]);
				var14.addVertex(0.0D, 100.0D, 0.0D);
				byte var9 = 16;
				var14.setColorRGBA_F(var15[0], var15[1], var15[2], 0.0F);

				for(int var10 = 0; var10 <= var9; ++var10) {
					var11 = (float)var10 * (float)Math.PI * 2.0F / (float)var9;
					float var12 = MathHelper.sin(var11);
					float var13 = MathHelper.cos(var11);
					var14.addVertex((double)(var12 * 120.0F), (double)(var13 * 120.0F), (double)(-var13 * 40.0F * var15[3]));
				}

				var14.draw();
				GL11.glPopMatrix();
				GL11.glShadeModel(GL11.GL_FLAT);
			}

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			GL11.glPushMatrix();
			var7 = 0.0F;
			var8 = 0.0F;
			float var16 = 0.0F;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef(var7, var8, var16);
			GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(this.worldObj.getCelestialAngle(var1) * 360.0F, 1.0F, 0.0F, 0.0F);
			float var17 = 30.0F;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain/sun.png"));
			var14.startDrawingQuads();
			var14.addVertexWithUV((double)(-var17), 100.0D, (double)(-var17), 0.0D, 0.0D);
			var14.addVertexWithUV((double)var17, 100.0D, (double)(-var17), 1.0D, 0.0D);
			var14.addVertexWithUV((double)var17, 100.0D, (double)var17, 1.0D, 1.0D);
			var14.addVertexWithUV((double)(-var17), 100.0D, (double)var17, 0.0D, 1.0D);
			var14.draw();
			var17 = 20.0F;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain/moon.png"));
			var14.startDrawingQuads();
			var14.addVertexWithUV((double)(-var17), -100.0D, (double)var17, 1.0D, 1.0D);
			var14.addVertexWithUV((double)var17, -100.0D, (double)var17, 0.0D, 1.0D);
			var14.addVertexWithUV((double)var17, -100.0D, (double)(-var17), 0.0D, 0.0D);
			var14.addVertexWithUV((double)(-var17), -100.0D, (double)(-var17), 1.0D, 0.0D);
			var14.draw();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			var11 = this.worldObj.func_679_f(var1);
			if(var11 > 0.0F) {
				GL11.glColor4f(var11, var11, var11, var11);
				GL11.glCallList(this.field_1434_y);
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_FOG);
			GL11.glPopMatrix();
			GL11.glColor3f(var3 * 0.2F + 0.04F, var4 * 0.2F + 0.04F, var5 * 0.6F + 0.1F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glCallList(this.field_1432_A);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(true);
		}
	}

	public void func_4141_b(float var1) {
		if(!this.mc.theWorld.worldProvider.field_4220_c) {
			if(this.mc.gameSettings.fancyGraphics) {
				this.func_6510_c(var1);
			} else {
				GL11.glDisable(GL11.GL_CULL_FACE);
				float var2 = (float)(this.mc.thePlayer.lastTickPosY + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY) * (double)var1);
				byte var3 = 32;
				int var4 = 256 / var3;
				Tessellator var5 = Tessellator.instance;
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/environment/clouds.png"));
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				Vec3D var6 = this.worldObj.func_628_d(var1);
				float var7 = (float)var6.xCoord;
				float var8 = (float)var6.yCoord;
				float var9 = (float)var6.zCoord;
				float var10;
				if(this.mc.gameSettings.anaglyph) {
					var10 = (var7 * 30.0F + var8 * 59.0F + var9 * 11.0F) / 100.0F;
					float var11 = (var7 * 30.0F + var8 * 70.0F) / 100.0F;
					float var12 = (var7 * 30.0F + var9 * 70.0F) / 100.0F;
					var7 = var10;
					var8 = var11;
					var9 = var12;
				}

				var10 = 0.5F / 1024.0F;
				double var22 = this.mc.thePlayer.prevPosX + (this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX) * (double)var1 + (double)(((float)this.field_1435_x + var1) * 0.03F);
				double var13 = this.mc.thePlayer.prevPosZ + (this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ) * (double)var1;
				int var15 = MathHelper.floor_double(var22 / 2048.0D);
				int var16 = MathHelper.floor_double(var13 / 2048.0D);
				var22 -= (double)(var15 * 2048);
				var13 -= (double)(var16 * 2048);
				float var17 = 120.0F - var2 + 0.33F;
				float var18 = (float)(var22 * (double)var10);
				float var19 = (float)(var13 * (double)var10);
				var5.startDrawingQuads();
				var5.setColorRGBA_F(var7, var8, var9, 0.8F);

				for(int var20 = -var3 * var4; var20 < var3 * var4; var20 += var3) {
					for(int var21 = -var3 * var4; var21 < var3 * var4; var21 += var3) {
						var5.addVertexWithUV((double)(var20 + 0), (double)var17, (double)(var21 + var3), (double)((float)(var20 + 0) * var10 + var18), (double)((float)(var21 + var3) * var10 + var19));
						var5.addVertexWithUV((double)(var20 + var3), (double)var17, (double)(var21 + var3), (double)((float)(var20 + var3) * var10 + var18), (double)((float)(var21 + var3) * var10 + var19));
						var5.addVertexWithUV((double)(var20 + var3), (double)var17, (double)(var21 + 0), (double)((float)(var20 + var3) * var10 + var18), (double)((float)(var21 + 0) * var10 + var19));
						var5.addVertexWithUV((double)(var20 + 0), (double)var17, (double)(var21 + 0), (double)((float)(var20 + 0) * var10 + var18), (double)((float)(var21 + 0) * var10 + var19));
					}
				}

				var5.draw();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}
		}
	}

	public void func_6510_c(float var1) {
		GL11.glDisable(GL11.GL_CULL_FACE);
		float var2 = (float)(this.mc.thePlayer.lastTickPosY + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY) * (double)var1);
		Tessellator var3 = Tessellator.instance;
		float var4 = 12.0F;
		float var5 = 4.0F;
		double var6 = (this.mc.thePlayer.prevPosX + (this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX) * (double)var1 + (double)(((float)this.field_1435_x + var1) * 0.03F)) / (double)var4;
		double var8 = (this.mc.thePlayer.prevPosZ + (this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ) * (double)var1) / (double)var4 + (double)0.33F;
		float var10 = 108.0F - var2 + 0.33F;
		int var11 = MathHelper.floor_double(var6 / 2048.0D);
		int var12 = MathHelper.floor_double(var8 / 2048.0D);
		var6 -= (double)(var11 * 2048);
		var8 -= (double)(var12 * 2048);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/environment/clouds.png"));
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Vec3D var13 = this.worldObj.func_628_d(var1);
		float var14 = (float)var13.xCoord;
		float var15 = (float)var13.yCoord;
		float var16 = (float)var13.zCoord;
		float var17;
		float var18;
		float var19;
		if(this.mc.gameSettings.anaglyph) {
			var17 = (var14 * 30.0F + var15 * 59.0F + var16 * 11.0F) / 100.0F;
			var18 = (var14 * 30.0F + var15 * 70.0F) / 100.0F;
			var19 = (var14 * 30.0F + var16 * 70.0F) / 100.0F;
			var14 = var17;
			var15 = var18;
			var16 = var19;
		}

		var17 = (float)(var6 * 0.0D);
		var18 = (float)(var8 * 0.0D);
		var19 = 0.00390625F;
		var17 = (float)MathHelper.floor_double(var6) * var19;
		var18 = (float)MathHelper.floor_double(var8) * var19;
		float var20 = (float)(var6 - (double)MathHelper.floor_double(var6));
		float var21 = (float)(var8 - (double)MathHelper.floor_double(var8));
		byte var22 = 8;
		byte var23 = 3;
		float var24 = 1.0F / 1024.0F;
		GL11.glScalef(var4, 1.0F, var4);

		for(int var25 = 0; var25 < 2; ++var25) {
			if(var25 == 0) {
				GL11.glColorMask(false, false, false, false);
			} else {
				GL11.glColorMask(true, true, true, true);
			}

			for(int var26 = -var23 + 1; var26 <= var23; ++var26) {
				for(int var27 = -var23 + 1; var27 <= var23; ++var27) {
					var3.startDrawingQuads();
					float var28 = (float)(var26 * var22);
					float var29 = (float)(var27 * var22);
					float var30 = var28 - var20;
					float var31 = var29 - var21;
					if(var10 > -var5 - 1.0F) {
						var3.setColorRGBA_F(var14 * 0.7F, var15 * 0.7F, var16 * 0.7F, 0.8F);
						var3.setNormal(0.0F, -1.0F, 0.0F);
						var3.addVertexWithUV((double)(var30 + 0.0F), (double)(var10 + 0.0F), (double)(var31 + (float)var22), (double)((var28 + 0.0F) * var19 + var17), (double)((var29 + (float)var22) * var19 + var18));
						var3.addVertexWithUV((double)(var30 + (float)var22), (double)(var10 + 0.0F), (double)(var31 + (float)var22), (double)((var28 + (float)var22) * var19 + var17), (double)((var29 + (float)var22) * var19 + var18));
						var3.addVertexWithUV((double)(var30 + (float)var22), (double)(var10 + 0.0F), (double)(var31 + 0.0F), (double)((var28 + (float)var22) * var19 + var17), (double)((var29 + 0.0F) * var19 + var18));
						var3.addVertexWithUV((double)(var30 + 0.0F), (double)(var10 + 0.0F), (double)(var31 + 0.0F), (double)((var28 + 0.0F) * var19 + var17), (double)((var29 + 0.0F) * var19 + var18));
					}

					if(var10 <= var5 + 1.0F) {
						var3.setColorRGBA_F(var14, var15, var16, 0.8F);
						var3.setNormal(0.0F, 1.0F, 0.0F);
						var3.addVertexWithUV((double)(var30 + 0.0F), (double)(var10 + var5 - var24), (double)(var31 + (float)var22), (double)((var28 + 0.0F) * var19 + var17), (double)((var29 + (float)var22) * var19 + var18));
						var3.addVertexWithUV((double)(var30 + (float)var22), (double)(var10 + var5 - var24), (double)(var31 + (float)var22), (double)((var28 + (float)var22) * var19 + var17), (double)((var29 + (float)var22) * var19 + var18));
						var3.addVertexWithUV((double)(var30 + (float)var22), (double)(var10 + var5 - var24), (double)(var31 + 0.0F), (double)((var28 + (float)var22) * var19 + var17), (double)((var29 + 0.0F) * var19 + var18));
						var3.addVertexWithUV((double)(var30 + 0.0F), (double)(var10 + var5 - var24), (double)(var31 + 0.0F), (double)((var28 + 0.0F) * var19 + var17), (double)((var29 + 0.0F) * var19 + var18));
					}

					var3.setColorRGBA_F(var14 * 0.9F, var15 * 0.9F, var16 * 0.9F, 0.8F);
					int var32;
					if(var26 > -1) {
						var3.setNormal(-1.0F, 0.0F, 0.0F);

						for(var32 = 0; var32 < var22; ++var32) {
							var3.addVertexWithUV((double)(var30 + (float)var32 + 0.0F), (double)(var10 + 0.0F), (double)(var31 + (float)var22), (double)((var28 + (float)var32 + 0.5F) * var19 + var17), (double)((var29 + (float)var22) * var19 + var18));
							var3.addVertexWithUV((double)(var30 + (float)var32 + 0.0F), (double)(var10 + var5), (double)(var31 + (float)var22), (double)((var28 + (float)var32 + 0.5F) * var19 + var17), (double)((var29 + (float)var22) * var19 + var18));
							var3.addVertexWithUV((double)(var30 + (float)var32 + 0.0F), (double)(var10 + var5), (double)(var31 + 0.0F), (double)((var28 + (float)var32 + 0.5F) * var19 + var17), (double)((var29 + 0.0F) * var19 + var18));
							var3.addVertexWithUV((double)(var30 + (float)var32 + 0.0F), (double)(var10 + 0.0F), (double)(var31 + 0.0F), (double)((var28 + (float)var32 + 0.5F) * var19 + var17), (double)((var29 + 0.0F) * var19 + var18));
						}
					}

					if(var26 <= 1) {
						var3.setNormal(1.0F, 0.0F, 0.0F);

						for(var32 = 0; var32 < var22; ++var32) {
							var3.addVertexWithUV((double)(var30 + (float)var32 + 1.0F - var24), (double)(var10 + 0.0F), (double)(var31 + (float)var22), (double)((var28 + (float)var32 + 0.5F) * var19 + var17), (double)((var29 + (float)var22) * var19 + var18));
							var3.addVertexWithUV((double)(var30 + (float)var32 + 1.0F - var24), (double)(var10 + var5), (double)(var31 + (float)var22), (double)((var28 + (float)var32 + 0.5F) * var19 + var17), (double)((var29 + (float)var22) * var19 + var18));
							var3.addVertexWithUV((double)(var30 + (float)var32 + 1.0F - var24), (double)(var10 + var5), (double)(var31 + 0.0F), (double)((var28 + (float)var32 + 0.5F) * var19 + var17), (double)((var29 + 0.0F) * var19 + var18));
							var3.addVertexWithUV((double)(var30 + (float)var32 + 1.0F - var24), (double)(var10 + 0.0F), (double)(var31 + 0.0F), (double)((var28 + (float)var32 + 0.5F) * var19 + var17), (double)((var29 + 0.0F) * var19 + var18));
						}
					}

					var3.setColorRGBA_F(var14 * 0.8F, var15 * 0.8F, var16 * 0.8F, 0.8F);
					if(var27 > -1) {
						var3.setNormal(0.0F, 0.0F, -1.0F);

						for(var32 = 0; var32 < var22; ++var32) {
							var3.addVertexWithUV((double)(var30 + 0.0F), (double)(var10 + var5), (double)(var31 + (float)var32 + 0.0F), (double)((var28 + 0.0F) * var19 + var17), (double)((var29 + (float)var32 + 0.5F) * var19 + var18));
							var3.addVertexWithUV((double)(var30 + (float)var22), (double)(var10 + var5), (double)(var31 + (float)var32 + 0.0F), (double)((var28 + (float)var22) * var19 + var17), (double)((var29 + (float)var32 + 0.5F) * var19 + var18));
							var3.addVertexWithUV((double)(var30 + (float)var22), (double)(var10 + 0.0F), (double)(var31 + (float)var32 + 0.0F), (double)((var28 + (float)var22) * var19 + var17), (double)((var29 + (float)var32 + 0.5F) * var19 + var18));
							var3.addVertexWithUV((double)(var30 + 0.0F), (double)(var10 + 0.0F), (double)(var31 + (float)var32 + 0.0F), (double)((var28 + 0.0F) * var19 + var17), (double)((var29 + (float)var32 + 0.5F) * var19 + var18));
						}
					}

					if(var27 <= 1) {
						var3.setNormal(0.0F, 0.0F, 1.0F);

						for(var32 = 0; var32 < var22; ++var32) {
							var3.addVertexWithUV((double)(var30 + 0.0F), (double)(var10 + var5), (double)(var31 + (float)var32 + 1.0F - var24), (double)((var28 + 0.0F) * var19 + var17), (double)((var29 + (float)var32 + 0.5F) * var19 + var18));
							var3.addVertexWithUV((double)(var30 + (float)var22), (double)(var10 + var5), (double)(var31 + (float)var32 + 1.0F - var24), (double)((var28 + (float)var22) * var19 + var17), (double)((var29 + (float)var32 + 0.5F) * var19 + var18));
							var3.addVertexWithUV((double)(var30 + (float)var22), (double)(var10 + 0.0F), (double)(var31 + (float)var32 + 1.0F - var24), (double)((var28 + (float)var22) * var19 + var17), (double)((var29 + (float)var32 + 0.5F) * var19 + var18));
							var3.addVertexWithUV((double)(var30 + 0.0F), (double)(var10 + 0.0F), (double)(var31 + (float)var32 + 1.0F - var24), (double)((var28 + 0.0F) * var19 + var17), (double)((var29 + (float)var32 + 0.5F) * var19 + var18));
						}
					}

					var3.draw();
				}
			}
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	public boolean func_948_a(EntityPlayer var1, boolean var2) {
		Collections.sort(this.field_1446_m, new RenderSorter(var1));
		int var3 = this.field_1446_m.size() - 1;
		int var4 = this.field_1446_m.size();

		for(int var5 = 0; var5 < var4; ++var5) {
			WorldRenderer var6 = (WorldRenderer)this.field_1446_m.get(var3 - var5);
			if(!var2) {
				if(var6.func_1202_a(var1) > 1024.0F) {
					if(var6.field_1749_o) {
						if(var5 >= 3) {
							return false;
						}
					} else if(var5 >= 1) {
						return false;
					}
				}
			} else if(!var6.field_1749_o) {
				continue;
			}

			var6.func_1198_a();
			this.field_1446_m.remove(var6);
			var6.needsUpdate = false;
		}

		return this.field_1446_m.size() == 0;
	}

	public void func_959_a(EntityPlayer var1, MovingObjectPosition var2, int var3, ItemStack var4, float var5) {
		Tessellator var6 = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, (MathHelper.sin((float)System.currentTimeMillis() / 100.0F) * 0.2F + 0.4F) * 0.5F);
		int var8;
		if(var3 == 0) {
			if(this.field_1450_i > 0.0F) {
				GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_SRC_COLOR);
				int var7 = this.renderEngine.getTexture("/terrain.png");
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, var7);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
				GL11.glPushMatrix();
				var8 = this.worldObj.getBlockId(var2.blockX, var2.blockY, var2.blockZ);
				Block var9 = var8 > 0 ? Block.blocksList[var8] : null;
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				GL11.glPolygonOffset(-3.0F, -3.0F);
				GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
				var6.startDrawingQuads();
				double var10 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var5;
				double var12 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var5;
				double var14 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var5;
				var6.setTranslationD(-var10, -var12, -var14);
				var6.disableColor();
				if(var9 == null) {
					var9 = Block.stone;
				}

				this.field_1438_u.renderBlockUsingTexture(var9, var2.blockX, var2.blockY, var2.blockZ, 240 + (int)(this.field_1450_i * 10.0F));
				var6.draw();
				var6.setTranslationD(0.0D, 0.0D, 0.0D);
				GL11.glPolygonOffset(0.0F, 0.0F);
				GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glDepthMask(true);
				GL11.glPopMatrix();
			}
		} else if(var4 != null) {
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			float var16 = MathHelper.sin((float)System.currentTimeMillis() / 100.0F) * 0.2F + 0.8F;
			GL11.glColor4f(var16, var16, var16, MathHelper.sin((float)System.currentTimeMillis() / 200.0F) * 0.2F + 0.5F);
			var8 = this.renderEngine.getTexture("/terrain.png");
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, var8);
			int var17 = var2.blockX;
			int var18 = var2.blockY;
			int var11 = var2.blockZ;
			if(var2.sideHit == 0) {
				--var18;
			}

			if(var2.sideHit == 1) {
				++var18;
			}

			if(var2.sideHit == 2) {
				--var11;
			}

			if(var2.sideHit == 3) {
				++var11;
			}

			if(var2.sideHit == 4) {
				--var17;
			}

			if(var2.sideHit == 5) {
				++var17;
			}
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
	}

	public void drawSelectionBox(EntityPlayer var1, MovingObjectPosition var2, int var3, ItemStack var4, float var5) {
		if(var3 == 0 && var2.typeOfHit == 0) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
			GL11.glLineWidth(2.0F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			float var6 = 0.002F;
			int var7 = this.worldObj.getBlockId(var2.blockX, var2.blockY, var2.blockZ);
			if(var7 > 0) {
				Block.blocksList[var7].setBlockBoundsBasedOnState(this.worldObj, var2.blockX, var2.blockY, var2.blockZ);
				double var8 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var5;
				double var10 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var5;
				double var12 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var5;
				this.drawOutlinedBoundingBox(Block.blocksList[var7].getSelectedBoundingBoxFromPool(this.worldObj, var2.blockX, var2.blockY, var2.blockZ).expands((double)var6, (double)var6, (double)var6).getOffsetBoundingBox(-var8, -var10, -var12));
			}

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
		}

	}

	private void drawOutlinedBoundingBox(AxisAlignedBB var1) {
		Tessellator var2 = Tessellator.instance;
		var2.startDrawing(3);
		var2.addVertex(var1.minX, var1.minY, var1.minZ);
		var2.addVertex(var1.maxX, var1.minY, var1.minZ);
		var2.addVertex(var1.maxX, var1.minY, var1.maxZ);
		var2.addVertex(var1.minX, var1.minY, var1.maxZ);
		var2.addVertex(var1.minX, var1.minY, var1.minZ);
		var2.draw();
		var2.startDrawing(3);
		var2.addVertex(var1.minX, var1.maxY, var1.minZ);
		var2.addVertex(var1.maxX, var1.maxY, var1.minZ);
		var2.addVertex(var1.maxX, var1.maxY, var1.maxZ);
		var2.addVertex(var1.minX, var1.maxY, var1.maxZ);
		var2.addVertex(var1.minX, var1.maxY, var1.minZ);
		var2.draw();
		var2.startDrawing(1);
		var2.addVertex(var1.minX, var1.minY, var1.minZ);
		var2.addVertex(var1.minX, var1.maxY, var1.minZ);
		var2.addVertex(var1.maxX, var1.minY, var1.minZ);
		var2.addVertex(var1.maxX, var1.maxY, var1.minZ);
		var2.addVertex(var1.maxX, var1.minY, var1.maxZ);
		var2.addVertex(var1.maxX, var1.maxY, var1.maxZ);
		var2.addVertex(var1.minX, var1.minY, var1.maxZ);
		var2.addVertex(var1.minX, var1.maxY, var1.maxZ);
		var2.draw();
	}

	public void func_949_a(int var1, int var2, int var3, int var4, int var5, int var6) {
		int var7 = MathHelper.bucketInt(var1, 16);
		int var8 = MathHelper.bucketInt(var2, 16);
		int var9 = MathHelper.bucketInt(var3, 16);
		int var10 = MathHelper.bucketInt(var4, 16);
		int var11 = MathHelper.bucketInt(var5, 16);
		int var12 = MathHelper.bucketInt(var6, 16);

		for(int var13 = var7; var13 <= var10; ++var13) {
			int var14 = var13 % this.field_1443_p;
			if(var14 < 0) {
				var14 += this.field_1443_p;
			}

			for(int var15 = var8; var15 <= var11; ++var15) {
				int var16 = var15 % this.field_1442_q;
				if(var16 < 0) {
					var16 += this.field_1442_q;
				}

				for(int var17 = var9; var17 <= var12; ++var17) {
					int var18 = var17 % this.field_1441_r;
					if(var18 < 0) {
						var18 += this.field_1441_r;
					}

					int var19 = (var18 * this.field_1442_q + var16) * this.field_1443_p + var14;
					WorldRenderer var20 = this.field_1444_o[var19];
					if(!var20.needsUpdate) {
						this.field_1446_m.add(var20);
					}

					var20.MarkDirty();
				}
			}
		}

	}

	public void func_934_a(int var1, int var2, int var3) {
		this.func_949_a(var1 - 1, var2 - 1, var3 - 1, var1 + 1, var2 + 1, var3 + 1);
	}

	public void func_937_b(int var1, int var2, int var3, int var4, int var5, int var6) {
		this.func_949_a(var1 - 1, var2 - 1, var3 - 1, var4 + 1, var5 + 1, var6 + 1);
	}

	public void func_960_a(ICamera var1, float var2) {
		for(int var3 = 0; var3 < this.field_1444_o.length; ++var3) {
			if(!this.field_1444_o[var3].func_1196_e() && (!this.field_1444_o[var3].field_1749_o || (var3 + this.field_1449_j & 15) == 0)) {
				this.field_1444_o[var3].func_1199_a(var1);
			}
		}

		++this.field_1449_j;
	}

	public void playRecord(String var1, int var2, int var3, int var4) {
		if(var1 != null) {
			this.mc.ingameGUI.func_553_b("C418 - " + var1);
		}

		this.mc.sndManager.func_331_a(var1, (float)var2, (float)var3, (float)var4, 1.0F, 1.0F);
	}

	public void playSound(String var1, double var2, double var4, double var6, float var8, float var9) {
		float var10 = 16.0F;
		if(var8 > 1.0F) {
			var10 *= var8;
		}

		if(this.mc.thePlayer.getDistanceSq(var2, var4, var6) < (double)(var10 * var10)) {
			this.mc.sndManager.func_336_b(var1, (float)var2, (float)var4, (float)var6, var8, var9);
		}

	}

	public void spawnParticle(String var1, double var2, double var4, double var6, double var8, double var10, double var12) {
		double var14 = this.mc.thePlayer.posX - var2;
		double var16 = this.mc.thePlayer.posY - var4;
		double var18 = this.mc.thePlayer.posZ - var6;
		if(var14 * var14 + var16 * var16 + var18 * var18 <= 256.0D) {
			if(var1 == "bubble") {
				this.mc.field_6321_h.func_1192_a(new EntityBubbleFX(this.worldObj, var2, var4, var6, var8, var10, var12));
			} else if(var1 == "smoke") {
				this.mc.field_6321_h.func_1192_a(new EntitySmokeFX(this.worldObj, var2, var4, var6));
			} else if(var1 == "portal") {
				this.mc.field_6321_h.func_1192_a(new EntityPortalFX(this.worldObj, var2, var4, var6, var8, var10, var12));
			} else if(var1 == "explode") {
				this.mc.field_6321_h.func_1192_a(new EntityExplodeFX(this.worldObj, var2, var4, var6, var8, var10, var12));
			} else if(var1 == "flame") {
				this.mc.field_6321_h.func_1192_a(new EntityFlameFX(this.worldObj, var2, var4, var6, var8, var10, var12));
			} else if(var1 == "lava") {
				this.mc.field_6321_h.func_1192_a(new EntityLavaFX(this.worldObj, var2, var4, var6));
			} else if(var1 == "splash") {
				this.mc.field_6321_h.func_1192_a(new EntitySplashFX(this.worldObj, var2, var4, var6, var8, var10, var12));
			} else if(var1 == "largesmoke") {
				this.mc.field_6321_h.func_1192_a(new EntitySmokeFX(this.worldObj, var2, var4, var6, 2.5F));
			} else if(var1 == "reddust") {
				this.mc.field_6321_h.func_1192_a(new EntityReddustFX(this.worldObj, var2, var4, var6));
			} else if(var1 == "snowballpoof") {
				this.mc.field_6321_h.func_1192_a(new EntitySlimeFX(this.worldObj, var2, var4, var6, Item.snowball));
			} else if(var1 == "slime") {
				this.mc.field_6321_h.func_1192_a(new EntitySlimeFX(this.worldObj, var2, var4, var6, Item.slimeBall));
			}

		}
	}

	public void obtainEntitySkin(Entity var1) {

	}

	public void releaseEntitySkin(Entity var1) {
		if(var1.skinUrl != null) {
			this.renderEngine.releaseImageData(var1.skinUrl);
		}

	}

	public void func_936_e() {
		for(int var1 = 0; var1 < this.field_1444_o.length; ++var1) {
			if(this.field_1444_o[var1].field_1747_A) {
				if(!this.field_1444_o[var1].needsUpdate) {
					this.field_1446_m.add(this.field_1444_o[var1]);
				}

				this.field_1444_o[var1].MarkDirty();
			}
		}

	}

	public void func_935_a(int var1, int var2, int var3, TileEntity var4) {
	}
}
