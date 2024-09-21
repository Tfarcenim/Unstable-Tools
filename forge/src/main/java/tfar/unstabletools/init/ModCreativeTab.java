package tfar.unstabletools.init;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tfar.unstabletools.crafting.Config;
import tfar.unstabletools.item.DivisionSignItem;

public class ModCreativeTab {

    public static final CreativeModeTab TAB = new CreativeModeTab.Builder(null,-1).title(Component.translatable("itemGroup.unstabletools"))
            .icon(ModItems.unstable_pickaxe::getDefaultInstance)
                      .displayItems((pEnabledFeatures, pOutput) -> {
        for (Item item : ModItems.getItems()) {
            pOutput.accept(item);
        }
        ItemStack filledSign = ModItems.division_sign.getDefaultInstance();
        filledSign.getOrCreateTag().putInt(DivisionSignItem.USES, Config.ServerConfig.uses.get());
        pOutput.accept(filledSign);
    }).build();

}
