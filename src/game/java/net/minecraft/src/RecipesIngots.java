package net.minecraft.src;

public class RecipesIngots {
	private Object[][] field_1198_a = new Object[][]{{Block.blockGold, Item.ingotGold}, {Block.blockSteel, Item.ingotIron}, {Block.blockDiamond, Item.diamond}};

	public void func_810_a(CraftingManager var1) {
		for(int var2 = 0; var2 < this.field_1198_a.length; ++var2) {
			Block var3 = (Block)this.field_1198_a[var2][0];
			Item var4 = (Item)this.field_1198_a[var2][1];
			var1.addRecipe(new ItemStack(var3), new Object[]{"###", "###", "###", Character.valueOf('#'), var4});
			var1.addRecipe(new ItemStack(var4, 9), new Object[]{"#", Character.valueOf('#'), var3});
		}

	}
}
