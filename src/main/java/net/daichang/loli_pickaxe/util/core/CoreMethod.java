package net.daichang.loli_pickaxe.util.core;

import net.daichang.loli_pickaxe.common.entity.EntityLoli;
import net.daichang.loli_pickaxe.minecraft.DeathList;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import static net.daichang.loli_pickaxe.Config.Config.sMode;

@SuppressWarnings("unused")
public class CoreMethod {
    public static float getHealth(LivingEntity living) {
        if (Util.isLoliEntity(living) || (living instanceof Player player && Util.isHasLoliPickaxe(player))){
            return living.getMaxHealth();
        } else if (living instanceof EntityLoli) {
            return 20.0F;
        } else if (sMode && DeathList.isList(living)){
            return 0.0F;
        } else {
            return living.getHealth();
        }
    }

    public static boolean isAlive(Entity entity){
        if (entity instanceof Player player && Util.isHasLoliPickaxe(player)){
            return true;
        } else if (sMode && DeathList.isList(entity)) {
            return false;
        } else {
            return entity.isAlive();
        }
    }

    public static boolean isDeadOrDying(LivingEntity living){
        if (Util.isLoliEntity(living) || (living instanceof Player player && Util.isHasLoliPickaxe(player))){
            return false;
        } else if (sMode && DeathList.isList(living)) {
            return true;
        } else {
            return living.isDeadOrDying();
        }
    }
}
