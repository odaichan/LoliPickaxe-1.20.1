package net.daichang.loli_pickaxe.util.core;

import net.daichang.loli_pickaxe.minecraft.DeathList;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("unused")
public class CoreMethod {
    public static float getHealth(LivingEntity living) {
        if(Util.isLoliEntity(living)){
            return living.getMaxHealth();
        }
        if(DeathList.isList(living)){
            return 0.0F;
        }
        return living.getHealth();
    }

    public static boolean isAlive(Entity entity) {
        if(DeathList.isList(entity)){
            return false;
        }
        return entity.isAlive();
    }

    public static boolean isDeadOrDying(LivingEntity entity) {
        if(Util.isLoliEntity(entity)){
            return false;
        }
        if(DeathList.isList(entity)){
            return true;
        }
        return entity.isDeadOrDying();
    }
}
