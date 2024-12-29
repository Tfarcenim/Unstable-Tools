package tfar.unstabletools.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import tfar.unstabletools.TranslationKeys;

import java.util.List;

public class InactiveDivisionSignItem extends AbstractDivisionSignItem{
    public InactiveDivisionSignItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(TranslationKeys.DROPS_FROM_WITHER);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Player player = ctx.getPlayer();
        InteractionHand hand = ctx.getHand();
        Level world = player.level();
        BlockPos pos = ctx.getClickedPos();
        if (hand == InteractionHand.OFF_HAND || world.isClientSide) return InteractionResult.FAIL;
        Block block = world.getBlockState(pos).getBlock();
        if (block != Blocks.ENCHANTING_TABLE) return InteractionResult.FAIL;
        long time = world.getLevelData().getDayTime() % 24000;

        boolean correctTime = false;
        if (time <= 17500) message(player, TranslationKeys.EARLY);
        else if (time <= 18500) {
            message(player, TranslationKeys.ON_TIME);
            correctTime = true;
        } else message(player, TranslationKeys.LATE);
        boolean circle = true;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;
                BlockPos pos1 = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j);
                if (world.getBlockState(pos1).getBlock() != Blocks.REDSTONE_WIRE) circle = false;
            }
        }

        if (!circle) message(player, TranslationKeys.INCOMPLETE_REDSTONE);
        boolean skyVisible = world.canSeeSkyFromBelowWater(pos.above());
        if (!skyVisible) message(player, TranslationKeys.NO_SKY);

        if (correctTime && circle && skyVisible) message(player, TranslationKeys.READY);

        return InteractionResult.PASS;
    }

    private static void message(Player player, Component component) {
        player.sendSystemMessage(component);
    }

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return 0xffff0000;
    }
}
