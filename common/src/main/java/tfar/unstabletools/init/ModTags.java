package tfar.unstabletools.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import tfar.unstabletools.UnstableTools;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> INCORRECT_FOR_UNSTABLE_TOOL = mod("incorrect_for_unstable_tool");
        private static TagKey<Block> mod(String pName) {
            return TagKey.create(Registries.BLOCK, UnstableTools.id(pName));
        }
    }
}
