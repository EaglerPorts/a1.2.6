package net.minecraft.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.lax1dude.eaglercraft.internal.vfs2.VFile2;
import net.minecraft.client.Minecraft;

public class TexturePackList {
	private List availableTexturePacks = new ArrayList();
	private TexturePackBase defaultTexturePack = new TexturePackDefault();
	public TexturePackBase selectedTexturePack;
	private Map field_6538_d = new HashMap();
	private Minecraft mc;
	private VFile2 texturePackDir;
	private String field_6535_g;

	public TexturePackList(Minecraft var1, VFile2 var2) {
		this.mc = var1;
		this.texturePackDir = new VFile2(var2, "texturepacks");

		this.field_6535_g = var1.gameSettings.skin;
		this.func_6532_a();
		this.selectedTexturePack.func_6482_a();
	}

	public boolean setTexturePack(TexturePackBase var1) {
		if(var1 == this.selectedTexturePack) {
			return false;
		} else {
			this.selectedTexturePack.closeTexturePackFile();
			this.field_6535_g = var1.texturePackFileName;
			this.selectedTexturePack = var1;
			this.mc.gameSettings.skin = this.field_6535_g;
			this.mc.gameSettings.saveOptions();
			this.selectedTexturePack.func_6482_a();
			return true;
		}
	}

	public void func_6532_a() {
		ArrayList var1 = new ArrayList();
		this.selectedTexturePack = null;
		var1.add(this.defaultTexturePack);
		VFile2[] var2 = this.texturePackDir.listFiles();
		VFile2[] var3 = var2;
		int var4 = var2.length;

		for(int var5 = 0; var5 < var4; ++var5) {
			VFile2 var6 = var3[var5];
			if(var6.getName().toLowerCase().endsWith(".zip")) {
				String var7 = var6.getName() + ":" + var6.length() + ":" + 0L;

				try {
					if(!this.field_6538_d.containsKey(var7)) {
						TexturePackCustom var8 = new TexturePackCustom(var6);
						var8.field_6488_d = var7;
						this.field_6538_d.put(var7, var8);
						var8.func_6485_a(this.mc);
					}

					TexturePackBase var12 = (TexturePackBase)this.field_6538_d.get(var7);
					if(var12.texturePackFileName.equals(this.field_6535_g)) {
						this.selectedTexturePack = var12;
					}

					var1.add(var12);
				} catch (IOException var9) {
					var9.printStackTrace();
				}
			}
		}

		if(this.selectedTexturePack == null) {
			this.selectedTexturePack = this.defaultTexturePack;
		}

		this.availableTexturePacks.removeAll(var1);
		Iterator var10 = this.availableTexturePacks.iterator();

		while(var10.hasNext()) {
			TexturePackBase var11 = (TexturePackBase)var10.next();
			var11.func_6484_b(this.mc);
			this.field_6538_d.remove(var11.field_6488_d);
		}

		this.availableTexturePacks = var1;
	}

	public List availableTexturePacks() {
		return new ArrayList(this.availableTexturePacks);
	}
}
