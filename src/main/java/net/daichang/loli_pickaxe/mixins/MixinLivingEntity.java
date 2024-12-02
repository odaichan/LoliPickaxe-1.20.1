package net.daichang.loli_pickaxe.mixins;

import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.minecraft.DeathList;
import net.daichang.loli_pickaxe.util.LoliDefenseUtil;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.daichang.loli_pickaxe.util.Util.sMode;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @Shadow protected abstract int increaseAirSupply(int p_21307_);

    @Shadow public abstract void indicateDamage(double p_270514_, double p_270826_);

    @Shadow public abstract boolean isDeadOrDying();

    @Unique
    private final LivingEntity loli_pickaxe$living = (LivingEntity) (Object) this;

    @Inject(method = "getHealth", at = @At("RETURN"), cancellable = true)
    private void getHealth(CallbackInfoReturnable<Float> cir){
        if(DeathList.isList(loli_pickaxe$living) && sMode){
            cir.setReturnValue(0.0f);
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
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void hurt(DamageSource source, float p_21017_, CallbackInfoReturnable<Boolean> cir) {
        if(Util.isLoliEntity(loli_pickaxe$living) && !(loli_pickaxe$living instanceof Player)){
            cir.setReturnValue(false);
        }
    }
}
