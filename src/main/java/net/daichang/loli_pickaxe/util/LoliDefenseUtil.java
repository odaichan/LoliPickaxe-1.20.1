package net.daichang.loli_pickaxe.util;

import net.daichang.loli_pickaxe.common.entity.EntityLoli;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class LoliDefenseUtil {
    public static void loliDefense(Player player){
        player.setHealth(player.getMaxHealth());
        player.setAirSupply(300);
        player.clearFire();
        player.getFoodData().setFoodLevel(Integer.MAX_VALUE);
        player.getFoodData().setSaturation(Float.MAX_VALUE);
        player.setTicksFrozen(-2);
        for (String s : player.getTags()) player.removeTag(s);
    }

    public static void safeEntity(Entity entity){
        if(!(entity instanceof Player) && !(entity instanceof EntityLoli)) {
            entity.removalReason = null;
            entity.clearFire();
            entity.fallDistance = 0;
            if (entity instanceof LivingEntity target) {
                target.invulnerableTime = -2;
                target.removeStingerTime = -2;
                target.hurtTime = 0;
                target.deathTime = 0;
                target.setHealth(target.getMaxHealth());
                target.setAirSupply(300);
                target.setTicksFrozen(-2);
            }
        } else if (entity instanceof EntityLoli loli) {
            loli.fallDistance = 0;
            loli.isAlive();
            loli.setHealth(20.0F);
            Util.Override_DATA_HEALTH_ID(loli, 20.0F);
        }
    }
}
