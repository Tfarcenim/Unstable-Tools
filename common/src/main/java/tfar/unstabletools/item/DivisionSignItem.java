package tfar.unstabletools.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import tfar.unstabletools.TranslationKeys;
import tfar.unstabletools.Config;
import tfar.unstabletools.init.ModDataComponents;
import tfar.unstabletools.init.ModItems;

import javax.annotation.Nonnull;
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
        return (int) (MAX_BAR_WIDTH * getUses(stack) / (double) Config.ServerConfig.uses.get());
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }



    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(TranslationKeys.usesLeft(getUses(pStack)));
    }

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return 0xffeedd00;
    }

    public static int getUses(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.USES,0);
    }

    public static void setUses(ItemStack stack, int uses) {
        stack.set(ModDataComponents.USES, uses);
    }
}
