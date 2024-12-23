package net.daichang.loli_pickaxe.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

public class RendererMixin {
    @Mixin(ItemRenderer.class)
    public static abstract class MixinItemRender{

        @Shadow public abstract void renderModelLists(BakedModel p_115190_, ItemStack p_115191_, int p_115192_, int p_115193_, PoseStack p_115194_, VertexConsumer p_115195_);

        @Shadow @Final private ItemColors itemColors;
        @Unique
        public ItemDisplayContext context;

        @Inject(method = "render", at = @At("RETURN"))
        private void render(ItemStack p_115144_, ItemDisplayContext p_270188_, boolean p_115146_, PoseStack p_115147_, MultiBufferSource p_115148_, int p_115149_, int p_115150_, BakedModel p_115151_, CallbackInfo ci) {
            context = p_270188_;
        }

        @Inject(method = "renderModelLists", at=@At("RETURN"))
        private void renderModelLists(BakedModel p_115190_, ItemStack stack, int p_115192_, int p_115193_, PoseStack p_115194_, VertexConsumer p_115195_, CallbackInfo ci){
            if (stack.getItem() == ItemRegister.VoidTotemItem.get()){
                this.itemColors.getColor(stack, new Random().nextInt());
            }
        }
    }
}