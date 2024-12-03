package net.daichang.loli_pickaxe.common.register.TabAddon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class LoliPickaxeSidebar {
    private final List<Component> items = new ArrayList<>();
    private final int width;
    private final int height;
    private final int top;
    private final int bottom;
    private final int itemHeight;

    public LoliPickaxeSidebar(int width, int height, int top, int bottom, int itemHeight) {
        this.width = width;
        this.height = height;
        this.top = top;
        this.bottom = bottom;
        this.itemHeight = itemHeight;
    }

    public void addEntry(Component text) {
        items.add(text);
    }

    public void render(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, boolean isSelected) {
        int currentY = y;
        for (Component item : items) {
            guiGraphics.drawString(Minecraft.getInstance().font, item.getString(), x + 5, currentY + 2, isSelected? 0xFFFF00 : 0xFFFFFF);
            currentY += itemHeight;
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int currentY = top;
        for (int i = 0; i < items.size(); i++) {
            if (mouseY >= currentY && mouseY < currentY + itemHeight) {
                // 这里添加点击每个列表项的逻辑，比如切换显示不同分类的物品等，示例只是打印信息
                System.out.println("Clicked on item: " + items.get(i).getString());
                return true;
            }
            currentY += itemHeight;
        }
        return false;
    }
}
