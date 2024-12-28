package tfar.unstabletools.init;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

public class ModDataComponents {
    public static final DataComponentType<Integer> TIMER = DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT)
            .networkSynchronized(ByteBufCodecs.INT).build();

    public static final DataComponentType<Integer> USES = DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT)
            .networkSynchronized(ByteBufCodecs.INT).build();
}
