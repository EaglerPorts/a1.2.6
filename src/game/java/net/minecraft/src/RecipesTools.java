package net.minecraft.src;

public class RecipesTools {
	private String[][] field_1665_a = new String[][]{{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", " #", " #"}};
	private Object[][] field_1664_b = new Object[][]{{Block.planks, Block.cobblestone, Item.ingotIron, Item.diamond, Item.ingotGold}, {Item.pickaxeWood, Item.pickaxeStone, Item.pickaxeSteel, Item.pickaxeDiamond, Item.pickaxeGold}, {Item.shovelWood, Item.shovelStone, Item.shovelSteel, Item.shovelDiamond, Item.shovelGold}, {Item.axeWood, Item.axeStone, Item.axeSteel, Item.axeDiamond, Item.axeGold}, {Item.hoeWood, Item.hoeStone, Item.hoeSteel, Item.hoeDiamond, Item.hoeGold}};

	public void func_1122_a(CraftingManager var1) {
		for(int var2 = 0; var2 < this.field_1664_b[0].length; ++var2) {
			Object var3 = this.field_1664_b[0][var2];

			for(int var4 = 0; var4 < this.field_1664_b.length - 1; ++var4) {
				Item var5 = (Item)this.field_1664_b[var4 + 1][var2];
				var1.addRecipe(new ItemStack(var5), new Object[]{this.field_1665_a[var4], Character.valueOf('#'), Item.stick, Character.valueOf('X'), var3});
			}
		}

	}
}
