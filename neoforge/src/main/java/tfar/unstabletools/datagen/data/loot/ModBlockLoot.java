package tfar.unstabletools.datagen.data.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import tfar.unstabletools.init.ModBlocks;

import java.util.List;

public class ModBlockLoot extends VanillaBlockLoot {

    public ModBlockLoot(HolderLookup.Provider pRegistries) {
        super(pRegistries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.unstable_block);
        dropSelf(ModBlocks.ethereal_glass);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return List.of(ModBlocks.unstable_block,ModBlocks.ethereal_glass);
    }
}
