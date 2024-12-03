package net.daichang.loli_pickaxe.mixins.timer;

import net.daichang.loli_pickaxe.util.HavenUtil.HavenUtil;
import net.minecraft.client.Minecraft;
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

        @Inject(method = "getDayTime", at = @At("RETURN"), cancellable = true)
        private void getDayTime(CallbackInfoReturnable<Long> cir){
            if(HavenUtil.isHaven()) if (Minecraft.getInstance().level != null) {
                int az = 0;
                long a1 = az++;
                cir.setReturnValue(a1);
            }
        }
    }
}
