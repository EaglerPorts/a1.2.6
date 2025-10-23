package net.minecraft.src;

import java.util.Comparator;

class RecipeSorter implements Comparator {
	final CraftingManager field_1557_a;

	RecipeSorter(CraftingManager var1) {
		this.field_1557_a = var1;
	}

	public int a(CraftingRecipe var1, CraftingRecipe var2) {
		return var2.getRecipeSize() < var1.getRecipeSize() ? -1 : (var2.getRecipeSize() > var1.getRecipeSize() ? 1 : 0);
	}

	public int compare(Object var1, Object var2) {
		return this.a((CraftingRecipe)var1, (CraftingRecipe)var2);
	}
}
