package tfar.unstabletools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
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
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import tfar.unstabletools.crafting.ConversionManager;
import tfar.unstabletools.datagen.Datagen;
import tfar.unstabletools.init.ModBlocks;
import tfar.unstabletools.init.ModCreativeTab;
import tfar.unstabletools.init.ModItems;
import tfar.unstabletools.init.ModRecipeSerializer;
import tfar.unstabletools.item.DivisionSignItem;
import tfar.unstabletools.item.UnstableIngotItem;
import tfar.unstabletools.item.tools.ItemUnstableShears;
import tfar.unstabletools.platform.Services;

import java.util.List;

@Mod(value = UnstableTools.MOD_ID)
public class UnstableToolsNeoForge {


    public static UnstableToolsNeoForge instance;
  public UnstableToolsNeoForge(IEventBus bus, Dist dist) {
    instance = this;
    NeoForge.EVENT_BUS.addListener(this::onDrops);
    NeoForge.EVENT_BUS.addListener(this::reload);
    NeoForge.EVENT_BUS.addListener(this::onItemDrop);
    NeoForge.EVENT_BUS.addListener(this::playertick);
    NeoForge.EVENT_BUS.addListener(this::onContainerClose);
    NeoForge.EVENT_BUS.addListener(this::onSacrifice);
    if (dist.isClient()) {
        ModClientForge.init(bus);
    }
    bus.addListener(this::registerBlock);
    bus.addListener(Datagen::gather);
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
  }

  public static final Tier UNSTABLE = new UnstableTier();

  public static final ArmorMaterial UNSTABLE_ARMOR = new UnstableArmorMaterial();

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
            if (stack.getItem() != ModItems.division_sign && stack.getItem() != ModItems.division_sign)
                continue;
            ItemStack newStack = new ItemStack(ModItems.division_sign);
            newStack.getOrCreateTag().putInt(DivisionSignItem.USES, Config.ServerConfig.uses.get());
            mainInventory.set(i, newStack);
        }
        if (!world.isClientSide) {
            LightningBolt entity = EntityType.LIGHTNING_BOLT.create(world);
            entity.moveTo(sacrifice.getX(), sacrifice.getY(), sacrifice.getZ());
            world.addFreshEntity(entity);
        }
        if (Services.PLATFORM.isModLoaded("cursedearth") && Config.ServerConfig.cursed_earth_integration.get()) {
            for (int x = pos.getX() - 7; x < pos.getX() + 8; x++)
                for (int z = pos.getZ() - 7; z < pos.getZ() + 8; z++) {
                    int y = world.getHeight(Heightmap.Types.WORLD_SURFACE, x, z) - 1;
                    for (int y1 = y + 7; y1 > y - 7; y1--) {
                        BlockPos pos1 = new BlockPos(x, y1, z);
                        BlockState block1 = world.getBlockState(pos1);
                        if (block1.is(BlockTags.DIRT)) {
                            world.setBlockAndUpdate(pos1, cursed_earth.defaultBlockState());
                            break;
                        }
                    }
                }
        }
    }

    void registerBlock(RegisterEvent event) {
      event.register(Registries.BLOCK, UnstableTools.id("unstable_block"), () -> ModBlocks.unstable_block);
      event.register(Registries.BLOCK, UnstableTools.id("ethereal_glass"), () -> ModBlocks.ethereal_glass);

      event.register(Registries.ITEM, UnstableTools.id("unstable_ingot"), () -> ModItems.UNSTABLE_INGOT);
      event.register(Registries.ITEM, UnstableTools.id("unstable_shears"), () -> ModItems.UNSTABLE_SHEARS);

      event.register(Registries.ITEM, UnstableTools.id("unstable_block"), () -> ModItems.unstable_block);
      event.register(Registries.ITEM, UnstableTools.id("ethereal_glass"), () -> ModItems.ethereal_glass);

      event.register(Registries.ITEM, UnstableTools.id("unstable_axe"), () -> ModItems.UNSTABLE_AXE);
      event.register(Registries.ITEM, UnstableTools.id("unstable_shovel"), () -> ModItems.UNSTABLE_SHOVEL);
      event.register(Registries.ITEM, UnstableTools.id("unstable_pickaxe"), () -> ModItems.UNSTABLE_PICKAXE);
      event.register(Registries.ITEM, UnstableTools.id("unstable_sword"), () -> ModItems.UNSTABLE_SWORD);
      event.register(Registries.ITEM, UnstableTools.id("unstable_hoe"), () -> ModItems.UNSTABLE_HOE);

      event.register(Registries.ITEM, UnstableTools.id("unstable_fishing_rod"), () -> ModItems.UNSTABLE_FISHING_ROD);
      event.register(Registries.ITEM, UnstableTools.id("unstable_bow"), () -> ModItems.UNSTABLE_BOW);

      event.register(Registries.ITEM, UnstableTools.id("unstable_helmet"), () -> ModItems.UNSTABLE_HELMET);
      event.register(Registries.ITEM, UnstableTools.id("unstable_chestplate"), () -> ModItems.UNSTABLE_CHESTPLATE);
      event.register(Registries.ITEM, UnstableTools.id("unstable_leggings"), () -> ModItems.UNSTABLE_LEGGINGS);
      event.register(Registries.ITEM, UnstableTools.id("unstable_boots"), () -> ModItems.UNSTABLE_BOOTS);
      event.register(Registries.ITEM, UnstableTools.id("inactive_division_sign"), () -> ModItems.INACTIVE_DIVISION_SIGN);
      event.register(Registries.ITEM, UnstableTools.id("division_sign"), () -> ModItems.division_sign);
      event.register(Registries.ITEM, UnstableTools.id("stable_division_sign"), () -> ModItems.stable_division_sign);

      event.register(Registries.RECIPE_SERIALIZER, UnstableTools.id("division"), () -> ModRecipeSerializer.division);

      event.register(Registries.CREATIVE_MODE_TAB,UnstableTools.id("tab"),() -> ModCreativeTab.TAB);

    }

    public static void onBlockDrops(Level worldIn, BlockPos pos, ItemStack stackToSpawn, Entity entity, ItemStack stack) {
      if (entity instanceof Player player) {
        if (stack.getItem() instanceof ItemUnstableShears) {
          player.addItem(stackToSpawn);
        }
      }
    }

    public final ConversionManager manager = new ConversionManager();

    private void reload(AddReloadListenerEvent event) {
      event.addListener(manager);
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

    void playertick(TickEvent.PlayerTickEvent e) {

      if (e.phase == TickEvent.Phase.START) return;

      AbstractContainerMenu container = e.player.containerMenu;

      try {
        MenuType<?> type = container.getType();
        if (!Config.ServerConfig.allowed_containers.get().contains(BuiltInRegistries.MENU.getKey(type).toString()))
          return;
      } catch (Exception ex) {
        return;
      }

      Level world = e.player.level();

      if (world.isClientSide) return;
      boolean explode = false;

      List<Slot> inventorySlots = container.slots;
      for (Slot slot : inventorySlots) {
        ItemStack stack = slot.getItem();
        if (!(stack.getItem() instanceof UnstableIngotItem) || !stack.hasTag() || slot instanceof ResultSlot)
          continue;
        int timer = stack.getTag().getInt("timer");
        if (timer <= 0) {
          slot.set(ItemStack.EMPTY);
          explode = true;
          continue;
        }
        stack.getTag().putInt("timer", --timer);
      }

      if (!explode) return;
      UnstableIngotItem.boom(e.player);
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
