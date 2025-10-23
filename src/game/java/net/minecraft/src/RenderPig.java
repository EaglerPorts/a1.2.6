package net.minecraft.src;

public class RenderPig extends RenderLiving {
	public RenderPig(ModelBase var1, ModelBase var2, float var3) {
		super(var1, var3);
		this.func_4013_a(var2);
	}

	protected boolean a(EntityPig var1, int var2) {
		this.loadTexture("/mob/saddle.png");
		return var2 == 0 && var1.rideable;
	}

	protected boolean func_166_a(EntityLiving var1, int var2) {
		return this.a((EntityPig)var1, var2);
	}
}
