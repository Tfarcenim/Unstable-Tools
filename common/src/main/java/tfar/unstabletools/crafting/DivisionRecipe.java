package tfar.unstabletools.crafting;

import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import tfar.unstabletools.init.ModRecipeSerializer;


public class DivisionRecipe extends ShapedRecipe {

  public DivisionRecipe(ShapedRecipe recipe) {
    super("division",recipe.category(), recipe.pattern,  recipe.getResultItem(null),recipe.showNotification());
  }


  /**
   * Used to check if a recipe matches current crafting inventory
   *
   * @param inv
   * @param worldIn
   */
/*  @Override
  public boolean matches(CraftingContainer inv, Level worldIn) {
    if (!(inv instanceof TransientCraftingContainer transientCraftingContainer)) return false;
    try {
      AbstractContainerMenu container = ((TransientCraftingContainerAccess)transientCraftingContainer).getMenu();
      MenuType<?> type = container.getType();//this will throw on certain inventories
      return Config.ServerConfig.allowed_containers.get().contains(BuiltInRegistries.MENU.getKey(type).toString()) && super.matches(inv, worldIn);
      } catch (Exception ohno) {
        //ohno.printStackTrace();
        return false;
      }
    }*/

  @Override
  public boolean matches(CraftingInput pInput, Level pLevel) {
    return super.matches(pInput, pLevel);
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return ModRecipeSerializer.division;
  }

  public static class DivisionRecipeSerializer extends ShapedRecipe.Serializer {


  }
}


