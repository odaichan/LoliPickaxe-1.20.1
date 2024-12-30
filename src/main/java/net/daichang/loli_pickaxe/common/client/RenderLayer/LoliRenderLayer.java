package net.daichang.loli_pickaxe.common.client.RenderLayer;

import net.daichang.loli_pickaxe.common.client.entityModel.ModelLoliEntity;
import net.daichang.loli_pickaxe.common.entity.EntityLoli;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LoliRenderLayer extends EnergySwirlLayer {
    private static final ResourceLocation ench = new ResourceLocation("textures/misc/enchanted_glint_item.png");

    private final ModelLoliEntity<EntityLoli> loli;

    public LoliRenderLayer(RenderLayerParent parent, EntityModelSet set) {
        super(parent);
        loli = new ModelLoliEntity<>(set.bakeLayer(ModelLayers.WITHER_ARMOR));
    }

    @Override
    protected float xOffset(float v) {
        return Mth.cos(v * 0.02F) * 3.0F;
    }

    @Override
    protected ResourceLocation getTextureLocation() {
        return ench;
    }

    @Override
    protected @NotNull EntityModel model() {
        return loli;
    }
}
