package net.daichang.loli_pickaxe.minecraft;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class DeathList{
    private static final List<String> myList = new ArrayList<>();

    public static boolean isList(Entity entity){
        return myList.contains(entity.getUUID().toString()) && !(entity instanceof Player);
    }

    public static void addList(Entity entity){
        myList.add(entity.getUUID().toString());
    }

    public static void removeList(Entity entity){
        myList.remove(entity.getUUID().toString());
    }

    public static String getList(){
        return myList.get(myList.size());
    }
}
