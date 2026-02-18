package tfar.unstabletools;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tfar.unstabletools.init.ModItems;

@OnlyIn(Dist.CLIENT)
public class UnstableElytraLayer extends ElytraLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("unstabletools", "textures/entities/unstable_elytra.png");

    public UnstableElytraLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> rendererIn, EntityModelSet modelSet) {
        super(rendererIn, modelSet);
    }

    @Override
    public boolean shouldRender(ItemStack stack, AbstractClientPlayer entity) {
        return stack.getItem() == ModItems.UNSTABLE_ELYTRA;
    }

    @Override
    public ResourceLocation getElytraTexture(ItemStack stack, AbstractClientPlayer entity) {
        return TEXTURE_ELYTRA;
    }
}
