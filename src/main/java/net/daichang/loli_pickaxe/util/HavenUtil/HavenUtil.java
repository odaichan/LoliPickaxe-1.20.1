package net.daichang.loli_pickaxe.util.HavenUtil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class HavenUtil {
    private static  boolean made = false;

    public static long DAY_TICK = 24000;

    public static boolean isHaven(){
        return made;
    }

    public static void setMadeInHaven(boolean haven){
        made = haven;
    }

    public static long setLong(long l){
        l++;
        return l;
    }

    public static void setDayTime(Level level){
        if(level instanceof ServerLevel serverLevel){
            if(serverLevel.getDayTime() < DAY_TICK){
                serverLevel.setDayTime(serverLevel.dayTime()+50);
            }else {
                serverLevel.setDayTime(0);
            }
        }
    }
}
