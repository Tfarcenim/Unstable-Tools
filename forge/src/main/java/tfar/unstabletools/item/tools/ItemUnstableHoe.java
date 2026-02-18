package tfar.unstabletools.item.tools;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import tfar.unstabletools.UnstableToolsForge;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ItemUnstableHoe extends HoeItem {
  public ItemUnstableHoe(Tier material, int attackDamage, float speed, Properties properties) {
    super(material, attackDamage, speed, properties);
  }

  /**
   * Called when this item is used when targetting a Block
   */
  @Override
  public InteractionResult useOn(UseOnContext context) {
    Level level = context.getLevel();
    BlockPos pos = context.getClickedPos();
    Player player = context.getPlayer();

    BlockState state = level.getBlockState(pos);

    // --- FIRST: Try conversion ---
    Block conversion = UnstableToolsForge.instance.manager
        .getConversionMap()
        .get(state.getBlock());

    if (conversion != null &&
        context.getClickedFace() != Direction.DOWN &&
        level.isEmptyBlock(pos.above())) {

      level.playSound(player, pos, SoundEvents.HOE_TILL,
          SoundSource.BLOCKS, 1.0F, 1.0F);

      if (!level.isClientSide) {
        level.setBlock(pos, conversion.defaultBlockState(), 11);

        if (player != null) {
          context.getItemInHand().hurtAndBreak(1, player,
              p -> p.broadcastBreakEvent(context.getHand()));
        }
      }

      return InteractionResult.SUCCESS;
    }

    // --- OTHERWISE: Do normal hoe behavior ---
    return super.useOn(context);
  }

  @Override
  public boolean isEnchantable(ItemStack pStack) {
    return true;
  }

}