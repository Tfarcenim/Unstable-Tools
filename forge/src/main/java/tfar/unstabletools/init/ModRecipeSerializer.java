package tfar.unstabletools.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import tfar.unstabletools.crafting.DivisionRecipe;

public class ModRecipeSerializer {
    public static final RecipeSerializer<?> division = new DivisionRecipe.DivisionRecipeSerializer();
}
