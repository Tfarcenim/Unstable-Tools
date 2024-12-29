package tfar.unstabletools.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.unstabletools.UnstableTools;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixinFabric extends Player {
    public ServerPlayerMixinFabric(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @Inject(method = "doCloseContainer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;transferState(Lnet/minecraft/world/inventory/AbstractContainerMenu;)V",shift = At.Shift.AFTER))
    private void onCloseContainer(CallbackInfo ci){
        UnstableTools.containerClosed(containerMenu,this);
    }
}
