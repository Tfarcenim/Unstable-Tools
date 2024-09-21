package tfar.unstabletools;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TranslationKeys {
    
    public static final MutableComponent EARLY =  Component.translatable("info.unstabletools.early");
    public static final MutableComponent ON_TIME =  Component.translatable("info.unstabletools.ontime");
    public static final MutableComponent LATE =  Component.translatable("info.unstabletools.late");
    public static final MutableComponent INCOMPLETE_REDSTONE =  Component.translatable("info.unstabletools.incomplete_redstone");
    public static final MutableComponent NO_SKY =  Component.translatable("info.unstabletools.no_sky");
    public static final MutableComponent READY =  Component.translatable("info.unstabletools.ready" );
    public static final MutableComponent INACTIVE =  Component.translatable("info.unstabletools.inactive");
    public static final MutableComponent ACTIVE =  Component.translatable("info.unstabletools.active");
    public static final MutableComponent STABLE =  Component.translatable("info.unstabletools.stable");

    public static final MutableComponent UNSTABLE_INGOT_TOOLTIP =  Component.translatable("info.unstabletools.unstable_ingot.tooltip").withStyle(ChatFormatting.AQUA);

    public static final MutableComponent DROPS_FROM_WITHER = Component.translatable("info.unstabletools.drops_from_wither").withStyle(ChatFormatting.AQUA);

    public static MutableComponent usesLeft(int u) {
        return Component.translatable("info.unstabletools.uses_left",u);
    }

    public static MutableComponent timeLeft(int u) {
        return Component.translatable("info.unstabletools.time_left",u);
    }
}
