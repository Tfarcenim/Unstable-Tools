package tfar.unstabletools.platform;

import net.minecraft.core.Registry;
import tfar.unstabletools.UnstableTools;
import tfar.unstabletools.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.util.Map;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <F> void registerAll(Map<String,? extends F> map, Registry<F> registry, Class<? extends F> filter) {
        for (Map.Entry<String,? extends F> entry : map.entrySet()) {
            Registry.register(registry, UnstableTools.id(entry.getKey()),entry.getValue());
        }
    }
}
