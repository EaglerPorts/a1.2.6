package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class EntityClientPlayerMP extends EntityPlayerSP {
	private NetClientHandler field_797_bg;
	private int field_9380_bx = 0;
	private double field_9379_by;
	private double field_9378_bz;
	private double field_9377_bA;
	private double field_9376_bB;
	private float field_9385_bC;
	private float field_9384_bD;
	private InventoryPlayer field_9383_bE = new InventoryPlayer((EntityPlayer)null);
	private boolean field_9382_bF = false;
	private boolean field_9381_bG = false;
	private int field_12242_bI = 0;

	public EntityClientPlayerMP(Minecraft var1, World var2, Session var3, NetClientHandler var4) {
		super(var1, var2, var3, 0);
		this.field_797_bg = var4;
	}

	public boolean canAttackEntity(Entity var1, int var2) {
		return false;
	}

	public void heal(int var1) {
	}

	public void onUpdate() {
		if(this.worldObj.blockExists(MathHelper.floor_double(this.posX), 64, MathHelper.floor_double(this.posZ))) {
			super.onUpdate();
			this.func_4056_N();
		}
	}

	public void func_6420_o() {
	}

	public void func_4056_N() {
		if(this.field_9380_bx++ == 20) {
			this.sendInventoryChanged();
			this.field_9380_bx = 0;
		}

		boolean var1 = this.func_381_o();
		if(var1 != this.field_9381_bG) {
			if(var1) {
				this.field_797_bg.addToSendQueue(new Packet18ArmAnimation(this, 104));
			} else {
				this.field_797_bg.addToSendQueue(new Packet18ArmAnimation(this, 105));
			}

			this.field_9381_bG = var1;
		}

		double var2 = this.posX - this.field_9379_by;
		double var4 = this.boundingBox.minY - this.field_9378_bz;
		double var6 = this.posY - this.field_9377_bA;
		double var8 = this.posZ - this.field_9376_bB;
		double var10 = (double)(this.rotationYaw - this.field_9385_bC);
		double var12 = (double)(this.rotationPitch - this.field_9384_bD);
		boolean var14 = var4 != 0.0D || var6 != 0.0D || var2 != 0.0D || var8 != 0.0D;
		boolean var15 = var10 != 0.0D || var12 != 0.0D;
		if(this.ridingEntity != null) {
			if(var15) {
				this.field_797_bg.addToSendQueue(new Packet11PlayerPosition(this.motionX, -999.0D, -999.0D, this.motionZ, this.onGround));
			} else {
				this.field_797_bg.addToSendQueue(new Packet13PlayerLookMove(this.motionX, -999.0D, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
			}

			var14 = false;
		} else if(var14 && var15) {
			this.field_797_bg.addToSendQueue(new Packet13PlayerLookMove(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
			this.field_12242_bI = 0;
		} else if(var14) {
			this.field_797_bg.addToSendQueue(new Packet11PlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.onGround));
			this.field_12242_bI = 0;
		} else if(var15) {
			this.field_797_bg.addToSendQueue(new Packet12PlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
			this.field_12242_bI = 0;
		} else {
			this.field_797_bg.addToSendQueue(new Packet10Flying(this.onGround));
			if(this.field_9382_bF == this.onGround && this.field_12242_bI <= 20) {
				++this.field_12242_bI;
			} else {
				this.field_12242_bI = 0;
			}
		}

		this.field_9382_bF = this.onGround;
		if(var14) {
			this.field_9379_by = this.posX;
			this.field_9378_bz = this.boundingBox.minY;
			this.field_9377_bA = this.posY;
			this.field_9376_bB = this.posZ;
		}

		if(var15) {
			this.field_9385_bC = this.rotationYaw;
			this.field_9384_bD = this.rotationPitch;
		}

	}

	private void sendInventoryChanged() {
		if(!this.inventory.compareInventory(this.field_9383_bE)) {
			this.field_797_bg.addToSendQueue(new Packet5PlayerInventory(-1, this.inventory.mainInventory));
			this.field_797_bg.addToSendQueue(new Packet5PlayerInventory(-2, this.inventory.craftingInventory));
			this.field_797_bg.addToSendQueue(new Packet5PlayerInventory(-3, this.inventory.armorInventory));
			this.field_9383_bE = this.inventory.copyInventory();
		}

	}

	protected void joinEntityItemWithWorld(EntityItem var1) {
		Packet21PickupSpawn var2 = new Packet21PickupSpawn(var1);
		this.field_797_bg.addToSendQueue(var2);
		var1.posX = (double)var2.xPosition / 32.0D;
		var1.posY = (double)var2.yPosition / 32.0D;
		var1.posZ = (double)var2.zPosition / 32.0D;
		var1.motionX = (double)var2.rotation / 128.0D;
		var1.motionY = (double)var2.pitch / 128.0D;
		var1.motionZ = (double)var2.roll / 128.0D;
	}

	public void sendChatMessage(String var1) {
		this.field_797_bg.addToSendQueue(new Packet3Chat(var1));
	}

	public void func_457_w() {
		super.func_457_w();
		this.field_797_bg.addToSendQueue(new Packet18ArmAnimation(this, 1));
	}

	public void func_9367_r() {
		this.sendInventoryChanged();
		this.field_797_bg.addToSendQueue(new Packet9());
	}

	protected void damageEntity(int var1) {
		this.health -= var1;
	}
}
