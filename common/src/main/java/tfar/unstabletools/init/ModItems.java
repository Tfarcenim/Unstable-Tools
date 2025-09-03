package tfar.unstabletools.init;

import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Unbreakable;
import tfar.unstabletools.UnstableTier;
import tfar.unstabletools.item.DivisionSignItem;
import tfar.unstabletools.item.InactiveDivisionSignItem;
import tfar.unstabletools.item.UnstableIngotItem;
import tfar.unstabletools.item.StableDivisionSignItem;
import tfar.unstabletools.item.tools.ItemUnstableAxe;
import tfar.unstabletools.item.tools.ItemUnstableHoe;
import tfar.unstabletools.item.tools.ItemUnstableShears;
import tfar.unstabletools.item.tools.UnstableBowItem;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class ModItems {

    public static final Tier UNSTABLE = new UnstableTier();
    private static final Set<Item> ITEMS = new HashSet<>();
    static Item.Properties properties = new Item.Properties();
    public static final Item ethereal_glass = new BlockItem(ModBlocks.ethereal_glass, properties);
    public static final Item unstable_block = new BlockItem(ModBlocks.unstable_block, properties);
    public static final Item UNSTABLE_INGOT = new UnstableIngotItem(properties);
    public static final Item INACTIVE_DIVISION_SIGN = new InactiveDivisionSignItem(new Item.Properties());
    public static final Item division_sign = new DivisionSignItem(new Item.Properties());
    public static final Item stable_division_sign = new StableDivisionSignItem(new Item.Properties());
    public static final Item UNSTABLE_AXE = new ItemUnstableAxe(UNSTABLE,  new Item.Properties()
            .attributes(AxeItem.createAttributes(UNSTABLE, 5, -3))
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
    );
    public static final Item UNSTABLE_BOW = new UnstableBowItem(new Item.Properties().stacksTo(1));
    public static final Item UNSTABLE_FISHING_ROD = new FishingRodItem(new Item.Properties().stacksTo(1)){  @Override
    public boolean isEnchantable(ItemStack pStack) {
        return true;
    }};
    public static final Item UNSTABLE_PICKAXE = new PickaxeItem(UNSTABLE, new Item.Properties()
            .attributes(PickaxeItem.createAttributes(UNSTABLE, 1, -2.8F))
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
    ){
        @Override
        public boolean isEnchantable(ItemStack pStack) {
            return true;
        }
    };
    public static final Item UNSTABLE_HOE = new ItemUnstableHoe(UNSTABLE, new Item.Properties()
            .attributes(HoeItem.createAttributes(UNSTABLE, -3, 0))
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
    );
    public static final Item UNSTABLE_SHEARS = new ItemUnstableShears(new Item.Properties()
            .component(DataComponents.TOOL, ShearsItem.createToolProperties())
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
    );
    public static final Item UNSTABLE_SHOVEL = new ShovelItem(UNSTABLE,  new Item.Properties()
            .attributes(ShovelItem.createAttributes(UNSTABLE, 1.5F, -3))
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
    ) {
        @Override
        public boolean isEnchantable(ItemStack pStack) {
            return true;
        }
    };
    public static final Item UNSTABLE_SWORD = new SwordItem(UNSTABLE, new Item.Properties()
            .attributes(SwordItem.createAttributes(UNSTABLE, 3, -2.4F))
            .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
    ) {
        @Override
        public boolean isEnchantable(ItemStack pStack) {
            return true;
        }
    };



    public static final ArmorItem UNSTABLE_HELMET = new ArmorItem(ModArmorMaterials.UNSTABLE,
            ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1)){
        @Override
        public boolean isEnchantable(ItemStack pStack) {
            return true;
        }
    };
    public static final ArmorItem UNSTABLE_CHESTPLATE = new ArmorItem(ModArmorMaterials.UNSTABLE,
            ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1)){
        @Override
        public boolean isEnchantable(ItemStack pStack) {
            return true;
        }
    };
    public static final ArmorItem UNSTABLE_LEGGINGS = new ArmorItem(ModArmorMaterials.UNSTABLE,
            ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1)){
        @Override
        public boolean isEnchantable(ItemStack pStack) {
            return true;
        }
    };
    public static final ArmorItem UNSTABLE_BOOTS = new ArmorItem(ModArmorMaterials.UNSTABLE,
            ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1)){
        @Override
        public boolean isEnchantable(ItemStack pStack) {
            return true;
        }
    };

    public static Set<Item> getItems() {
        if (ITEMS.isEmpty()) {
            for (Field field : ModItems.class.getFields()) {
                try {
                    Object o = field.get(null);
                    if (o instanceof Item item) ITEMS.add(item);
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            }
        }
        return ITEMS;
    }
}
