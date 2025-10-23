package net.minecraft.src;

public class EntityZombieSimple extends EntityMobs {
	public EntityZombieSimple(World var1) {
		super(var1);
		this.texture = "/mob/zombie.png";
		this.field_9333_am = 0.5F;
		this.field_762_e = 50;
		this.health *= 10;
		this.yOffset *= 6.0F;
		this.setSize(this.width * 6.0F, this.height * 6.0F);
	}

	protected float func_439_a(int var1, int var2, int var3) {
		return this.worldObj.getLightBrightness(var1, var2, var3) - 0.5F;
	}
}
