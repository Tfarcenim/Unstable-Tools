package tfar.unstabletools.init;

import net.minecraft.world.item.*;
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

import static tfar.unstabletools.UnstableToolsNeoForge.*;

public class ModItems {

    private static final Set<Item> ITEMS = new HashSet<>();
    static Item.Properties properties = new Item.Properties();
    public static final Item ethereal_glass = new BlockItem(ModBlocks.ethereal_glass, properties);
    public static final Item unstable_block = new BlockItem(ModBlocks.unstable_block, properties);
    public static final Item UNSTABLE_INGOT = new UnstableIngotItem(properties);
    public static final Item INACTIVE_DIVISION_SIGN = new InactiveDivisionSignItem(new Item.Properties());
    public static final Item division_sign = new DivisionSignItem(new Item.Properties());
    public static final Item stable_division_sign = new StableDivisionSignItem(new Item.Properties());
    public static final Item UNSTABLE_AXE = new ItemUnstableAxe(UNSTABLE, 9, -3, properties);
    public static final Item UNSTABLE_BOW = new UnstableBowItem(properties);
    public static final Item UNSTABLE_FISHING_ROD = new FishingRodItem(properties){  @Override
    public boolean isEnchantable(ItemStack pStack) {
        return true;
    }};
    public static final Item UNSTABLE_PICKAXE = new PickaxeItem(UNSTABLE, 1, -2.8f, properties){
        @Override
        public boolean isEnchantable(ItemStack pStack) {
            return true;
        }
    };
    public static final Item UNSTABLE_HOE = new ItemUnstableHoe(UNSTABLE, -4, 0, properties);
    public static final Item UNSTABLE_SHEARS = new ItemUnstableShears(properties);
    public static final Item UNSTABLE_SHOVEL = new ShovelItem(UNSTABLE, 3, -1.5f, properties);
    public static final Item UNSTABLE_SWORD = new SwordItem(UNSTABLE, 3, -2.4f, properties);

    public static final Item UNSTABLE_HELMET = new ArmorItem(UNSTABLE_ARMOR, ArmorItem.Type.HELMET, properties){
        @Override
        public boolean isEnchantable(ItemStack pStack) {
            return true;
        }
    };
    public static final Item UNSTABLE_CHESTPLATE = new ArmorItem(UNSTABLE_ARMOR, ArmorItem.Type.CHESTPLATE, properties){
        @Override
        public boolean isEnchantable(ItemStack pStack) {
            return true;
        }
    };
    public static final Item UNSTABLE_LEGGINGS = new ArmorItem(UNSTABLE_ARMOR, ArmorItem.Type.LEGGINGS, properties){
        @Override
        public boolean isEnchantable(ItemStack pStack) {
            return true;
        }
    };
    public static final Item UNSTABLE_BOOTS = new ArmorItem(UNSTABLE_ARMOR, ArmorItem.Type.BOOTS, properties){
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
