package net.minecraft.src;

import java.util.List;
import java.util.Random;

import net.lax1dude.eaglercraft.internal.buffer.FloatBuffer;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class EntityRenderer {
	private Minecraft mc;
	private float field_1387_i = 0.0F;
	public ItemRenderer field_1395_a;
	private int field_1386_j;
	private Entity field_1385_k = null;
	private long field_1384_l = System.currentTimeMillis();
	private Random field_1383_m = new Random();
	volatile int field_1394_b = 0;
	volatile int field_1393_c = 0;
	FloatBuffer field_1392_d = GLAllocation.createDirectFloatBuffer(16);
	float field_4270_e;
	float field_4269_f;
	float field_4268_g;
	private float field_1382_n;
	private float field_1381_o;

	public EntityRenderer(Minecraft var1) {
		this.mc = var1;
		this.field_1395_a = new ItemRenderer(var1);
	}

	public void func_911_a() {
		this.field_1382_n = this.field_1381_o;
		float var1 = this.mc.theWorld.getLightBrightness(MathHelper.floor_double(this.mc.thePlayer.posX), MathHelper.floor_double(this.mc.thePlayer.posY), MathHelper.floor_double(this.mc.thePlayer.posZ));
		float var2 = (float)(3 - this.mc.gameSettings.renderDistance) / 3.0F;
		float var3 = var1 * (1.0F - var2) + var2;
		this.field_1381_o += (var3 - this.field_1381_o) * 0.1F;
		++this.field_1386_j;
		this.field_1395_a.func_895_a();
		if(this.mc.field_6288_M) {
			this.func_916_c();
		}

	}

	public void func_910_a(float var1) {
		if(this.mc.thePlayer != null) {
			double var2 = (double)this.mc.field_6327_b.getBlockReachDistance();
			this.mc.objectMouseOver = this.mc.thePlayer.rayTrace(var2, var1);
			double var4 = var2;
			Vec3D var6 = this.mc.thePlayer.getPosition(var1);
			if(this.mc.objectMouseOver != null) {
				var4 = this.mc.objectMouseOver.hitVec.distanceTo(var6);
			}

			if(this.mc.field_6327_b instanceof PlayerControllerTest) {
				var2 = 32.0D;
			} else {
				if(var4 > 3.0D) {
					var4 = 3.0D;
				}

				var2 = var4;
			}

			Vec3D var7 = this.mc.thePlayer.getLook(var1);
			Vec3D var8 = var6.addVector(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2);
			this.field_1385_k = null;
			float var9 = 1.0F;
			List var10 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.thePlayer, this.mc.thePlayer.boundingBox.addCoord(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2).expands((double)var9, (double)var9, (double)var9));
			double var11 = 0.0D;

			for(int var13 = 0; var13 < var10.size(); ++var13) {
				Entity var14 = (Entity)var10.get(var13);
				if(var14.canBeCollidedWith()) {
					float var15 = var14.func_4035_j_();
					AxisAlignedBB var16 = var14.boundingBox.expands((double)var15, (double)var15, (double)var15);
					MovingObjectPosition var17 = var16.func_1169_a(var6, var8);
					if(var16.isVecInside(var6)) {
						if(0.0D < var11 || var11 == 0.0D) {
							this.field_1385_k = var14;
							var11 = 0.0D;
						}
					} else if(var17 != null) {
						double var18 = var6.distanceTo(var17.hitVec);
						if(var18 < var11 || var11 == 0.0D) {
							this.field_1385_k = var14;
							var11 = var18;
						}
					}
				}
			}

			if(this.field_1385_k != null && !(this.mc.field_6327_b instanceof PlayerControllerTest)) {
				this.mc.objectMouseOver = new MovingObjectPosition(this.field_1385_k);
			}

		}
	}

	private float func_914_d(float var1) {
		EntityPlayerSP var2 = this.mc.thePlayer;
		float var3 = 70.0F;
		if(var2.isInsideOfMaterial(Material.water)) {
			var3 = 60.0F;
		}

		if (this.mc.currentScreen == null) {
			if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindZoom.keyCode)) {
				var3 /= 4.0F;
			}
		}

		if(var2.health <= 0) {
			float var4 = (float)var2.deathTime + var1;
			var3 /= (1.0F - 500.0F / (var4 + 500.0F)) * 2.0F + 1.0F;
		}

		return var3;
	}

	private void hurtCameraEffect(float var1) {
		EntityPlayerSP var2 = this.mc.thePlayer;
		float var3 = (float)var2.hurtTime - var1;
		float var4;
		if(var2.health <= 0) {
			var4 = (float)var2.deathTime + var1;
			GL11.glRotatef(40.0F - 8000.0F / (var4 + 200.0F), 0.0F, 0.0F, 1.0F);
		}

		if(var3 >= 0.0F) {
			var3 /= (float)var2.field_9332_M;
			var3 = MathHelper.sin(var3 * var3 * var3 * var3 * (float)Math.PI);
			var4 = var2.field_9331_N;
			GL11.glRotatef(-var4, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-var3 * 14.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(var4, 0.0F, 1.0F, 0.0F);
		}
	}

	private void func_917_f(float var1) {
		if(!this.mc.gameSettings.thirdPersonView) {
			EntityPlayerSP var2 = this.mc.thePlayer;
			float var3 = var2.field_9290_aS - var2.field_9291_aR;
			float var4 = var2.field_9290_aS + var3 * var1;
			float var5 = var2.field_775_e + (var2.field_774_f - var2.field_775_e) * var1;
			float var6 = var2.field_9329_Q + (var2.field_9328_R - var2.field_9329_Q) * var1;
			GL11.glTranslatef(MathHelper.sin(var4 * (float)Math.PI) * var5 * 0.5F, -Math.abs(MathHelper.cos(var4 * (float)Math.PI) * var5), 0.0F);
			GL11.glRotatef(MathHelper.sin(var4 * (float)Math.PI) * var5 * 3.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(Math.abs(MathHelper.cos(var4 * (float)Math.PI + 0.2F) * var5) * 5.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(var6, 1.0F, 0.0F, 0.0F);
		}
	}

	private void orientCamera(float var1) {
		EntityPlayerSP var2 = this.mc.thePlayer;
		double var3 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)var1;
		double var5 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)var1;
		double var7 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)var1;
		if(this.mc.gameSettings.thirdPersonView) {
			double var9 = 4.0D;
			float var11 = var2.rotationYaw;
			float var12 = var2.rotationPitch;
			if(Keyboard.isKeyDown(Keyboard.KEY_F1)) {
				var12 += 180.0F;
				var9 += 2.0D;
			}

			double var13 = (double)(-MathHelper.sin(var11 / 180.0F * (float)Math.PI) * MathHelper.cos(var12 / 180.0F * (float)Math.PI)) * var9;
			double var15 = (double)(MathHelper.cos(var11 / 180.0F * (float)Math.PI) * MathHelper.cos(var12 / 180.0F * (float)Math.PI)) * var9;
			double var17 = (double)(-MathHelper.sin(var12 / 180.0F * (float)Math.PI)) * var9;

			for(int var19 = 0; var19 < 8; ++var19) {
				float var20 = (float)((var19 & 1) * 2 - 1);
				float var21 = (float)((var19 >> 1 & 1) * 2 - 1);
				float var22 = (float)((var19 >> 2 & 1) * 2 - 1);
				var20 *= 0.1F;
				var21 *= 0.1F;
				var22 *= 0.1F;
				MovingObjectPosition var23 = this.mc.theWorld.rayTraceBlocks(Vec3D.createVector(var3 + (double)var20, var5 + (double)var21, var7 + (double)var22), Vec3D.createVector(var3 - var13 + (double)var20 + (double)var22, var5 - var17 + (double)var21, var7 - var15 + (double)var22));
				if(var23 != null) {
					double var24 = var23.hitVec.distanceTo(Vec3D.createVector(var3, var5, var7));
					if(var24 < var9) {
						var9 = var24;
					}
				}
			}

			if(Keyboard.isKeyDown(Keyboard.KEY_F1)) {
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			}

			GL11.glRotatef(var2.rotationPitch - var12, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(var2.rotationYaw - var11, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, 0.0F, (float)(-var9));
			GL11.glRotatef(var11 - var2.rotationYaw, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(var12 - var2.rotationPitch, 1.0F, 0.0F, 0.0F);
		} else {
			GL11.glTranslatef(0.0F, 0.0F, -0.1F);
		}

		GL11.glRotatef(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * var1, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * var1 + 180.0F, 0.0F, 1.0F, 0.0F);
	}

	private void setupCameraTransform(float var1, int var2) {
		this.field_1387_i = (float)(256 >> this.mc.gameSettings.renderDistance);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		float var3 = 0.07F;
		if(this.mc.gameSettings.anaglyph) {
			GL11.glTranslatef((float)(-(var2 * 2 - 1)) * var3, 0.0F, 0.0F);
		}

		GLU.gluPerspective(this.func_914_d(var1), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.field_1387_i);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		if(this.mc.gameSettings.anaglyph) {
			GL11.glTranslatef((float)(var2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
		}

		this.hurtCameraEffect(var1);
		if(this.mc.gameSettings.viewBobbing) {
			this.func_917_f(var1);
		}

		float var4 = this.mc.thePlayer.field_4133_d + (this.mc.thePlayer.field_4134_c - this.mc.thePlayer.field_4133_d) * var1;
		if(var4 > 0.0F) {
			float var5 = 5.0F / (var4 * var4 + 5.0F) - var4 * 0.04F;
			var5 *= var5;
			GL11.glRotatef(var4 * var4 * 1500.0F, 0.0F, 1.0F, 1.0F);
			GL11.glScalef(1.0F / var5, 1.0F, 1.0F);
			GL11.glRotatef(-var4 * var4 * 1500.0F, 0.0F, 1.0F, 1.0F);
		}

		this.orientCamera(var1);
	}

	private void func_4135_b(float var1, int var2) {
		GL11.glLoadIdentity();
		if(this.mc.gameSettings.anaglyph) {
			GL11.glTranslatef((float)(var2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
		}

		GL11.glPushMatrix();
		this.hurtCameraEffect(var1);
		if(this.mc.gameSettings.viewBobbing) {
			this.func_917_f(var1);
		}

		if(!this.mc.gameSettings.thirdPersonView && !Keyboard.isKeyDown(Keyboard.KEY_F1)) {
			this.field_1395_a.renderItemInFirstPerson(var1);
		}

		GL11.glPopMatrix();
		if(!this.mc.gameSettings.thirdPersonView) {
			this.field_1395_a.renderOverlays(var1);
			this.hurtCameraEffect(var1);
		}

		if(this.mc.gameSettings.viewBobbing) {
			this.func_917_f(var1);
		}

	}

	public void func_4136_b(float var1) {
		if(!Display.isActive()) {
			if(System.currentTimeMillis() - this.field_1384_l > 500L) {
				this.mc.func_6252_g();
			}
		} else {
			this.field_1384_l = System.currentTimeMillis();
		}

		if(this.mc.field_6289_L) {
			this.mc.mouseHelper.func_772_c();
			float var2 = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
			float var3 = var2 * var2 * var2 * 8.0F;
			float var4 = (float)this.mc.mouseHelper.field_1114_a * var3;
			float var5 = (float)this.mc.mouseHelper.field_1113_b * var3;
			byte var6 = 1;
			if(this.mc.gameSettings.invertMouse) {
				var6 = -1;
			}

			this.mc.thePlayer.func_346_d(var4, var5 * (float)var6);
		}

		if(!this.mc.field_6307_v) {
			ScaledResolution var7 = new ScaledResolution(this.mc.displayWidth, this.mc.displayHeight);
			int var8 = var7.getScaledWidth();
			int var9 = var7.getScaledHeight();
			int var10 = Mouse.getX() * var8 / this.mc.displayWidth;
			int var11 = var9 - Mouse.getY() * var9 / this.mc.displayHeight - 1;
			if(this.mc.theWorld != null) {
				this.func_4134_c(var1);
				if(!Keyboard.isKeyDown(Keyboard.KEY_F1)) {
					this.mc.ingameGUI.renderGameOverlay(var1, this.mc.currentScreen != null, var10, var11);
				}
			} else {
				GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
				GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
				this.func_905_b();
			}

			if(this.mc.currentScreen != null) {
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
				this.mc.currentScreen.drawScreen(var10, var11, var1);
			}

		}
	}

	public void func_4134_c(float var1) {
		this.func_910_a(var1);
		EntityPlayerSP var2 = this.mc.thePlayer;
		RenderGlobal var3 = this.mc.field_6323_f;
		EffectRenderer var4 = this.mc.field_6321_h;
		double var5 = var2.lastTickPosX + (var2.posX - var2.lastTickPosX) * (double)var1;
		double var7 = var2.lastTickPosY + (var2.posY - var2.lastTickPosY) * (double)var1;
		double var9 = var2.lastTickPosZ + (var2.posZ - var2.lastTickPosZ) * (double)var1;

		for(int var11 = 0; var11 < 2; ++var11) {
			if(this.mc.gameSettings.anaglyph) {
				if(var11 == 0) {
					GL11.glColorMask(false, true, true, false);
				} else {
					GL11.glColorMask(true, false, false, false);
				}
			}

			GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
			this.updateFogColor(var1);
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
			GL11.glEnable(GL11.GL_CULL_FACE);
			this.setupCameraTransform(var1, var11);
			ClippingHelperImplementation.func_1155_a();
			if(this.mc.gameSettings.renderDistance < 2) {
				this.func_4140_a(-1);
				var3.func_4142_a(var1);
			}

			GL11.glEnable(GL11.GL_FOG);
			this.func_4140_a(1);
			Frustrum var12 = new Frustrum();
			var12.func_343_a(var5, var7, var9);
			this.mc.field_6323_f.func_960_a(var12, var1);
			this.mc.field_6323_f.func_948_a(var2, false);
			this.func_4140_a(0);
			GL11.glEnable(GL11.GL_FOG);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
			RenderHelper.disableStandardItemLighting();
			var3.func_943_a(var2, 0, (double)var1);
			RenderHelper.enableStandardItemLighting();
			var3.func_951_a(var2.getPosition(var1), var12, var1);
			var4.func_1187_b(var2, var1);
			RenderHelper.disableStandardItemLighting();
			this.func_4140_a(0);
			var4.func_1189_a(var2, var1);
			if(this.mc.objectMouseOver != null && var2.isInsideOfMaterial(Material.water)) {
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				var3.func_959_a(var2, this.mc.objectMouseOver, 0, var2.inventory.getCurrentItem(), var1);
				var3.drawSelectionBox(var2, this.mc.objectMouseOver, 0, var2.inventory.getCurrentItem(), var1);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
			}

			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			this.func_4140_a(0);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
			if(this.mc.gameSettings.fancyGraphics) {
				int var13 = var3.func_943_a(var2, 1, (double)var1);
				if(this.mc.gameSettings.anaglyph) {
					if(var11 == 0) {
						GL11.glColorMask(false, true, true, false);
					} else {
						GL11.glColorMask(true, false, false, false);
					}
				}

				if(var13 > 0) {
					var3.func_944_a(1, (double)var1);
				}
			} else {
				var3.func_943_a(var2, 1, (double)var1);
			}

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			if(this.mc.objectMouseOver != null && !var2.isInsideOfMaterial(Material.water)) {
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				var3.func_959_a(var2, this.mc.objectMouseOver, 0, var2.inventory.getCurrentItem(), var1);
				var3.drawSelectionBox(var2, this.mc.objectMouseOver, 0, var2.inventory.getCurrentItem(), var1);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
			}

			GL11.glDisable(GL11.GL_FOG);
			if(this.field_1385_k != null) {
			}

			this.func_4140_a(0);
			GL11.glEnable(GL11.GL_FOG);
			var3.func_4141_b(var1);
			GL11.glDisable(GL11.GL_FOG);
			this.func_4140_a(1);
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			this.func_4135_b(var1, var11);
			if(!this.mc.gameSettings.anaglyph) {
				return;
			}
		}

		GL11.glColorMask(true, true, true, false);
	}

	private void func_916_c() {
		if(this.mc.gameSettings.fancyGraphics) {
			EntityPlayerSP var1 = this.mc.thePlayer;
			World var2 = this.mc.theWorld;
			int var3 = MathHelper.floor_double(var1.posX);
			int var4 = MathHelper.floor_double(var1.posY);
			int var5 = MathHelper.floor_double(var1.posZ);
			byte var6 = 16;

			for(int var7 = 0; var7 < 150; ++var7) {
				int var8 = var3 + this.field_1383_m.nextInt(var6) - this.field_1383_m.nextInt(var6);
				int var9 = var5 + this.field_1383_m.nextInt(var6) - this.field_1383_m.nextInt(var6);
				int var10 = var2.func_696_e(var8, var9);
				int var11 = var2.getBlockId(var8, var10 - 1, var9);
				if(var10 <= var4 + var6 && var10 >= var4 - var6) {
					float var12 = this.field_1383_m.nextFloat();
					float var13 = this.field_1383_m.nextFloat();
					if(var11 > 0) {
						this.mc.field_6321_h.func_1192_a(new EntityRainFX(var2, (double)((float)var8 + var12), (double)((float)var10 + 0.1F) - Block.blocksList[var11].minY, (double)((float)var9 + var13)));
					}
				}
			}

		}
	}

	public void func_905_b() {
		ScaledResolution var1 = new ScaledResolution(this.mc.displayWidth, this.mc.displayHeight);
		int var2 = var1.getScaledWidth();
		int var3 = var1.getScaledHeight();
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, (double)var2, (double)var3, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
	}

	private void updateFogColor(float var1) {
		World var2 = this.mc.theWorld;
		EntityPlayerSP var3 = this.mc.thePlayer;
		float var4 = 1.0F / (float)(4 - this.mc.gameSettings.renderDistance);
		var4 = 1.0F - (float)Math.pow((double)var4, 0.25D);
		Vec3D var5 = var2.func_4079_a(this.mc.thePlayer, var1);
		float var6 = (float)var5.xCoord;
		float var7 = (float)var5.yCoord;
		float var8 = (float)var5.zCoord;
		Vec3D var9 = var2.func_4082_d(var1);
		this.field_4270_e = (float)var9.xCoord;
		this.field_4269_f = (float)var9.yCoord;
		this.field_4268_g = (float)var9.zCoord;
		this.field_4270_e += (var6 - this.field_4270_e) * var4;
		this.field_4269_f += (var7 - this.field_4269_f) * var4;
		this.field_4268_g += (var8 - this.field_4268_g) * var4;
		if(var3.isInsideOfMaterial(Material.water)) {
			this.field_4270_e = 0.02F;
			this.field_4269_f = 0.02F;
			this.field_4268_g = 0.2F;
		} else if(var3.isInsideOfMaterial(Material.lava)) {
			this.field_4270_e = 0.6F;
			this.field_4269_f = 0.1F;
			this.field_4268_g = 0.0F;
		}

		float var10 = this.field_1382_n + (this.field_1381_o - this.field_1382_n) * var1;
		this.field_4270_e *= var10;
		this.field_4269_f *= var10;
		this.field_4268_g *= var10;
		if(this.mc.gameSettings.anaglyph) {
			float var11 = (this.field_4270_e * 30.0F + this.field_4269_f * 59.0F + this.field_4268_g * 11.0F) / 100.0F;
			float var12 = (this.field_4270_e * 30.0F + this.field_4269_f * 70.0F) / 100.0F;
			float var13 = (this.field_4270_e * 30.0F + this.field_4268_g * 70.0F) / 100.0F;
			this.field_4270_e = var11;
			this.field_4269_f = var12;
			this.field_4268_g = var13;
		}

		GL11.glClearColor(this.field_4270_e, this.field_4269_f, this.field_4268_g, 0.0F);
	}

	private void func_4140_a(int var1) {
		EntityPlayerSP var2 = this.mc.thePlayer;
		GL11.glFog(GL11.GL_FOG_COLOR, this.func_908_a(this.field_4270_e, this.field_4269_f, this.field_4268_g, 1.0F));
		GL11.glNormal3f(0.0F, -1.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var3;
		float var4;
		float var5;
		float var6;
		float var7;
		float var8;
		if(var2.isInsideOfMaterial(Material.water)) {
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 0.1F);
			var3 = 0.4F;
			var4 = 0.4F;
			var5 = 0.9F;
			if(this.mc.gameSettings.anaglyph) {
				var6 = (var3 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
				var7 = (var3 * 30.0F + var4 * 70.0F) / 100.0F;
				var8 = (var3 * 30.0F + var5 * 70.0F) / 100.0F;
			}
		} else if(var2.isInsideOfMaterial(Material.lava)) {
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 2.0F);
			var3 = 0.4F;
			var4 = 0.3F;
			var5 = 0.3F;
			if(this.mc.gameSettings.anaglyph) {
				var6 = (var3 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
				var7 = (var3 * 30.0F + var4 * 70.0F) / 100.0F;
				var8 = (var3 * 30.0F + var5 * 70.0F) / 100.0F;
			}
		} else {
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
			GL11.glFogf(GL11.GL_FOG_START, this.field_1387_i * 0.25F);
			GL11.glFogf(GL11.GL_FOG_END, this.field_1387_i);
			if(var1 < 0) {
				GL11.glFogf(GL11.GL_FOG_START, 0.0F);
				GL11.glFogf(GL11.GL_FOG_END, this.field_1387_i * 0.8F);
			}

			if(this.mc.theWorld.worldProvider.field_4220_c) {
				GL11.glFogf(GL11.GL_FOG_START, 0.0F);
			}
		}

		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT);
	}

	private FloatBuffer func_908_a(float var1, float var2, float var3, float var4) {
		this.field_1392_d.clear();
		this.field_1392_d.put(var1).put(var2).put(var3).put(var4);
		this.field_1392_d.flip();
		return this.field_1392_d;
	}
}
