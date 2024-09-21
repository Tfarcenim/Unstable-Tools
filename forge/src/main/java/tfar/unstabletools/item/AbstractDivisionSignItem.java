package tfar.unstabletools.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tfar.unstabletools.IItemColored;

public abstract class AbstractDivisionSignItem extends Item implements IItemColored {
    public AbstractDivisionSignItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return itemStack;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}
