package tfar.unstabletools.init;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import tfar.unstabletools.EtherealGlassBlock;

public class ModBlocks {

    public static final Block unstable_block = new Block(Block.Properties.of().strength(50, 6000).requiresCorrectToolForDrops()) {

        @Override
        public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
            return adjacentBlockState.getBlock() == this;
        }
    };

    public static final Block ethereal_glass = new EtherealGlassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).mapColor(MapColor.COLOR_GRAY).noOcclusion()
            .isValidSpawn((p_50806_, p_50807_, p_50808_, p_50808_2) -> false).isRedstoneConductor(ModBlocks::never).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never));


    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }
}