package tfar.unstabletools.item;

import net.minecraft.world.item.ItemStack;

public class StableDivisionSignItem extends AbstractDivisionSignItem {
    public StableDivisionSignItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return 0x00ff00;
    }
}
