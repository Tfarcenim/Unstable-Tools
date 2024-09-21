package tfar.unstabletools.datagen.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ShapedRecipeBuilderCustom extends ShapedRecipeBuilder {

    private RecipeSerializer<?> serializer;

    public ShapedRecipeBuilderCustom(RecipeCategory pCategory, ItemLike pResult, int pCount) {
        super(pCategory, pResult, pCount);
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static ShapedRecipeBuilderCustom customShaped(RecipeCategory pCategory, ItemLike pResult) {
        return customShaped(pCategory, pResult, 1);
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static ShapedRecipeBuilderCustom customShaped(RecipeCategory pCategory, ItemLike pResult, int pCount) {
        return new ShapedRecipeBuilderCustom(pCategory, pResult, pCount);
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public ShapedRecipeBuilderCustom define(Character pSymbol, TagKey<Item> pTag) {
        return (ShapedRecipeBuilderCustom) super.define(pSymbol, pTag);
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public ShapedRecipeBuilderCustom define(Character pSymbol, ItemLike pItem) {
        return (ShapedRecipeBuilderCustom) super.define(pSymbol, pItem);
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public ShapedRecipeBuilderCustom define(Character pSymbol, Ingredient pIngredient) {
        return (ShapedRecipeBuilderCustom) super.define(pSymbol,pIngredient);
    }

    /**
     * Adds a new entry to the patterns for this recipe.
     */
    public ShapedRecipeBuilderCustom pattern(String pPattern) {
        return (ShapedRecipeBuilderCustom) super.pattern(pPattern);
    }

    public ShapedRecipeBuilderCustom unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        return (ShapedRecipeBuilderCustom) super.unlockedBy(pCriterionName, pCriterionTrigger);
    }

    public ShapedRecipeBuilderCustom group(@Nullable String pGroupName) {
        return (ShapedRecipeBuilderCustom) super.group(pGroupName);
    }

    public ShapedRecipeBuilderCustom showNotification(boolean pShowNotification) {
        return (ShapedRecipeBuilderCustom) super.showNotification(pShowNotification);
    }

    public ShapedRecipeBuilderCustom serializer(RecipeSerializer<?> serializer) {
        this.serializer = serializer;
        return this;
    }

    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new CustomResult(pRecipeId, this.result, this.count, this.group == null ? "" : this.group, determineBookCategory(this.category), this.rows, this.key, this.advancement, pRecipeId.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.showNotification, serializer));
    }

    public static class CustomResult extends Result {
        private final RecipeSerializer<?> serializer;

        public CustomResult(ResourceLocation pId, Item pResult, int pCount, String pGroup, CraftingBookCategory pCategory, List<String> pPattern, Map<Character, Ingredient> pKey, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId, boolean pShowNotification,RecipeSerializer<?> serializer) {
            super(pId, pResult, pCount, pGroup, pCategory, pPattern, pKey, pAdvancement, pAdvancementId, pShowNotification);
            this.serializer = serializer;
        }

        public RecipeSerializer<?> getType() {
            return serializer;
        }
    }
}