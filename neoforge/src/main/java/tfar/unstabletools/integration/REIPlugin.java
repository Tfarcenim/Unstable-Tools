package tfar.unstabletools.integration;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.forge.REIPluginCommon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import tfar.unstabletools.UnstableTools;
import tfar.unstabletools.UnstableToolsNeoForge;
import tfar.unstabletools.crafting.ConversionManager;
import tfar.unstabletools.crafting.ConversionRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@REIPluginCommon
public class REIPlugin implements REIServerPlugin {

    public static final ResourceLocation BLOCK_CONVERSIONS = UnstableTools.id(ConversionManager.BLOCK_CONVS);
    public static final CategoryIdentifier<ConversionRecipeDisplay> TYPE = CategoryIdentifier.of(BLOCK_CONVERSIONS.getNamespace(), BLOCK_CONVERSIONS.getPath());

    public static List<ConversionRecipe> getRecipes() {
        ConversionManager conversionManager = UnstableTools.manager;
        List<ConversionRecipe> conversionRecipes = new ArrayList<>();
        for (Map.Entry<Block,Block> entry:conversionManager.getConversionMap().entrySet()) {
            conversionRecipes.add(new ConversionRecipe(entry.getKey(),entry.getValue()));
        }
        return conversionRecipes;
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(TYPE, ConversionRecipeDisplay.serializer());
    }
}
