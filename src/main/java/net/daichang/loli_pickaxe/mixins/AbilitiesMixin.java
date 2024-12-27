package net.daichang.loli_pickaxe.mixins;

import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Abilities.class)
public class AbilitiesMixin {
    @Unique
    private final Minecraft loli_pickaxe$mc = Minecraft.getInstance();
    @Unique
    private final Player loli_pickaxe$player = loli_pickaxe$mc.player;

    @Inject(method = "getFlyingSpeed", at = @At("RETURN"), cancellable = true)
    private void getFlyingSpeed(CallbackInfoReturnable<Float> cir){
        if (loli_pickaxe$player != null && loli_pickaxe$player.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))){
            cir.setReturnValue(0.15F);
        }
    }

    @Inject(method = "getWalkingSpeed", at = @At("RETURN"), cancellable = true)
    private void getWalkingSpeed(CallbackInfoReturnable<Float> cir){
        if (loli_pickaxe$player != null && loli_pickaxe$player.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get())) && Util.sMode){
            cir.setReturnValue(0.2F);
        }
    }
}
