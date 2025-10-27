package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class EntityPlayerSP extends EntityPlayer {
	public MovementInput field_787_a;
	private Minecraft mc;
	public int field_9373_b = 20;
	private boolean field_9374_bx = false;
	public float field_4134_c;
	public float field_4133_d;

	public EntityPlayerSP(Minecraft var1, World var2, Session var3, int var4) {
		super(var2);
		this.mc = var1;
		this.dimension = var4;

		this.field_771_i = var3.inventory;
	}

	public void func_418_b_() {
		super.func_418_b_();
		this.field_9342_ah = this.field_787_a.field_1174_a;
		this.field_9340_ai = this.field_787_a.field_1173_b;
		this.isJumping = this.field_787_a.field_1176_d;
	}

	public void onLivingUpdate() {
		this.field_4133_d = this.field_4134_c;
		if(this.field_9374_bx) {
			if(this.field_4134_c == 0.0F) {
				this.mc.sndManager.func_337_a("portal.trigger", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
			}

			this.field_4134_c += 0.0125F;
			if(this.field_4134_c >= 1.0F) {
				this.field_4134_c = 1.0F;
				this.field_9373_b = 10;
				this.mc.sndManager.func_337_a("portal.travel", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
				this.mc.func_6237_k();
			}

			this.field_9374_bx = false;
		} else {
			if(this.field_4134_c > 0.0F) {
				this.field_4134_c -= 0.05F;
			}

			if(this.field_4134_c < 0.0F) {
				this.field_4134_c = 0.0F;
			}
		}

		if(this.field_9373_b > 0) {
			--this.field_9373_b;
		}

		this.field_787_a.func_797_a(this);
		if(this.field_787_a.field_1175_e && this.field_9287_aY < 0.2F) {
			this.field_9287_aY = 0.2F;
		}

		super.onLivingUpdate();
	}

	public void func_458_k() {
		this.field_787_a.func_798_a();
	}

	public void func_460_a(int var1, boolean var2) {
		this.field_787_a.func_796_a(var1, var2);
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
		var1.setInteger("Score", this.score);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
		this.score = var1.getInteger("Score");
	}

	public void displayGUIChest(IInventory var1) {
		this.mc.displayGuiScreen(new GuiChest(this.inventory, var1));
	}

	public void displayGUIEditSign(TileEntitySign var1) {
		this.mc.displayGuiScreen(new GuiEditSign(var1));
	}

	public void displayWorkbenchGUI() {
		this.mc.displayGuiScreen(new GuiCrafting(this.inventory));
	}

	public void displayGUIFurnace(TileEntityFurnace var1) {
		this.mc.displayGuiScreen(new GuiFurnace(this.inventory, var1));
	}

	public void func_443_a_(Entity var1, int var2) {
		this.mc.field_6321_h.func_1192_a(new EntityPickupFX(this.mc.theWorld, var1, this, -0.5F));
	}

	public int getPlayerArmorValue() {
		return this.inventory.getTotalArmorValue();
	}

	public void func_6415_a_(Entity var1) {
		if(!var1.interact(this)) {
			ItemStack var2 = this.getCurrentEquippedItem();
			if(var2 != null && var1 instanceof EntityLiving) {
				var2.useItemOnEntity((EntityLiving)var1);
				if(var2.stackSize <= 0) {
					var2.func_1097_a(this);
					this.destroyCurrentEquippedItem();
				}
			}

		}
	}

	public void sendChatMessage(String var1) {
	}

	public void func_6420_o() {
	}

	public boolean func_381_o() {
		return this.field_787_a.field_1175_e;
	}

	public void func_4039_q() {
		if(this.field_9373_b > 0) {
			this.field_9373_b = 10;
		} else {
			this.field_9374_bx = true;
		}
	}

	public void setHealth(int var1) {
		int var2 = this.health - var1;
		if(var2 <= 0) {
			this.health = var1;
		} else {
			this.field_9346_af = var2;
			this.field_9335_K = this.health;
			this.field_9306_bj = this.field_9366_o;
			this.damageEntity(var2);
			this.hurtTime = this.field_9332_M = 10;
		}

	}

	public void func_9367_r() {
		this.mc.respawn();
	}
}
