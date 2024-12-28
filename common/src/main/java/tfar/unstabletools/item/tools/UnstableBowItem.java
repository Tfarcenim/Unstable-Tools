package tfar.unstabletools.item.tools;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

public class UnstableBowItem extends BowItem {


  public UnstableBowItem(Properties pProperties) {
    super(pProperties);
  }



  @Override
  public boolean isEnchantable(ItemStack pStack) {
    return true;
  }

}
