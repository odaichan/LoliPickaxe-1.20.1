package net.daichang.loli_pickaxe.util.core;

import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("unused")
public class CoreMethod {
    public static float getHealth(LivingEntity living) {
        return switch (Util.getCategory(living)){
            case isLoliEntity -> living.getMaxHealth();
            case notLoliEntity -> 0.0F;
            case normalEntity -> living.getHealth();
        };
    }

    public static boolean isAlive(Entity entity){
        return switch (Util.getCategory(entity)){
            case isLoliEntity -> true;
            case normalEntity -> entity.isAlive();
            case notLoliEntity -> false;
        };
    }

    public static boolean isDeadOrDying(LivingEntity living){
        return switch (Util.getCategory(living)){
            case isLoliEntity -> false;
            case normalEntity -> living.isDeadOrDying();
            case notLoliEntity -> true;
        };
    }
}
