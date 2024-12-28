package tfar.unstabletools.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import tfar.unstabletools.IItemColored;
import tfar.unstabletools.TranslationKeys;
import tfar.unstabletools.UnstableTools;
import tfar.unstabletools.init.ModDataComponents;

import java.util.List;

public class UnstableIngotItem extends Item implements IItemColored {

  public static final ResourceKey<DamageType> DIVIDE_BY_DIAMOND = ResourceKey.create(Registries.DAMAGE_TYPE, UnstableTools.id("divide_by_diamond"));

  public UnstableIngotItem(Properties properties) {
    super(properties);
  }

  @Override
  public void appendHoverText(ItemStack stack, TooltipContext pContext, List<Component> tooltip, TooltipFlag pTooltipFlag) {
    if (Screen.hasShiftDown()){
      tooltip.add(TranslationKeys.UNSTABLE_INGOT_TOOLTIP);
    }
    if (!stack.has(ModDataComponents.TIMER)) {
      tooltip.add(TranslationKeys.STABLE);
      return;
    }
    int timer = getTimer(stack);
    tooltip.add(TranslationKeys.timeLeft(timer));
  }

  public static void boom(Player player) {
    Level world = player.level();
    world.explode(null, player.getX(), player.getY(), player.getZ(), 1, Level.ExplosionInteraction.NONE);
    player.hurt(player.damageSources().source(DIVIDE_BY_DIAMOND), 100);
  }

  @Override
  public int getColor(ItemStack stack, int tintIndex) {
    if (!stack.has(ModDataComponents.TIMER)) {
      return 0xffffffff;
    } else {
      int time = getTimer(stack);
      double scale = time / 200d;

      int red, green, blue;

      if (scale >= .5) {
        red = green = 0xff;

        blue = (int) ((2 * scale - 1) * 0xff);
      } else if (scale >= .25) {
        red = 0xff;
        green = (int) (2 * scale * 0xff);
        blue = 0;
      } else {
        scale *= 256;
        scale = Math.floor(scale);
        scale %= 2;
        switch ((int) scale) {
          case 0: {
            red = 0xff;
            green = blue = 0;
            break;
          }
          case 1: {
            red = green = 0xff;
            blue = 0;
            break;
          }
          default:
            //this should never be anything other than 1 or 0
            throw new IllegalStateException("thonk"+scale);
        }
      }
      return 0xff000000| (red << 16) + (green << 8) + blue;
    }
  }

  public static int getTimer(ItemStack stack) {
    return stack.getOrDefault(ModDataComponents.TIMER, -1);
  }

  public static boolean checkExplosion(ItemStack stack) {
    return stack.getItem() instanceof UnstableIngotItem && stack.has(ModDataComponents.TIMER);
  }
}