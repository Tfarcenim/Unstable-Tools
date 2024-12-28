package tfar.unstabletools.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import tfar.unstabletools.crafting.DivisionRecipe;

public class ModRecipeSerializer {
    public static final RecipeSerializer<?> division = new DivisionRecipe.DivisionRecipeSerializer();
}
