package tfar.unstabletools.item.tools;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;

public class ItemUnstableShears extends ShearsItem {

  public ItemUnstableShears(Properties builder) {
    super(builder);
  }

  @Override
  public float getDestroySpeed(ItemStack stack, BlockState state) {
    return 20;
  }

  @Override
  public boolean isEnchantable(ItemStack pStack) {
    return true;
  }

  //doesn't work
  /*@SubscribeEvent
  @SuppressWarnings("unused")
  public static void itemDrops(BlockEvent.HarvestDropsEvent e) {
    PlayerEntity player = e.getHarvester();
    if (player != null && player.getHeldItemMainhand().getItem() instanceof ItemUnstableShears)
      e.getDrops().removeIf(player::addItemStackToInventory);
  }*/
}