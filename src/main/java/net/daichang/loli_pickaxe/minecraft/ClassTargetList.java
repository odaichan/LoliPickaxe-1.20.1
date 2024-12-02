package net.daichang.loli_pickaxe.minecraft;

import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassTargetList {
    private static final List<String> targetList = new ArrayList<>();

    public static boolean isTargetList(Entity entity){
        return targetList.contains(entity.getClass().getName());
    }

    public static void addTarget(Entity entity){
        targetList.add(entity.getClass().getName());
    }

    public static void removeTarget(Collection<? extends String> p_13412_){
        targetList.removeAll(p_13412_);
    }
}
