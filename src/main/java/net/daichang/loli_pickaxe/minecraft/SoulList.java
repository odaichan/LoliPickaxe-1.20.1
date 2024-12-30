package net.daichang.loli_pickaxe.minecraft;

import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SoulList {
    private static final List<UUID> soulList = new ArrayList<>();

    public static boolean isSoulList(Player player){
        return soulList.contains(player.getUUID());
    }

    public static void addTarget(Player entity){
        soulList.add(entity.getUUID());
    }

    public static void removePlayer(Player player){
        soulList.remove(player.getUUID());
    }

    public static String getList(){
        return soulList.iterator().toString();
    }

    public static void clearSoulList(){
        for (UUID o : soulList){
            soulList.remove(o);
        }
    }
}
