package tfar.unstabletools.datagen.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import tfar.unstabletools.UnstableTools;
import tfar.unstabletools.init.ModBlocks;
import tfar.unstabletools.init.ModItems;
import tfar.unstabletools.init.ModRecipeSerializer;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
        public ModRecipeProvider(PackOutput output) {
                super(output);
        }

        @Override
        protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ethereal_glass, 8)
                                .define('G', ModItems.UNSTABLE_INGOT)
                                .define('P', Tags.Items.GLASS)
                                .pattern("PPP")
                                .pattern("PGP")
                                .pattern("PPP")
                                .unlockedBy("has_glass", has(Tags.Items.GLASS))
                                .save(consumer);

                String has = "has_unstable_ingot";

                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.UNSTABLE_AXE).define('#', Blocks.OBSIDIAN)
                                .define('X', ModItems.UNSTABLE_INGOT).pattern("XX").pattern("X#").pattern(" #")
                                .unlockedBy(has, has(ModItems.UNSTABLE_INGOT)).save(consumer);
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.UNSTABLE_BOOTS)
                                .define('X', ModItems.UNSTABLE_INGOT).pattern("X X").pattern("X X")
                                .unlockedBy(has, has(ModItems.UNSTABLE_INGOT)).save(consumer);
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.UNSTABLE_CHESTPLATE)
                                .define('X', ModItems.UNSTABLE_INGOT).pattern("X X").pattern("XXX").pattern("XXX")
                                .unlockedBy(has, has(ModItems.UNSTABLE_INGOT)).save(consumer);
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.UNSTABLE_HELMET)
                                .define('X', ModItems.UNSTABLE_INGOT).pattern("XXX").pattern("X X")
                                .unlockedBy(has, has(ModItems.UNSTABLE_INGOT)).save(consumer);
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.UNSTABLE_HOE).define('#', Blocks.OBSIDIAN)
                                .define('X', ModItems.UNSTABLE_INGOT).pattern("XX").pattern(" #").pattern(" #")
                                .unlockedBy(has, has(ModItems.UNSTABLE_INGOT)).save(consumer);
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.UNSTABLE_LEGGINGS)
                                .define('X', ModItems.UNSTABLE_INGOT).pattern("XXX").pattern("X X").pattern("X X")
                                .unlockedBy(has, has(ModItems.UNSTABLE_INGOT)).save(consumer);
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.UNSTABLE_PICKAXE).define('#', Blocks.OBSIDIAN)
                                .define('X', ModItems.UNSTABLE_INGOT).pattern("XXX").pattern(" # ").pattern(" # ")
                                .unlockedBy(has, has(ModItems.UNSTABLE_INGOT)).save(consumer);
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.UNSTABLE_SHOVEL).define('#', Blocks.OBSIDIAN)
                                .define('X', ModItems.UNSTABLE_INGOT).pattern("X").pattern("#").pattern("#")
                                .unlockedBy(has, has(ModItems.UNSTABLE_INGOT)).save(consumer);
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.UNSTABLE_SWORD).define('#', Blocks.OBSIDIAN)
                                .define('X', ModItems.UNSTABLE_INGOT).pattern("X").pattern("X").pattern("#")
                                .unlockedBy(has, has(ModItems.UNSTABLE_INGOT)).save(consumer);

                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.UNSTABLE_SHEARS)
                                .define('#', ModItems.UNSTABLE_INGOT)
                                .define('O', Blocks.CRYING_OBSIDIAN)
                                .pattern("O#")
                                .pattern("#O")
                                .unlockedBy(has, has(ModItems.UNSTABLE_INGOT)).save(consumer);

                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.UNSTABLE_FISHING_ROD)
                                .define('#', ModItems.UNSTABLE_INGOT)
                                .define('X', Items.STRING)
                                .pattern("  #")
                                .pattern(" #X").pattern("# X").unlockedBy("has_string", has(Items.STRING))
                                .save(consumer);

                ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.UNSTABLE_ELYTRA)
                                .define('#', ModItems.UNSTABLE_INGOT)
                                .define('E', Items.ELYTRA)
                                .define('O', Items.OBSIDIAN)
                                .pattern("#O#")
                                .pattern(" E ")
                                .unlockedBy("has_elytra", has(Items.ELYTRA)).save(consumer);

                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.UNSTABLE_BOW)
                                .define('#', ModItems.UNSTABLE_INGOT)
                                .define('X', Items.STRING).pattern(" #X").pattern("# X").pattern(" #X")
                                .unlockedBy("has_string", has(Items.STRING)).save(consumer);

                ShapedRecipeBuilderCustom.customShaped(RecipeCategory.MISC, ModItems.UNSTABLE_INGOT)
                                .define('i', Tags.Items.INGOTS_IRON)
                                .define('D', ModItems.division_sign)
                                .define('d', Tags.Items.GEMS_DIAMOND)
                                .pattern("i")
                                .pattern("D")
                                .pattern("d")
                                .serializer(ModRecipeSerializer.division)
                                .unlockedBy("has_division_sign", has(ModItems.division_sign)).save(consumer);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.UNSTABLE_INGOT)
                                .define('i', Tags.Items.INGOTS_IRON)
                                .define('D', ModItems.stable_division_sign)
                                .define('d', Tags.Items.GEMS_DIAMOND)
                                .pattern("i")
                                .pattern("D")
                                .pattern("d")
                                .unlockedBy("has_stable_division_sign", has(ModItems.division_sign))
                                .save(consumer, UnstableTools.id("stable_unstable_ingot"));

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.unstable_block)
                                .define('i', Tags.Items.STORAGE_BLOCKS_IRON)
                                .define('D', ModItems.stable_division_sign)
                                .define('d', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                                .pattern("i")
                                .pattern("D")
                                .pattern("d")
                                .unlockedBy("has_stable_division_sign", has(ModItems.division_sign))
                                .save(consumer, UnstableTools.id("unstable_block_by_division"));

        }

}
