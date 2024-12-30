package net.daichang.loli_pickaxe.common.client.screens.modes;

import net.daichang.loli_pickaxe.common.client.screens.LoliModeScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import static net.daichang.loli_pickaxe.util.Util.blueScreen;


public class SuperModeScreen extends LoliModeScreen {
    private int leftPos;
    protected int imageWidth = 176;
    protected int imageHeight = 166;
    private int topPos;
    Button button_empty;
    Button button_empty1;
    Button button_isture;

    public SuperModeScreen(Component text) {
        super(text);
    }

    @Override
    protected void init() {
        super.init();
        leftPos = (this.width - this.imageWidth) / 2;
        topPos = (this.height - this.imageHeight) / 2;
        button_empty = Button.builder(Component.literal("<"), e -> {
            minecraft.setScreen(new LoliModeScreen(Component.translatable("")));
        }).bounds(this.leftPos + 11, this.topPos + 43, 30, 20).build(builder -> new Button(builder) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
                super.renderWidget(guiGraphics, gx, gy, ticks);
            }
        });
        guistate.put("button:button_empty", button_empty);
        this.addRenderableWidget(button_empty);
        button_empty1 = Button.builder(Component.literal(">"), e -> {
            if (true) {

            }
        }).bounds(this.leftPos + 146, this.topPos + 43, 30, 20).build();
        guistate.put("button:button_empty1", button_empty1);
        this.addRenderableWidget(button_empty1);
        button_isture = Button.builder(Component.translatable(blueScreen +  ""), e -> {
            blueScreen = !blueScreen;
        }).bounds(this.leftPos + 65, this.topPos + 61, 56, 20).build();
        guistate.put("button:button_isture", button_isture);
        this.addRenderableWidget(button_isture);
    }
}
