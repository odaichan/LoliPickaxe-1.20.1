package net.daichang.loli_pickaxe.mixins;

import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.minecraft.player.client.ClientLoliPlayer;
import net.daichang.loli_pickaxe.minecraft.player.server.ServerLoliPlayer;
import net.daichang.loli_pickaxe.util.HelperLib;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static net.minecraft.world.level.block.LiquidBlock.STABLE_SHAPE;

public class Mixins {
    @Mixin(ServerPlayer.class)
    public static abstract class MixinServerPlayer{
        @Unique
        private final ServerPlayer loli_pickaxe$serverPlayer = (ServerPlayer) (Object)this;

        @Inject(method = "tick", at = @At("HEAD"))
        private void tick(CallbackInfo ci){
            HelperLib.replaceClass(loli_pickaxe$serverPlayer, ServerLoliPlayer.class);
        }
    }

    @Mixin(LocalPlayer.class)
    public static class MixinLocalPlayer{
        @Unique
        private final LocalPlayer loli_pickaxe$localPlayer = (LocalPlayer) (Object) this;

        @Inject(method = "tick", at = @At("HEAD"))
        private void tick(CallbackInfo ci){
            HelperLib.replaceClass(loli_pickaxe$localPlayer, ClientLoliPlayer.class);
        }
    }

    @Mixin(LiquidBlock.class)
    public static class MixinLiquidBlock extends Block{
        @Shadow @Final private List<FluidState> stateCache;

        @Shadow @Final public static IntegerProperty LEVEL;

        public MixinLiquidBlock(Properties p_49795_) {
            super(p_49795_);
            this.registerDefaultState(this.getStateDefinition().any().setValue(LEVEL, 1));
        }

        /**
         * @author
         * @reason
         */
        @Overwrite(remap = false)
        public @NotNull VoxelShape getCollisionShape(BlockState p_54760_, BlockGetter p_54761_, BlockPos p_54762_, CollisionContext p_54763_) {
            Minecraft mc = Minecraft.getInstance();
            if (Util.lquidWalk && mc.player != null && mc.player.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get()) {
                return Shapes.block();
            } else {
                return p_54763_.isAbove(STABLE_SHAPE, p_54762_, true) && (Integer) p_54760_.getValue(LEVEL) == 0 && p_54763_.canStandOnFluid(p_54761_.getFluidState(p_54762_.above()), p_54760_.getFluidState()) ? STABLE_SHAPE : Shapes.empty();
            }
        }

        /**
         * @author
         * @reason
         */
        @Overwrite(remap = false)
        public RenderShape getRenderShape(BlockState p_54738_) {
            Minecraft mc = Minecraft.getInstance();
            if (Util.lquidWalk&& mc.player != null && mc.player.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get()){
                return RenderShape.MODEL;
            }else {
                return RenderShape.INVISIBLE;
            }
        }

        /**
         * @author
         * @reason
         */
        @Overwrite(remap = false)
        public VoxelShape getShape(BlockState p_54749_, BlockGetter p_54750_, BlockPos p_54751_, CollisionContext p_54752_) {
            Minecraft mc = Minecraft.getInstance();
            if (Util.displayFluidBorder&& mc.player != null && mc.player.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get()){
                return Shapes.block();
            }else {
                return Shapes.empty();
            }
        }
    }
}
