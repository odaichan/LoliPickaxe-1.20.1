package net.daichang.loli_pickaxe.mixins;

import net.daichang.loli_pickaxe.common.entity.Boss.EntityLoliGod;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.minecraft.DeathList;
import net.daichang.loli_pickaxe.util.LoliDefenseUtil;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @Shadow protected abstract int increaseAirSupply(int p_21307_);

    @Shadow public abstract void indicateDamage(double p_270514_, double p_270826_);

    @Shadow public abstract void remove(Entity.RemovalReason p_276115_);

    @Shadow protected abstract boolean trapdoorUsableAsLadder(BlockPos p_21177_, BlockState p_21178_);

    @Shadow protected abstract void tickDeath();

    @Unique private final LivingEntity loli_pickaxe$living = (LivingEntity) (Object) this;

    @Inject(method = "getHealth", at = @At("RETURN"), cancellable = true)
    private void getHealth(CallbackInfoReturnable<Float> cir){
        if(DeathList.isList(loli_pickaxe$living)){
            cir.setReturnValue(0.0F);
        }
        if (loli_pickaxe$living instanceof Player && ((Player) loli_pickaxe$living).getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))){
            cir.setReturnValue(loli_pickaxe$living.getMaxHealth());
        }
        if(Util.isLoliEntity(loli_pickaxe$living)&& !(loli_pickaxe$living instanceof Player)){
            cir.setReturnValue(loli_pickaxe$living.getMaxHealth());
        }
    }

    @Inject(method = "tickDeath", at =@At("RETURN"), cancellable = true)
    private void tickDeath(CallbackInfo ci){
        if (loli_pickaxe$living instanceof Player && ((Player) loli_pickaxe$living).getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))){
            ci.cancel();
        }
    }

    @Inject(method = "tick", at= @At("HEAD"))
    private void tick(CallbackInfo ci){
        if (loli_pickaxe$living instanceof Player && ((Player) loli_pickaxe$living).getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))){
            LoliDefenseUtil.loliDefense((Player) loli_pickaxe$living);
        }
        if(Util.isLoliEntity(loli_pickaxe$living) && !(loli_pickaxe$living instanceof Player)){
            LoliDefenseUtil.safeEntity(loli_pickaxe$living);
        }
        if (DeathList.isList(loli_pickaxe$living)) {
            this.tickDeath();
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void hurt(DamageSource source, float p_21017_, CallbackInfoReturnable<Boolean> cir) {
        if(Util.isLoliEntity(loli_pickaxe$living) && !(loli_pickaxe$living instanceof Player)){
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isAlive", at = @At("HEAD"), cancellable = true)
    private void isAlive(CallbackInfoReturnable<Boolean> cir) {
        if(loli_pickaxe$living.getHealth()>0){
            cir.setReturnValue(true);
        }
        if (loli_pickaxe$living.getHealth()<=0){
            cir.setReturnValue(false);
        }
        if (Util.isLoliEntity(loli_pickaxe$living)){
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "remove", at= @At("HEAD"), cancellable = true)
    private void remove(Entity.RemovalReason p_276115_, CallbackInfo ci){
        if(loli_pickaxe$living instanceof EntityLoliGod){
            ci.cancel();
        }
    }

}