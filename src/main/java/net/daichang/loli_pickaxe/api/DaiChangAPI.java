package net.daichang.loli_pickaxe.api;

import com.sun.jna.Library;
import com.sun.jna.Native;
import net.minecraft.resources.ResourceLocation;

public interface DaiChangAPI extends Library {
    // 加载本地库，这里假设库名为libmc_window_renderer.so（Linux）或mc_window_renderer.dll（Windows）等，具体根据实际编译生成的库名
    DaiChangAPI INSTANCE = Native.load("daichang-minecraft-jna", DaiChangAPI.class);

    // 方法用于设置要替换的图片的资源位置
    void setReplacementImagePath(ResourceLocation resourceLocation);

    // 方法用于触发替换窗口渲染为指定图片的操作
    void replaceWindowRendering();

    // 方法用于恢复原始的窗口渲染（可选，如果需要提供恢复功能的话）
    void restoreOriginalRendering();

    // 方法用于替换死亡界面
    void replaceDeathScreen();
}
