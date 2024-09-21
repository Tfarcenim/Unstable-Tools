package tfar.unstabletools.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import tfar.unstabletools.TranslationKeys;
import tfar.unstabletools.crafting.Config;
import tfar.unstabletools.init.ModItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class DivisionSignItem extends AbstractDivisionSignItem {

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
        int d = getUses(stack);
        d--;
        if (d > 0) {
            setUses(stack, d);
            return stack;
        } else {
            return new ItemStack(ModItems.INACTIVE_DIVISION_SIGN);
        }
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return (int) (MAX_BAR_WIDTH * stack.getOrCreateTag().getInt(USES) / (double) Config.ServerConfig.uses.get());
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(TranslationKeys.usesLeft(getUses(stack)));
    }

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return 0xeedd00;
    }

    public static int getUses(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(USES) : 0;
    }

    public static void setUses(ItemStack stack, int uses) {
        stack.getOrCreateTag().putInt(USES, uses);
    }
}
