package tfar.unstabletools;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.neoforged.fml.config.ModConfig;

public class UnstableToolsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(UnstableTools.MOD_ID, ModConfig.Type.COMMON,Config.COMMON_SPEC);
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.
        ServerLivingEntityEvents.AFTER_DEATH.register(UnstableTools::livingDeath);
        // Use Fabric to bootstrap the Common mod.
        UnstableTools.init();
    }
}
