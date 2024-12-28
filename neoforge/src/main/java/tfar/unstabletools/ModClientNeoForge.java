package tfar.unstabletools;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import tfar.unstabletools.init.ModBlocks;
import tfar.unstabletools.init.ModItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

public class ModClientNeoForge {

  public static void init(IEventBus bus) {
    bus.addListener(ModClientNeoForge::setup);
    bus.addListener(ModClientNeoForge::colors);
  }

  static void colors(RegisterColorHandlersEvent.Item e) {
   ModItems.getItems().stream().filter(IItemColored.class::isInstance).forEach(item -> e.register(((IItemColored) item)::getColor, item));
  }

  static void setup(FMLClientSetupEvent event) {
    ItemBlockRenderTypes.setRenderLayer(ModBlocks.unstable_block, RenderType.cutout());
    ItemBlockRenderTypes.setRenderLayer(ModBlocks.ethereal_glass, RenderType.cutout());
    ItemProperties.register(ModItems.UNSTABLE_FISHING_ROD,ResourceLocation.parse("cast"), ModClient.FISHING);
    ItemProperties.register(ModItems.UNSTABLE_BOW,ResourceLocation.parse("pull"), ModClient.PULL);
  }

}
