package tfar.unstabletools;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import tfar.unstabletools.init.ModBlocks;
import tfar.unstabletools.init.ModItems;

public class ModClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), ModBlocks.ethereal_glass,ModBlocks.unstable_block);
        for (Item item : ModItems.getItems()) {
            if (item instanceof IItemColored iItemColored)
                ColorProviderRegistry.ITEM.register(iItemColored::getColor,item);
        }
        ModClient.setup();
    }
}
