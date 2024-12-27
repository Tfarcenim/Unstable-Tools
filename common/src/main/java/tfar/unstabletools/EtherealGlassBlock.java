package tfar.unstabletools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EtherealGlassBlock extends TransparentBlock {


    public EtherealGlassBlock(Properties p_309186_) {
        super(p_309186_);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pContext instanceof EntityCollisionContext entityCollisionContext &&
                entityCollisionContext.getEntity() instanceof Player player ? Shapes.empty(): super.getCollisionShape(pState, pLevel, pPos, pContext);
    }
}
