package net.minecraft.src;

public class EntityFallingSand extends Entity {
	public int field_799_a;
	public int field_798_b = 0;

	public EntityFallingSand(World var1) {
		super(var1);
	}

	public EntityFallingSand(World var1, float var2, float var3, float var4, int var5) {
		super(var1);
		this.field_799_a = var5;
		this.field_618_ad = true;
		this.setSize(0.98F, 0.98F);
		this.yOffset = this.height / 2.0F;
		this.setPosition((double)var2, (double)var3, (double)var4);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.entityWalks = false;
		this.prevPosX = (double)var2;
		this.prevPosY = (double)var3;
		this.prevPosZ = (double)var4;
	}

	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	public void onUpdate() {
		if(this.field_799_a == 0) {
			this.setEntityDead();
		} else {
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			++this.field_798_b;
			this.motionY -= (double)0.04F;
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= (double)0.98F;
			this.motionY *= (double)0.98F;
			this.motionZ *= (double)0.98F;
			int var1 = MathHelper.floor_double(this.posX);
			int var2 = MathHelper.floor_double(this.posY);
			int var3 = MathHelper.floor_double(this.posZ);
			if(this.worldObj.getBlockId(var1, var2, var3) == this.field_799_a) {
				this.worldObj.setBlockWithNotify(var1, var2, var3, 0);
			}

			if(this.onGround) {
				this.motionX *= (double)0.7F;
				this.motionZ *= (double)0.7F;
				this.motionY *= -0.5D;
				this.setEntityDead();
				if(!this.worldObj.canBlockBePlacedAt(this.field_799_a, var1, var2, var3, true) || !this.worldObj.setBlockWithNotify(var1, var2, var3, this.field_799_a)) {
					this.dropItem(this.field_799_a, 1);
				}
			} else if(this.field_798_b > 100) {
				this.dropItem(this.field_799_a, 1);
				this.setEntityDead();
			}

		}
	}

	protected void writeEntityToNBT(NBTTagCompound var1) {
		var1.setByte("Tile", (byte)this.field_799_a);
	}

	protected void readEntityFromNBT(NBTTagCompound var1) {
		this.field_799_a = var1.getByte("Tile") & 255;
	}

	public float func_392_h_() {
		return 0.0F;
	}

	public World func_465_i() {
		return this.worldObj;
	}
}
