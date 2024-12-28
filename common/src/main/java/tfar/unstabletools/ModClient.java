package tfar.unstabletools;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;

public class ModClient {
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
        return living.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration(living) - living.getUseItemRemainingTicks()) / 20.0F;
      }
    };
}
