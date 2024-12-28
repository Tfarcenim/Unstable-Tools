package tfar.unstabletools;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.apache.logging.log4j.util.Lazy;
import tfar.unstabletools.crafting.ConversionManager;
import tfar.unstabletools.init.ModBlocks;
import tfar.unstabletools.init.ModCreativeTab;
import tfar.unstabletools.init.ModItems;
import tfar.unstabletools.init.ModRecipeSerializer;
import tfar.unstabletools.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class UnstableTools {

    public static final String MOD_ID = "unstabletools";
    public static final String MOD_NAME = "UnstableTools";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final ConversionManager manager = new ConversionManager();
    public static boolean cursed_earth;

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
         cursed_earth = Services.PLATFORM.isModLoaded("cursedearth");

        Services.PLATFORM.registerAll(ModBlocks.class,BuiltInRegistries.BLOCK, Block.class);
        Services.PLATFORM.registerAll(ModItems.class,BuiltInRegistries.ITEM, Item.class);
        Services.PLATFORM.registerAll(ModRecipeSerializer.class,BuiltInRegistries.RECIPE_SERIALIZER,dirtyCast(RecipeSerializer.class));
        Services.PLATFORM.registerAll(ModCreativeTab.class,BuiltInRegistries.CREATIVE_MODE_TAB, CreativeModeTab.class);

    }

    @SuppressWarnings("unchecked")
    static <T> Class<T> dirtyCast(Class<?> clazz) {
        return (Class<T>) clazz;
    }

    public static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath(MOD_ID,path);
    }

    public static final Lazy<Block> cursed_earth_block = Lazy.lazy(() -> BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("cursedearth","cursed_earth")));

}