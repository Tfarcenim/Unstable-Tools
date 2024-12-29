package tfar.unstabletools;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import tfar.unstabletools.init.ModItems;
import tfar.unstabletools.init.ModTags;


public class UnstableTier implements Tier {

    @Override
    public int getUses() {
        return 0;
    }

    @Override
    public float getSpeed() {
        return 10;
    }

    @Override
    public float getAttackDamageBonus() {
        return 8;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return ModTags.Blocks.INCORRECT_FOR_UNSTABLE_TOOL;
    }


    @Override
    public int getEnchantmentValue() {
        return 25;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.UNSTABLE_INGOT);
    }
}
