package net.daichang.loli_pickaxe.mixins;

import net.daichang.loli_pickaxe.Config.Config;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.minecraft.player.client.ClientLoliPlayer;
import net.daichang.loli_pickaxe.minecraft.player.server.ServerLoliPlayer;
import net.daichang.loli_pickaxe.util.HelperLib;
import net.daichang.loli_pickaxe.util.TextUtil;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.ASMEventHandler;
import net.minecraftforge.eventbus.api.Event;
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
            if (Config.liquidWalk && mc.player != null && mc.player.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get()) {
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
            if (Config.liquidWalk && mc.player != null && mc.player.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get()){
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
            if (Config.displayFluidBorder&& mc.player != null && mc.player.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get()){
                return Shapes.block();
            }else {
                return Shapes.empty();
            }
        }
    }
    
    @Mixin(value = {ASMEventHandler.class}, priority = 2147483647, remap = false)
    public static class MixinASMEventHandler{
        @Inject(method = "invoke", at= @At("RETURN"), cancellable = true)
        private void invoke(Event event, CallbackInfo ci){
            ItemStack stack = new ItemStack(ItemRegister.LoliPickaxe.get());
            if (event instanceof PlayerInteractEvent.LeftClickBlock){
                PlayerInteractEvent.LeftClickBlock e = (PlayerInteractEvent.LeftClickBlock) event;
                Player player = e.getEntity();
                Level level = e.getLevel();
                BlockPos pos = e.getPos();
                BlockState state = level.getBlockState(pos);
                Block block = state.getBlock();
                if (player.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get() && Config.forcedExcavation){
                    ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), (new ItemStack(block)));
                    level.addFreshEntity(item);
                    item.setPickUpDelay(0);
                    level.destroyBlock(pos, false, player);
                    if (block instanceof LiquidBlock){
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
                    }
                }
            }
            if (event instanceof LivingDeathEvent){
                LivingDeathEvent event1 = (LivingDeathEvent) event;
                LivingEntity living = event1.getEntity();
                if (Util.isLoliEntity(living)){
                    ci.cancel();
                }
                if (living instanceof Player player && player.getInventory().contains(stack)){
                    ci.cancel();
                }
                if (living.getRemovalReason() ==null){
                    living.removalReason = Entity.RemovalReason.KILLED;
                }
            }
            if (event instanceof LivingHurtEvent){
                LivingHurtEvent e = (LivingHurtEvent) event;
                LivingEntity living = e.getEntity();
                if (Util.isLoliEntity(living)){
                    ci.cancel();
                }
                if (living instanceof Player player && player.getInventory().contains(stack)){
                    ci.cancel();
                }
            }
            if (event instanceof BlockEvent.BreakEvent){
                BlockEvent.BreakEvent e = (BlockEvent.BreakEvent) event;
                Player player = e.getPlayer();
                Item item=ItemRegister.LoliPickaxe.get();
                LevelAccessor levelAccessor = e.getLevel();
                int loliRange = Config.breakrange;
                BlockPos pos = e.getPos();
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();
                BlockPos loliPos = new BlockPos(pos.getX() + loliRange, pos.getY()  + loliRange, pos.getZ() + loliRange);
                BlockState state = levelAccessor.getBlockState(loliPos);
                Block block = state.getBlock();
                if (player.getMainHandItem().getItem() == item && levelAccessor instanceof Level level){
                    ItemEntity itemEntity = new ItemEntity(level, x, y, z, (new ItemStack(block)));
                    level.destroyBlock(loliPos, true, player);
                }
            }
            if (event instanceof ItemTooltipEvent){
                ItemTooltipEvent tooltipEvent = (ItemTooltipEvent) event;
                if (tooltipEvent.getItemStack().getItem() == ItemRegister.LoliPickaxe.get()) {
                    List<Component> tooltip = tooltipEvent.getToolTip();
                    int size = tooltip.size();
                    MutableComponent mutableComponent1 = Component.translatable("attribute.name.generic.attack_damage");
                    MutableComponent mutableComponent2 = Component.translatable("attribute.name.generic.attack_speed");
                    String var10000 = TextUtil.GetColor("TREE(3) ");
                    MutableComponent mutableComponent3 = Component.literal(" " + var10000 + ChatFormatting.DARK_GREEN + " 攻击伤害");
                    MutableComponent mutableComponent4 = Component.literal(" " + var10000 + ChatFormatting.DARK_GREEN + " 攻击速度");
                    for (int i = 0; i < size; i++) {
                        Component line = tooltip.get(i);
                        if (line.contains(mutableComponent1))
                            tooltip.set(i, mutableComponent3);
                        if (line.contains(mutableComponent2))
                            tooltip.set(i, mutableComponent4);
                    }
                }
            }
        }
    }
}
