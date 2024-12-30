package net.daichang.loli_pickaxe.common.client.entityRender;

import net.daichang.loli_pickaxe.common.client.entityModel.ModelLoliEntity;
import net.daichang.loli_pickaxe.common.entity.EntityLoli;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LoliRender extends MobRenderer<EntityLoli, ModelLoliEntity<EntityLoli>> {
    private final ResourceLocation location = new ResourceLocation("loli_pickaxe", "textures/entity/loli.png");
    private static final ResourceLocation WITHER_INVULNERABLE_LOCATION = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");

    public LoliRender(EntityRendererProvider.Context context) {
        super(context, new ModelLoliEntity(context.bakeLayer(ModelLoliEntity.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityLoli entity) {
        return location;
    }
}
