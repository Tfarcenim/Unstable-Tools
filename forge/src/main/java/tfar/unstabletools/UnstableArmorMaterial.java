package tfar.unstabletools;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import tfar.unstabletools.init.ModItems;

import javax.annotation.Nonnull;
import java.util.EnumMap;

public class UnstableArmorMaterial implements ArmorMaterial {

    static EnumMap<ArmorItem.Type, Integer> map = Util.make(new EnumMap<>(ArmorItem.Type.class), (enumMap) -> {
        enumMap.put(ArmorItem.Type.BOOTS, 4);
        enumMap.put(ArmorItem.Type.LEGGINGS, 7);
        enumMap.put(ArmorItem.Type.CHESTPLATE, 9);
        enumMap.put(ArmorItem.Type.HELMET, 4);
    });

    @Override
    public int getDurabilityForType(@Nonnull ArmorItem.Type slotIn) {
        return 0;
    }

    @Override
    public int getDefenseForType(@Nonnull ArmorItem.Type slot) {
        return map.get(slot);
    }

    @Override
    public int getEnchantmentValue() {
        return 25;
    }

    @Nonnull
    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_DIAMOND;
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.UNSTABLE_INGOT);
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

    @Override
    public float getKnockbackResistance() {
        return 1;
    }
}
