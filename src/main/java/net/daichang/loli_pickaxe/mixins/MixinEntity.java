package net.daichang.loli_pickaxe.mixins;

import net.daichang.loli_pickaxe.common.entity.Boss.EntityLoliGod;
import net.daichang.loli_pickaxe.minecraft.ClassTargetList;
import net.daichang.loli_pickaxe.util.LoliAttackUtil;
import net.daichang.loli_pickaxe.util.LoliDefenseUtil;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.daichang.loli_pickaxe.util.Util.classTarget;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow public abstract InteractionResult interact(Player p_19978_, InteractionHand p_19979_);

    @Shadow public abstract boolean isColliding(BlockPos p_20040_, BlockState p_20041_);

    @Shadow public abstract boolean equals(Object p_20245_);

    @Shadow public SynchedEntityData entityData;

    @Unique
    private final Entity loli_pickaxe$entity = (Entity) (Object) this;


    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci){
        if(loli_pickaxe$entity instanceof LivingEntity && Util.isLoliEntity((LivingEntity) loli_pickaxe$entity)){
            LoliDefenseUtil.safeEntity(loli_pickaxe$entity);
        }
        if(classTarget && ClassTargetList.isTargetList(loli_pickaxe$entity)){
            LoliAttackUtil.removeEntity(loli_pickaxe$entity);
        }
    }

    @Inject(method = "remove", at= @At("HEAD"), cancellable = true)
    private void remove(Entity.RemovalReason p_146834_, CallbackInfo ci){
        if(loli_pickaxe$entity instanceof EntityLoliGod){
            ci.cancel();
        }
    }

    @Inject(method = "isRemoved", at= @At("RETURN"), cancellable = true)
    private void isRemoved(CallbackInfoReturnable<Boolean> cir){
        if(loli_pickaxe$entity instanceof EntityLoliGod){
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "setRemoved", at= @At("HEAD"), cancellable = true)
    private void setRemoved(Entity.RemovalReason p_146876_, CallbackInfo ci){
        if(loli_pickaxe$entity instanceof EntityLoliGod){
            ci.cancel();
        }
    }
}
