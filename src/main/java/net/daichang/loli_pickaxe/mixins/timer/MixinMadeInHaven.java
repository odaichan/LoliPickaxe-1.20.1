package net.daichang.loli_pickaxe.mixins.timer;

import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

public class MixinMadeInHaven {
    @Mixin(Level.class)
    public static abstract class MixinLevel{
        @Unique
        private final Level loli_pickaxe$level =(Level) (Object) this;
    }
}
