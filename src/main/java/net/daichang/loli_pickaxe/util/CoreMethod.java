package net.daichang.loli_pickaxe.util;

import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.minecraft.DeathList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("unused")
public class CoreMethod {
    public static float getHealth(LivingEntity living){
        if (living.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get()){
            return 20.0F;
        }else if (DeathList.isList(living)){
            return 0.0F;
        }
        return living.getHealth();
    }

    public static boolean isDeadOrDying(LivingEntity entity) {
        if (entity.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get()){
            return false;
        } else if (DeathList.isList(entity)) {
            return true;
        } else {
            return entity.isDeadOrDying();
        }
    }

    public static boolean isAlive(Entity entity) {
        if (DeathList.isList(entity)) {
            return false;
        } else {
            return entity.isAlive();
        }
    }
}
