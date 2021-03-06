package com.tfar.unstabletools;

import com.tfar.unstabletools.item.IItemColored;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT,modid = UnstableTools.MODID)
public class Client {
  @SubscribeEvent
  public static void colors(ColorHandlerEvent.Item e) {
    UnstableTools.MOD_ITEMS.stream().filter(IItemColored.class::isInstance).forEach(item -> e.getItemColors().register(((IItemColored) item)::getColor, item));
  }
}
