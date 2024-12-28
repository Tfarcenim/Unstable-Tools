package tfar.unstabletools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.commons.lang3.tuple.Pair;
import tfar.unstabletools.datagen.Datagen;
import tfar.unstabletools.init.*;
import tfar.unstabletools.item.UnstableIngotItem;
import tfar.unstabletools.item.tools.ItemUnstableShears;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod(value = UnstableTools.MOD_ID)
public class UnstableToolsNeoForge {

    public static Map<Registry<?>, List<Pair<ResourceLocation, Supplier<?>>>> registerLater = new HashMap<>();




    public UnstableToolsNeoForge(IEventBus bus, Dist dist, ModContainer container) {
        NeoForge.EVENT_BUS.addListener(this::onDrops);
        NeoForge.EVENT_BUS.addListener(this::reload);
        NeoForge.EVENT_BUS.addListener(this::onItemDrop);
        NeoForge.EVENT_BUS.addListener(this::playertick);
        NeoForge.EVENT_BUS.addListener(this::onContainerClose);
        NeoForge.EVENT_BUS.addListener(this::onSacrifice);
        if (dist.isClient()) {
            ModClientNeoForge.init(bus);
        }
        bus.addListener(this::registerBlock);
        bus.addListener(Datagen::gather);
        bus.addListener(this::onInitialize);
        container.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);

        ((MappedRegistry<ArmorMaterial>)BuiltInRegistries.ARMOR_MATERIAL).unfreeze();
        UnstableTools.init();
    }

    public void onSacrifice(LivingDeathEvent e) {
        if (!(e.getSource().getEntity() instanceof Player player)) return;
        LivingEntity sacrifice = e.getEntity();
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
            if (stack.getItem() != ModItems.division_sign)
                continue;
            ItemStack newStack = new ItemStack(ModItems.division_sign);
            newStack.set(ModDataComponents.USES, Config.ServerConfig.uses.get());
            mainInventory.set(i, newStack);
        }
        if (!world.isClientSide) {
            LightningBolt entity = EntityType.LIGHTNING_BOLT.create(world);
            entity.moveTo(sacrifice.getX(), sacrifice.getY(), sacrifice.getZ());
            world.addFreshEntity(entity);
        }
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

    void registerBlock(RegisterEvent event) {
        for (Map.Entry<Registry<?>,List<Pair<ResourceLocation, Supplier<?>>>> entry : registerLater.entrySet()) {
            Registry<?> registry = entry.getKey();
            List<Pair<ResourceLocation, Supplier<?>>> toRegister = entry.getValue();
            for (Pair<ResourceLocation,Supplier<?>> pair : toRegister) {
                event.register((ResourceKey<? extends Registry<Object>>)registry.key(),pair.getLeft(),(Supplier<Object>)pair.getValue());
            }
        }
    }

    public void onInitialize(FMLCommonSetupEvent e) {
        registerLater.clear();
    }

    public static void onBlockDrops(Level worldIn, BlockPos pos, ItemStack stackToSpawn, Entity entity, ItemStack stack) {
        if (entity instanceof Player player) {
            if (stack.getItem() instanceof ItemUnstableShears) {
                player.addItem(stackToSpawn);
            }
        }
    }

    private void reload(AddReloadListenerEvent event) {
        event.addListener(UnstableTools.manager);
    }

    private void onDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof WitherBoss && event.getSource().getEntity() instanceof Player) {

            ItemStack itemStackToDrop = new ItemStack(ModItems.INACTIVE_DIVISION_SIGN);
            event.getDrops().add(new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), itemStackToDrop));
        }
    }

    void onItemDrop(ItemTossEvent e) {
        Player p = e.getPlayer();
        ItemEntity entityItem = e.getEntity();
        ItemStack stack = entityItem.getItem();
        if (UnstableIngotItem.checkExplosion(stack)) {
            UnstableIngotItem.boom(p);
            e.setCanceled(true);
        }
    }

    void playertick(PlayerTickEvent.Post e) {

        Player player = e.getEntity();
        if (player.level().isClientSide) return;
        AbstractContainerMenu container = e.getEntity().containerMenu;

        try {
            MenuType<?> type = container.getType();
            if (!Config.ServerConfig.allowed_containers.get().contains(BuiltInRegistries.MENU.getKey(type).toString()))
                return;
        } catch (Exception ex) {
            return;
        }

        Level world = player.level();

        boolean explode = false;

        List<Slot> inventorySlots = container.slots;
        for (Slot slot : inventorySlots) {
            ItemStack stack = slot.getItem();
            if (!(stack.getItem() instanceof UnstableIngotItem) || slot instanceof ResultSlot)
                continue;
            int timer = stack.getOrDefault(ModDataComponents.TIMER,0);
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

    void onContainerClose(PlayerContainerEvent.Close e) {
        AbstractContainerMenu c = e.getContainer();
        boolean explode = false;
        for (Slot slot : c.slots) {
            ItemStack stack = slot.getItem();
            if (!UnstableIngotItem.checkExplosion(stack) || slot instanceof ResultSlot) continue;
            slot.set(ItemStack.EMPTY);
            explode = true;
        }
        if (!explode) return;
        UnstableIngotItem.boom(e.getEntity());
    }
}
