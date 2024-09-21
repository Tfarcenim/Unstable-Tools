package tfar.unstabletools;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tfar.unstabletools.init.ModBlocks;
import tfar.unstabletools.init.ModItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT,modid = UnstableTools.MOD_ID)
public class Client {
  @SubscribeEvent
  public static void colors(RegisterColorHandlersEvent.Item e) {
    ModItems.getItems().stream().filter(IItemColored.class::isInstance).forEach(item -> e.register(((IItemColored) item)::getColor, item));
    ItemBlockRenderTypes.setRenderLayer(ModBlocks.unstable_block, RenderType.cutout());
    ItemBlockRenderTypes.setRenderLayer(ModBlocks.ethereal_glass, RenderType.cutout());
  }

  public static final ClampedItemPropertyFunction FISHING =  (stack, p_174586_, living, p_174588_) -> {
    if (living == null) {
      return 0.0F;
    } else {
      boolean flag = living.getMainHandItem() == stack;
      boolean flag1 = living.getOffhandItem() == stack;
      if (living.getMainHandItem().getItem() instanceof FishingRodItem) {
        flag1 = false;
      }

      return (flag || flag1) && living instanceof Player player && player.fishing != null ? 1.0F : 0.0F;
    }
  };

  public static final ClampedItemPropertyFunction PULL = (stack, p_174636_, living, p_174638_) -> {
    if (living == null) {
      return 0.0F;
    } else {
      return living.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F;
    }
  };

  @SubscribeEvent
  public static void setup(FMLClientSetupEvent event) {
    ItemProperties.register(ModItems.UNSTABLE_FISHING_ROD,new ResourceLocation("cast"),FISHING);
    ItemProperties.register(ModItems.UNSTABLE_BOW,new ResourceLocation("pull"),PULL);
  }

}
