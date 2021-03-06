package com.tfar.unstabletools;

import com.tfar.unstabletools.crafting.Config;
import com.tfar.unstabletools.crafting.RecipeDivision;
import com.tfar.unstabletools.item.ItemDivisionSign;
import com.tfar.unstabletools.item.ItemUnstableIngot;
import com.tfar.unstabletools.tools.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

import static com.tfar.unstabletools.UnstableTools.ObjectHolders.*;
import static com.tfar.unstabletools.UnstableTools.ObjectHolders.unstable_block;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

@Mod(value = UnstableTools.MODID)
public class UnstableTools {

  public UnstableTools() {
    EVENT_BUS.addListener(this::onDrops);
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
  }

  public static final String MODID = "unstabletools";

  public static class UnstableTier implements IItemTier {

    @Override
    public int getMaxUses() {
      return 0;
    }

    @Override
    public float getEfficiency() {
      return 8;
    }

    @Override
    public float getAttackDamage() {
      return 8;
    }

    @Override
    public int getHarvestLevel() {
      return 4;
    }

    @Override
    public int getEnchantability() {
      return 25;
    }

    @Nonnull
    @Override
    public Ingredient getRepairMaterial() {
      return Ingredient.fromItems(unstable_ingot);
    }
  }

  public static final IItemTier UNSTABLE = new UnstableTier();

  public static class UnstableArmorMaterial implements IArmorMaterial {

    private static int[] array = new int[]{4, 7, 9, 4};

    @Override
    public int getDurability(@Nonnull EquipmentSlotType slotIn) {
      return 0;
    }

    @Override
    public int getDamageReductionAmount(@Nonnull EquipmentSlotType slot) {
      return array[slot.getIndex()];
    }

    @Override
    public int getEnchantability() {
      return 25;
    }

    @Nonnull
    @Override
    public SoundEvent getSoundEvent() {
      return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
    }

    @Nonnull
    @Override
    public Ingredient getRepairMaterial() {
      return Ingredient.fromItems(unstable_ingot);
    }

    @Nonnull
    @Override
    public String getName() {
      return "unstable";
    }

    @Override
    public float getToughness() {
      return 5;
    }
  }

  public static final IArmorMaterial UNSTABLE_ARMOR = new UnstableArmorMaterial();
  public static final Set<Item> MOD_ITEMS = new HashSet<>();

  public static ItemGroup creativeTab = new ItemGroup(MODID) {
    @Override
    public ItemStack createIcon() {
      return new ItemStack(unstable_pickaxe);
    }
  };

  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
      IForgeRegistry<Block> registry = event.getRegistry();
      register(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(5, 6000)) {

                 @Nullable
                 @Override
                 public ToolType getHarvestTool(BlockState state) {
                   return ToolType.PICKAXE;
                 }

                 @Nonnull
                       @Override
                       public BlockRenderLayer getRenderLayer() {
                         return BlockRenderLayer.CUTOUT;
                       }

                       @Override
                       @OnlyIn(Dist.CLIENT)
                       public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
                         return adjacentBlockState.getBlock() == this;
                       }

                       @Override
                       public boolean isSolid(BlockState state) {
                         return true;
                       }

                       @Override
                       public boolean isBeaconBase(BlockState state, IWorldReader world, BlockPos pos, BlockPos beacon) {
                         return true;
                       }
                     }
              , "unstable_block", registry);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
      IForgeRegistry<Item> registry = event.getRegistry();

      Item.Properties properties = new Item.Properties().group(creativeTab);

      register(new ItemUnstableIngot(properties), "unstable_ingot", registry);
      register(new ItemUnstableShears(properties), "unstable_shears", registry);

      register(new BlockItem(unstable_block, properties), unstable_block.getRegistryName().getPath(), registry);

      register(new ItemUnstableAxe(UNSTABLE,9, -3, properties), "unstable_axe", registry);
      register(new ShovelItem(UNSTABLE, 3, -1.5f, properties), "unstable_spade", registry);
      register(new PickaxeItem(UNSTABLE, 1, -2.8f, properties), "unstable_pickaxe", registry);
      register(new SwordItem(UNSTABLE, 3, -2.4f, properties), "unstable_sword", registry);
      register(new ItemUnstableHoe(UNSTABLE, 1, properties), "unstable_hoe", registry);

      register(new FishingRodItem(properties),"unstable_fishing_rod",registry);
      register(new UnstablePaxelItem( 1,-1,UNSTABLE, AxeItem.EFFECTIVE_ON, properties), "unstable_paxel", registry);
      register(new UnstableBowItem(properties),"unstable_bow",registry);

      register(new ArmorItem(UNSTABLE_ARMOR, EquipmentSlotType.HEAD, properties), "unstable_helmet", registry);
      register(new ArmorItem(UNSTABLE_ARMOR, EquipmentSlotType.CHEST, properties), "unstable_chestplate", registry);
      register(new ArmorItem(UNSTABLE_ARMOR, EquipmentSlotType.LEGS, properties), "unstable_leggings", registry);
      register(new ArmorItem(UNSTABLE_ARMOR, EquipmentSlotType.FEET, properties), "unstable_boots", registry);
      register(new ItemDivisionSign(properties), "inactive_division_sign", registry);
      register(new ItemDivisionSign(properties), "division_sign", registry);
      register(new ItemDivisionSign(properties), "stable_division_sign", registry);
    }

    @SubscribeEvent
    public static void registerSerials(RegistryEvent.Register<IRecipeSerializer<?>> event) {
      register(new SpecialRecipeSerializer<>(RecipeDivision::new),"division",event.getRegistry());
    }

    private static <T extends IForgeRegistryEntry<T>> void register(T obj, String name, IForgeRegistry<T> registry) {
      registry.register(obj.setRegistryName(new ResourceLocation(MODID, name)));
      if (obj instanceof Item) MOD_ITEMS.add((Item) obj);
    }
  }

    public void onDrops(LivingDropsEvent event) {
      LivingEntity entity = event.getEntityLiving();
      if (entity instanceof WitherEntity && (event.getSource().getTrueSource() instanceof PlayerEntity)
              && !(event.getSource().getTrueSource() instanceof FakePlayer)) {

        ItemStack itemStackToDrop = new ItemStack(inactive_division_sign);
        event.getDrops().add(new ItemEntity(entity.world, entity.posX,
                entity.posY, entity.posZ, itemStackToDrop));
      }
    }


  @ObjectHolder(MODID)
  public static class ObjectHolders {

    public static final Item unstable_ingot = null;

    public static final Block unstable_block = null;

    public static final Item inactive_division_sign = null;

    public static final Item division_sign = null;

    public static final Item stable_division_sign = null;

    public static final Item unstable_pickaxe = null;

    public static final IRecipeSerializer<?> division = null;

  }
}
