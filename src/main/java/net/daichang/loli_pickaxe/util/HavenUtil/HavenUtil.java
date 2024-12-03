package net.daichang.loli_pickaxe.util.HavenUtil;

public class HavenUtil {
    private static  boolean made = false;

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
}
