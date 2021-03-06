package com.nr.mod.crafting;

import net.minecraft.item.ItemStack;

import com.nr.mod.items.NRItems;

public class OxidiserRecipes extends NRRecipeHelper {

	private static final OxidiserRecipes recipes = new OxidiserRecipes();

	public OxidiserRecipes(){
		super(2, 2);
	}
	public static final NRRecipeHelper instance() {
		return recipes;
	}

	public void addRecipes() {
		matMat(12, 4, 53);
		matMat(12, 19, 54);
		for (int i = 0; i < 7; i++) matMat(4, 24+(2*i), 55+(2*i));
		for (int i = 0; i < 7; i++) matMat(1, 25+(2*i), 56+(2*i));
		for (int i = 0; i < 4; i++) fuelFuel(32, i, 51+i);
		for (int i = 0; i < 4; i++) fuelFuel(32, 6+i, 55+i);
		for (int i = 0; i < 4; i++) fuelFuel(32, 11+i, 59+i);
		for (int i = 0; i < 4; i++) fuelFuel(32, 17+i, 63+i);
		for (int i = 0; i < 4; i++) fuelFuel(16, 22+i, 67+i);
		for (int i = 0; i < 4; i++) fuelFuel(16, 28+i, 71+i);
	}
	
	public void fuelFuel(int oxyNum, int metaIn, int metaOut) {
		addRecipe(new ItemStack(NRItems.fuel, 1, metaIn), new ItemStack(NRItems.fuel, oxyNum, 35), new ItemStack(NRItems.fuel, 1, metaOut), new ItemStack(NRItems.fuel, oxyNum, 45));
	}
	
	public void matMat(int oxyNum, int metaIn, int metaOut) {
		addRecipe(new ItemStack(NRItems.material, 1, metaIn), new ItemStack(NRItems.fuel, oxyNum, 35), new ItemStack(NRItems.material, 1, metaOut), new ItemStack(NRItems.fuel, oxyNum, 45));
	}
}