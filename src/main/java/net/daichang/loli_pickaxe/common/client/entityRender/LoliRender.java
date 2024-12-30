package net.daichang.loli_pickaxe.common.client.entityRender;

import net.daichang.loli_pickaxe.common.client.entityModel.ModelLoliEntity;
import net.daichang.loli_pickaxe.common.entity.EntityLoli;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class LoliRender extends MobRenderer<EntityLoli, ModelLoliEntity<EntityLoli>> {
    private final ResourceLocation location = new ResourceLocation("loli_pickaxe", "textures/entity/loli.png");

    public LoliRender(EntityRendererProvider.Context context) {
        super(context, new ModelLoliEntity(context.bakeLayer(ModelLoliEntity.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityLoli entity) {
        return location;
    }

    @Override
    protected @Nullable RenderType getRenderType(EntityLoli p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
        return super.getRenderType(p_115322_, p_115323_, p_115324_, p_115325_);
    }
}
