package tfar.unstabletools.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.common.Mod;
import tfar.unstabletools.crafting.Config;
import tfar.unstabletools.init.ModItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber
public class DivisionSignItem extends StableDivisionSignItem {

    public static final String USES = "d";

    public DivisionSignItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return damage(stack.copy());
    }

    public ItemStack damage(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        int d = nbt.getInt(USES);
        d--;
        if (d > 0) {
            nbt.putInt(USES, d);
            return stack;
        } else {
            return new ItemStack(this);
        }
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return (int) (MAX_BAR_WIDTH * stack.getOrCreateTag().getInt(USES) / (double) Config.ServerConfig.uses.get());
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getUses(stack) > 0;
    }

    @Override
    @Nonnull
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
        if (time <= 17500) message(player, Component.translatable("unstabletools.early"));
        else if (time <= 18500) {
            message(player, Component.translatable("unstabletools.ontime"));
            correctTime = true;
        } else message(player, Component.translatable("unstabletools.late"));
        boolean circle = true;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;
                BlockPos pos1 = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j);
                if (world.getBlockState(pos1).getBlock() != Blocks.REDSTONE_WIRE) circle = false;
            }
        }

        if (!circle) message(player, Component.translatable("unstabletools.incomplete"));
        boolean skyVisible = world.canSeeSkyFromBelowWater(pos.above());
        if (!skyVisible) message(player, Component.translatable("unstabletools.nosky"));

        if (correctTime && circle && skyVisible) message(player, Component.translatable("unstabletools.ready"));

        return InteractionResult.PASS;
    }

    private static void message(Player player, Component component) {
        player.sendSystemMessage(component);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (Screen.hasShiftDown())
            tooltip.add(Component.literal("Drops from Wither").withStyle(ChatFormatting.AQUA));
        if (stack.hasTag()){
            tooltip.add(Component.literal("Uses Left: " + getUses(stack)));
        } else {
            tooltip.add(Component.literal("Inactive"));
        }
    }

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return getUses(stack) <= 0 ? 0xff0000 : 0xeedd00;
    }

    public static int getUses(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(USES) : 0;
    }

    public static void setUses(ItemStack stack,int uses) {
        stack.getOrCreateTag().putInt(USES,uses);
    }

}
