package net.daichang.loli_pickaxe.mixins.timer;

import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class MixinMadeInHaven {
    @Mixin(Level.class)
    public static abstract class MixinLevel{
        @Unique
        private final Level loli_pickaxe$level =(Level) (Object) this;

        @Inject(method = "getDayTime", at = @At("RETURN"), cancellable = true)
        private void getDayTime(CallbackInfoReturnable<Long> cir){
            cir.setReturnValue(loli_pickaxe$level.dayTime()+100);
        }
    }
}
