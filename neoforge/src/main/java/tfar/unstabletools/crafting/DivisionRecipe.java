package tfar.unstabletools.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import tfar.unstabletools.Config;
import tfar.unstabletools.init.ModRecipeSerializer;
import tfar.unstabletools.item.UnstableIngotItem;
import tfar.unstabletools.mixin.TransientCraftingContainerAccess;

import javax.annotation.Nonnull;

public class DivisionRecipe extends ShapedRecipe {

  public DivisionRecipe(ShapedRecipe recipe) {
    super(recipe.getId(), "division",recipe.category(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getResultItem(null));
  }


  /**
   * Used to check if a recipe matches current crafting inventory
   *
   * @param inv
   * @param worldIn
   */
  @Override
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
    }

  @Override
  public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
    ItemStack assemble = super.assemble(pContainer, pRegistryAccess);
    assemble.getOrCreateTag().putInt(UnstableIngotItem.TIMER,Config.ServerConfig.timer.get());
    return assemble;
  }

  @Nonnull
  @Override
  public RecipeSerializer<?> getSerializer() {
    return ModRecipeSerializer.division;
  }

  public static class DivisionRecipeSerializer extends ShapedRecipe.Serializer {

    @Override
    public DivisionRecipe fromJson(ResourceLocation location, JsonObject json) {
      return new DivisionRecipe(super.fromJson(location, json));
    }


    @Override
    public DivisionRecipe fromNetwork(@Nonnull ResourceLocation p_199426_1_, FriendlyByteBuf p_199426_2_) {
      return new DivisionRecipe(super.fromNetwork(p_199426_1_, p_199426_2_));
    }
  }
}


