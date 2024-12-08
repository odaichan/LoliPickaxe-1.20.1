package net.daichang.loli_pickaxe.mixins.timer;

import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class MixinMadeInHaven {
    @Mixin(Level.class)
    public static abstract class MixinLevel{
        @Shadow public abstract long getDayTime();

        @Inject(method = "getDayTime", at = @At("RETURN"))
        private void getDayTime(CallbackInfoReturnable<Long> cir){
        }
    }
}
