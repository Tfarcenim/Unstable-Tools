package tfar.unstabletools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import org.apache.logging.log4j.util.Lazy;
import tfar.unstabletools.crafting.ConversionManager;
import tfar.unstabletools.init.*;
import tfar.unstabletools.item.UnstableIngotItem;
import tfar.unstabletools.item.tools.ItemUnstableShears;
import tfar.unstabletools.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

        Services.PLATFORM.registerAll(ModBlocks.class, BuiltInRegistries.BLOCK, Block.class);
        Services.PLATFORM.registerAll(ModItems.class, BuiltInRegistries.ITEM, Item.class);
        Services.PLATFORM.registerAll(ModRecipeSerializer.class, BuiltInRegistries.RECIPE_SERIALIZER, dirtyCast(RecipeSerializer.class));
        Services.PLATFORM.registerAll(ModCreativeTab.class, BuiltInRegistries.CREATIVE_MODE_TAB, CreativeModeTab.class);
        Services.PLATFORM.registerAll(ModDataComponents.class, BuiltInRegistries.DATA_COMPONENT_TYPE, dirtyCast(DataComponentType.class));

    }

    public static void playerTick(ServerPlayer player) {
        AbstractContainerMenu container = player.containerMenu;

        boolean explode = false;

        List<Slot> inventorySlots = container.slots;
        for (Slot slot : inventorySlots) {
            ItemStack stack = slot.getItem();
            if (!UnstableIngotItem.checkExplosion(stack) || slot instanceof ResultSlot)
                continue;
            int timer = stack.get(ModDataComponents.TIMER);
            if (timer <= 0) {
                slot.set(ItemStack.EMPTY);
                explode = true;
                continue;
            }
            stack.set(ModDataComponents.TIMER, --timer);
        }

        if (!explode) return;
        UnstableIngotItem.boom(player);
    }

    public static void onBlockDrops(Level worldIn, BlockPos pos, ItemStack stackToSpawn, Entity entity, ItemStack stack) {
        if (entity instanceof Player player) {
            if (stack.getItem() instanceof ItemUnstableShears) {
                player.addItem(stackToSpawn);
            }
        }
    }

    public static void livingDeath(LivingEntity sacrifice, DamageSource source) {
        if (!(source.getEntity() instanceof ServerPlayer player)) return;
        Level world = sacrifice.level();
        BlockPos pos = sacrifice.blockPosition();
        if (!world.canSeeSkyFromBelowWater(pos)) return;
        Block block = world.getBlockState(pos).getBlock();
        if (block != Blocks.ENCHANTING_TABLE) return;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;
                BlockPos pos1 = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j);
                if (world.getBlockState(pos1).getBlock() != Blocks.REDSTONE_WIRE) return;
            }
        }

        long time = world.getLevelData().getDayTime() % 24000;
        if (time <= 17500 || time > 18500) return;
        NonNullList<ItemStack> mainInventory = player.getInventory().items;
        for (int i = 0; i < mainInventory.size(); i++) {
            final ItemStack stack = mainInventory.get(i);
            if (stack.getItem() != ModItems.INACTIVE_DIVISION_SIGN)
                continue;
            ItemStack newStack = new ItemStack(ModItems.division_sign);
            newStack.set(ModDataComponents.USES, Config.ServerConfig.uses.get());
            mainInventory.set(i, newStack);
        }
        LightningBolt entity = EntityType.LIGHTNING_BOLT.create(world);
        entity.moveTo(sacrifice.getX(), sacrifice.getY(), sacrifice.getZ());
        world.addFreshEntity(entity);

        if (UnstableTools.cursed_earth && Config.ServerConfig.cursed_earth_integration.get()) {
            for (int x = pos.getX() - 7; x < pos.getX() + 8; x++)
                for (int z = pos.getZ() - 7; z < pos.getZ() + 8; z++) {
                    int y = world.getHeight(Heightmap.Types.WORLD_SURFACE, x, z) - 1;
                    for (int y1 = y + 7; y1 > y - 7; y1--) {
                        BlockPos pos1 = new BlockPos(x, y1, z);
                        BlockState block1 = world.getBlockState(pos1);
                        if (block1.is(BlockTags.DIRT)) {
                            world.setBlockAndUpdate(pos1, UnstableTools.cursed_earth_block.get().defaultBlockState());
                            break;
                        }
                    }
                }
        }
    }


    public static void containerClosed(AbstractContainerMenu menu, Player player) {
        boolean explode = false;
        for (Slot slot : menu.slots) {
            ItemStack stack = slot.getItem();
            if (!UnstableIngotItem.checkExplosion(stack) || slot instanceof ResultSlot) continue;
            slot.set(ItemStack.EMPTY);
            explode = true;
        }
        if (!explode) return;
        UnstableIngotItem.boom(player);
    }

    @SuppressWarnings("unchecked")
    static <T> Class<T> dirtyCast(Class<?> clazz) {
        return (Class<T>) clazz;
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static final Lazy<Block> cursed_earth_block = Lazy.lazy(() -> BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("cursedearth", "cursed_earth")));

}