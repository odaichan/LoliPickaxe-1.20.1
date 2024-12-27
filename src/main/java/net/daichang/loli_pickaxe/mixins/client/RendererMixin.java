package net.daichang.loli_pickaxe.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
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

    @Mixin(ForgeGui.class)
    public static class MixinForgeGui extends Gui {
        @Unique
        public int loli_pickaxe$leftHeight = 39;
        public MixinForgeGui(Minecraft p_232355_, ItemRenderer p_232356_) {
            super(p_232355_, p_232356_);
        }

        /**
         * @author
         * @reason
         */
//        @Overwrite(remap = false)
//        public void renderHealth(int width, int height, GuiGraphics guiGraphics) {
//            this.minecraft.getProfiler().push("health");
//            RenderSystem.enableBlend();
//            Player player = (Player)this.minecraft.getCameraEntity();
//            int health = 0;
//            if (player != null) {
//                health = Mth.ceil(player.getHealth());
//                if (player.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))) {
//                    health = Mth.ceil(player.getMaxHealth());
//                }
//            }
//            boolean highlight = this.healthBlinkTime > (long)this.tickCount && (this.healthBlinkTime - (long)this.tickCount) / 3L % 2L == 1L;
//            if (health < this.lastHealth && player.invulnerableTime > 0) {
//                this.lastHealthTime = Util.getMillis();
//                this.healthBlinkTime = (long)(this.tickCount + 20);
//            } else if (health > this.lastHealth && player.invulnerableTime > 0) {
//                this.lastHealthTime = Util.getMillis();
//                this.healthBlinkTime = (long)(this.tickCount + 10);
//            }
//
//            if (Util.getMillis() - this.lastHealthTime > 1000L) {
//                this.lastHealth = health;
//                this.displayHealth = health;
//                this.lastHealthTime = Util.getMillis();
//            }
//
//            this.lastHealth = health;
//            int healthLast = this.displayHealth;
//            AttributeInstance attrMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
//            float healthMax = Math.max((float)attrMaxHealth.getValue(), (float)Math.max(healthLast, health));
//            int absorb = Mth.ceil(player.getAbsorptionAmount());
//            int healthRows = Mth.ceil((healthMax + (float)absorb) / 2.0F / 10.0F);
//            int rowHeight = Math.max(10 - (healthRows - 2), 3);
//            this.random.setSeed((long)(this.tickCount * 312871));
//            int left = width / 2 - 91;
//            int top = height - this.loli_pickaxe$leftHeight;
//            this.loli_pickaxe$leftHeight += healthRows * rowHeight;
//            if (rowHeight != 10) {
//                this.loli_pickaxe$leftHeight += 10 - rowHeight;
//            }
//
//            int regen = -1;
//            if (player.hasEffect(MobEffects.REGENERATION)) {
//                regen = this.tickCount % Mth.ceil(healthMax + 5.0F);
//            }
//
//            this.renderHearts(guiGraphics, player, left, top, rowHeight, regen, healthMax, health, healthLast, absorb, highlight);
//            RenderSystem.disableBlend();
//            this.minecraft.getProfiler().pop();
//        }
    }
}
