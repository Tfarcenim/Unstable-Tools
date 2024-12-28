package tfar.unstabletools.datagen.data;

import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import tfar.unstabletools.crafting.DivisionRecipe;

import javax.annotation.Nullable;
import java.util.Objects;

public class ShapedRecipeBuilderCustom extends ShapedRecipeBuilder {

    private RecipeSerializer<?> serializer;

    public ShapedRecipeBuilderCustom(RecipeCategory pCategory, ItemStack stack) {
        super(pCategory, stack);
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static ShapedRecipeBuilderCustom customShaped(RecipeCategory pCategory, ItemStack stack) {
        return new ShapedRecipeBuilderCustom(pCategory, stack);
    }

    /**
     * Adds a key to the recipe pattern.
     */
    @Override
    public ShapedRecipeBuilderCustom define(Character pSymbol, TagKey<Item> pTag) {
        return (ShapedRecipeBuilderCustom) super.define(pSymbol, pTag);
    }

    /**
     * Adds a key to the recipe pattern.
     */
    @Override
    public ShapedRecipeBuilderCustom define(Character pSymbol, ItemLike pItem) {
        return (ShapedRecipeBuilderCustom) super.define(pSymbol, pItem);
    }

    /**
     * Adds a key to the recipe pattern.
     */
    @Override
    public ShapedRecipeBuilderCustom define(Character pSymbol, Ingredient pIngredient) {
        return (ShapedRecipeBuilderCustom) super.define(pSymbol,pIngredient);
    }

    /**
     * Adds a new entry to the patterns for this recipe.
     */
    @Override
    public ShapedRecipeBuilderCustom pattern(String pPattern) {
        return (ShapedRecipeBuilderCustom) super.pattern(pPattern);
    }

    @Override
    public ShapedRecipeBuilderCustom unlockedBy(String pName, Criterion<?> pCriterion) {
        return (ShapedRecipeBuilderCustom) super.unlockedBy(pName, pCriterion);
    }

    @Override
    public ShapedRecipeBuilderCustom group(@Nullable String pGroupName) {
        return (ShapedRecipeBuilderCustom) super.group(pGroupName);
    }

    @Override
    public ShapedRecipeBuilderCustom showNotification(boolean pShowNotification) {
        return (ShapedRecipeBuilderCustom) super.showNotification(pShowNotification);
    }

    public ShapedRecipeBuilderCustom serializer(RecipeSerializer<?> serializer) {
        this.serializer = serializer;
        return this;
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        ShapedRecipePattern shapedrecipepattern = this.ensureValid(pId);
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        DivisionRecipe shapedrecipe = new DivisionRecipe(new ShapedRecipe(
                Objects.requireNonNullElse(this.group, ""),
                RecipeBuilder.determineBookCategory(this.category),
                shapedrecipepattern,
                this.resultStack,
                this.showNotification
        ));
        pRecipeOutput.accept(pId, shapedrecipe, advancement$builder.build(pId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

}