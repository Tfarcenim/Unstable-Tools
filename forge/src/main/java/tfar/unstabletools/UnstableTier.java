package tfar.unstabletools;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import tfar.unstabletools.init.ModItems;

import javax.annotation.Nonnull;

public class UnstableTier implements Tier {

    @Override
    public int getUses() {
        return 0;
    }

    @Override
    public float getSpeed() {
        return 8;
    }

    @Override
    public float getAttackDamageBonus() {
        return 8;
    }

    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public int getEnchantmentValue() {
        return 25;
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.unstable_ingot);
    }
}
