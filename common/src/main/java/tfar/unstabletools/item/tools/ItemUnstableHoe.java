package tfar.unstabletools.item.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import tfar.unstabletools.UnstableTools;

public class ItemUnstableHoe extends HoeItem {
  public ItemUnstableHoe(Tier material, Properties properties) {
    super(material,properties);
  }

  /**
   * Called when this item is used when targetting a Block
   */
  @Override
  public InteractionResult useOn(UseOnContext context) {
    Level level = context.getLevel();
    BlockPos blockpos = context.getClickedPos();
      if (context.getClickedFace() != Direction.DOWN && level.isEmptyBlock(blockpos.above())) {
        Block block = UnstableTools.manager.getConversionMap().get(level.getBlockState(blockpos).getBlock());
        if (block != null) {
          Player playerentity = context.getPlayer();
          level.playSound(playerentity, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
          if (!level.isClientSide) {
            level.setBlock(blockpos, block.defaultBlockState(), 11);
            if (playerentity != null) {
              context.getItemInHand().hurtAndBreak(1, playerentity, LivingEntity.getSlotForHand(context.getHand()));
            }
          }
          return InteractionResult.SUCCESS;
        }
      }
    return InteractionResult.PASS;
  }

  @Override
  public boolean isEnchantable(ItemStack pStack) {
    return true;
  }

}