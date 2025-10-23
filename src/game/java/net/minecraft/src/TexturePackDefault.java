package net.minecraft.src;

import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.opengl.ImageData;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class TexturePackDefault extends TexturePackBase {
	private int texturePackName = -1;
	private ImageData field_6490_f;

	public TexturePackDefault() {
		this.texturePackFileName = "Default";
		this.firstDescriptionLine = "The default look of Minecraft";

		this.field_6490_f = ImageData.loadImageFile(EagRuntime.getResourceStream("/pack.png"));

	}

	public void func_6484_b(Minecraft var1) {
		if(this.field_6490_f != null) {
			var1.renderEngine.deleteTexture(this.texturePackName);
		}

	}

	public void func_6483_c(Minecraft var1) {
		if(this.field_6490_f != null && this.texturePackName < 0) {
			this.texturePackName = var1.renderEngine.allocateAndSetupTexture(this.field_6490_f);
		}

		if(this.field_6490_f != null) {
			var1.renderEngine.bindTexture(this.texturePackName);
		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, var1.renderEngine.getTexture("/gui/unknown_pack.png"));
		}

	}
}
