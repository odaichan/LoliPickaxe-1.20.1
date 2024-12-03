package net.daichang.loli_pickaxe.api;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface DaiChangScreenAPI extends Library {
    DaiChangScreenAPI INSTANCE = Native.load("daichang-screen-jna.dll", DaiChangScreenAPI.class);

    // 方法用于在Minecraft中持续打开指定的Screen
    void continuouslyOpenSpecifiedScreen(String screenClassName);
}
