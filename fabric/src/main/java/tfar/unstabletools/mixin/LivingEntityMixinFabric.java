package tfar.unstabletools.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.unstabletools.init.ModItems;

@Mixin(WitherBoss.class)
public abstract class LivingEntityMixinFabric extends LivingEntity {
    protected LivingEntityMixinFabric(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "dropCustomDeathLoot",at = @At("RETURN"))
    private void dropsEvent(ServerLevel level, DamageSource damageSource, boolean recentlyHit, CallbackInfo ci) {
        spawnAtLocation(ModItems.INACTIVE_DIVISION_SIGN);
    }
}
