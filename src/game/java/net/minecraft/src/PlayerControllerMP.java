package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PlayerControllerMP extends PlayerController {
	private int field_9445_c = -1;
	private int field_9444_d = -1;
	private int field_9443_e = -1;
	private float field_9442_f = 0.0F;
	private float field_1080_g = 0.0F;
	private float field_9441_h = 0.0F;
	private int field_9440_i = 0;
	private boolean field_9439_j = false;
	private NetClientHandler field_9438_k;
	private int field_1075_l = 0;

	public PlayerControllerMP(Minecraft var1, NetClientHandler var2) {
		super(var1);
		this.field_9438_k = var2;
	}

	public void flipPlayer(EntityPlayer var1) {
		var1.rotationYaw = -180.0F;
	}

	public boolean sendBlockRemoved(int var1, int var2, int var3, int var4) {
		this.field_9438_k.addToSendQueue(new Packet14BlockDig(3, var1, var2, var3, var4));
		int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
		this.mc.theWorld.getBlockMetadata(var1, var2, var3);
		boolean var7 = super.sendBlockRemoved(var1, var2, var3, var4);
		ItemStack var8 = this.mc.thePlayer.getCurrentEquippedItem();
		if(var8 != null) {
			var8.hitBlock(var5, var1, var2, var3);
			if(var8.stackSize == 0) {
				var8.func_1097_a(this.mc.thePlayer);
				this.mc.thePlayer.destroyCurrentEquippedItem();
			}
		}

		return var7;
	}

	public void clickBlock(int var1, int var2, int var3, int var4) {
		this.field_9439_j = true;
		this.field_9438_k.addToSendQueue(new Packet14BlockDig(0, var1, var2, var3, var4));
		int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
		if(var5 > 0 && this.field_9442_f == 0.0F) {
			Block.blocksList[var5].onBlockClicked(this.mc.theWorld, var1, var2, var3, this.mc.thePlayer);
		}

		if(var5 > 0 && Block.blocksList[var5].func_225_a(this.mc.thePlayer) >= 1.0F) {
			this.sendBlockRemoved(var1, var2, var3, var4);
		}

	}

	public void func_6468_a() {
		if(this.field_9439_j) {
			this.field_9439_j = false;
			this.field_9438_k.addToSendQueue(new Packet14BlockDig(2, 0, 0, 0, 0));
			this.field_9442_f = 0.0F;
			this.field_9440_i = 0;
		}
	}

	public void sendBlockRemoving(int var1, int var2, int var3, int var4) {
		this.field_9439_j = true;
		this.func_730_e();
		this.field_9438_k.addToSendQueue(new Packet14BlockDig(1, var1, var2, var3, var4));
		if(this.field_9440_i > 0) {
			--this.field_9440_i;
		} else {
			if(var1 == this.field_9445_c && var2 == this.field_9444_d && var3 == this.field_9443_e) {
				int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
				if(var5 == 0) {
					return;
				}

				Block var6 = Block.blocksList[var5];
				this.field_9442_f += var6.func_225_a(this.mc.thePlayer);
				if(this.field_9441_h % 4.0F == 0.0F && var6 != null) {
					this.mc.sndManager.func_336_b(var6.stepSound.func_1145_d(), (float)var1 + 0.5F, (float)var2 + 0.5F, (float)var3 + 0.5F, (var6.stepSound.func_1147_b() + 1.0F) / 8.0F, var6.stepSound.func_1144_c() * 0.5F);
				}

				++this.field_9441_h;
				if(this.field_9442_f >= 1.0F) {
					this.sendBlockRemoved(var1, var2, var3, var4);
					this.field_9442_f = 0.0F;
					this.field_1080_g = 0.0F;
					this.field_9441_h = 0.0F;
					this.field_9440_i = 5;
				}
			} else {
				this.field_9442_f = 0.0F;
				this.field_1080_g = 0.0F;
				this.field_9441_h = 0.0F;
				this.field_9445_c = var1;
				this.field_9444_d = var2;
				this.field_9443_e = var3;
			}

		}
	}

	public void func_6467_a(float var1) {
		if(this.field_9442_f <= 0.0F) {
			this.mc.ingameGUI.field_6446_b = 0.0F;
			this.mc.field_6323_f.field_1450_i = 0.0F;
		} else {
			float var2 = this.field_1080_g + (this.field_9442_f - this.field_1080_g) * var1;
			this.mc.ingameGUI.field_6446_b = var2;
			this.mc.field_6323_f.field_1450_i = var2;
		}

	}

	public float getBlockReachDistance() {
		return 4.0F;
	}

	public void func_717_a(World var1) {
		super.func_717_a(var1);
	}

	public void func_6474_c() {
		this.func_730_e();
		this.field_1080_g = this.field_9442_f;
		this.mc.sndManager.func_4033_c();
	}

	private void func_730_e() {
		ItemStack var1 = this.mc.thePlayer.inventory.getCurrentItem();
		int var2 = 0;
		if(var1 != null) {
			var2 = var1.itemID;
		}

		if(var2 != this.field_1075_l) {
			this.field_1075_l = var2;
			this.field_9438_k.addToSendQueue(new Packet16BlockItemSwitch(0, this.field_1075_l));
		}

	}

	public boolean sendPlaceBlock(EntityPlayer var1, World var2, ItemStack var3, int var4, int var5, int var6, int var7) {
		this.func_730_e();
		this.field_9438_k.addToSendQueue(new Packet15Place(var3 != null ? var3.itemID : -1, var4, var5, var6, var7));
		return super.sendPlaceBlock(var1, var2, var3, var4, var5, var6, var7);
	}

	public boolean sendUseItem(EntityPlayer var1, World var2, ItemStack var3) {
		this.func_730_e();
		this.field_9438_k.addToSendQueue(new Packet15Place(var3 != null ? var3.itemID : -1, -1, -1, -1, 255));
		return super.sendUseItem(var1, var2, var3);
	}

	public EntityPlayer func_4087_b(World var1) {
		return new EntityClientPlayerMP(this.mc, var1, this.mc.field_6320_i, this.field_9438_k);
	}

	public void func_6472_b(EntityPlayer var1, Entity var2) {
		this.func_730_e();
		this.field_9438_k.addToSendQueue(new Packet7(var1.field_620_ab, var2.field_620_ab, 1));
		var1.attackTargetEntityWithCurrentItem(var2);
	}

	public void func_6475_a(EntityPlayer var1, Entity var2) {
		this.func_730_e();
		this.field_9438_k.addToSendQueue(new Packet7(var1.field_620_ab, var2.field_620_ab, 0));
		var1.func_6415_a_(var2);
	}
}
