package net.daichang.loli_pickaxe.mixins;

import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.util.LoliAttackUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class MixinPlayer {
    @Unique
    private final ItemStack loli_pickaxe$loliPickaxe = new ItemStack(ItemRegister.LoliPickaxe.get());

    @Unique
    private final Player loli_pickaxe$loli = (Player) (Object) this;

    @Inject(method = "hurt", at = @At("RETURN"), cancellable = true)
    private void hurt(DamageSource source, float p_36155_, CallbackInfoReturnable<Boolean> cir){
        Entity target = source.getEntity();
        if(loli_pickaxe$loli.getInventory().contains(loli_pickaxe$loliPickaxe) && target instanceof LivingEntity){
            LoliAttackUtil.killEntity(loli_pickaxe$loli,target);
            cir.setReturnValue(false);
        }
        if(loli_pickaxe$loli.isUsingItem() && loli_pickaxe$loli.getMainHandItem().getItem() == ItemRegister.Test.get() && target != null){
            target.hurt(source, p_36155_);
        }
    }
}
