package tfar.unstabletools;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Config {

  public static final ServerConfig SERVER;
  public static final ModConfigSpec COMMON_SPEC;

  static {
    final Pair<ServerConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(ServerConfig::new);
    COMMON_SPEC = specPair.getRight();
    SERVER = specPair.getLeft();
  }

  public static class ServerConfig {

    public static ModConfigSpec.ConfigValue<List<String>> allowed_containers;
    public static ModConfigSpec.BooleanValue cursed_earth_integration;
    public static ModConfigSpec.IntValue timer;
    public static ModConfigSpec.IntValue uses;

    ServerConfig(ModConfigSpec.Builder builder) {
      List<String> strings = new ArrayList<>();
      strings.add(BuiltInRegistries.MENU.getKey(MenuType.CRAFTING).toString());
      strings.add("fastbench:fastbench");
      builder.push("general");
      allowed_containers = builder
              .comment("Allowed Container Types")
              .translation("text.unstabletools.config.allowed_containers")
              .define("containers", strings, List.class::isInstance);
      cursed_earth_integration = builder
              .comment("Enable integration with Cursed Earth")
              .translation("text.unstabletools.config.cursed_earth_integration")
              .define("cursed earth integration",true);
      timer = builder
              .comment("Time before explosion in ticks")
              .translation("text.unstabletools.config.timer")
              .defineInRange("timer",200, 1,Integer.MAX_VALUE);

      uses = builder
              .comment("Number of uses when charged")
              .translation("text.unstabletools.config.uses")
              .defineInRange("uses",256, 1,Integer.MAX_VALUE);
      builder.pop();
    }
  }
}
