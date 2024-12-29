package tfar.unstabletools.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.unstabletools.UnstableTools;

@Mixin(Player.class)
public class PlayerMixinFabric {
    @Inject(method = "tick",at = @At("RETURN"))
    private void onPlayerTick(CallbackInfo ci) {
        if ((Object)this instanceof ServerPlayer serverPlayer) {
            UnstableTools.playerTick(serverPlayer);
        }
    }
}
