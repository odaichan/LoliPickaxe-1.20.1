package net.daichang.loli_pickaxe.util.HavenUtil;

import net.daichang.loli_pickaxe.util.handler.TimeDataHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class HavenUtil {
    private static  boolean made = false;

    public static long MAX_DAY_TIME = 24000;

    public static boolean isHaven(){
        return made;
    }

    public static void setMadeInHaven(boolean haven){
        made = haven;
    }

    public static void setDayTime(Level level){
        if(level instanceof ServerLevel serverLevel && !TimeDataHandler.get().isTimeStopped()){
            if(serverLevel.getDayTime() < MAX_DAY_TIME){
                serverLevel.setDayTime(serverLevel.dayTime() + 50);
            }else {
                serverLevel.setDayTime(0);
                serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal("New Day has started!"), false);
            }
        }
    }
}
