package net.daichang.loli_pickaxe.common.client.entityRender;

import net.daichang.loli_pickaxe.common.client.entityModel.ModelLoliEntity;
import net.daichang.loli_pickaxe.common.entity.Boss.EntityLoliGod;
import net.daichang.loli_pickaxe.minecraft.Fonts.LoliFont;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LoliGodRender extends MobRenderer<EntityLoliGod, ModelLoliEntity<EntityLoliGod>> {
    private final ResourceLocation location = new ResourceLocation("loli_pickaxe", "textures/entity/loli.png");
    public LoliGodRender(EntityRendererProvider.Context context) {
        super(context, new ModelLoliEntity(context.bakeLayer(ModelLoliEntity.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityLoliGod entityLoliGod) {
        return location;
    }

    @Override
    public @NotNull Font getFont() {
        return LoliFont.getFont();
    }
}
