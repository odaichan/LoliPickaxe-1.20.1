package net.daichang.loli_pickaxe.common.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.daichang.loli_pickaxe.common.client.screens.modes.SuperModeScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

import static net.daichang.loli_pickaxe.util.Util.blueScreen;

public class LoliModeScreen extends Screen {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    private int leftPos;
    protected int imageWidth = 176;
    protected int imageHeight = 166;
    private int topPos;
    Button button_empty;
    Button button_empty1;
    Button button_isture;

    public LoliModeScreen(Component text) {
        super(text);
    }
    private static final ResourceLocation texture = new ResourceLocation("loli_pickaxe:textures/screen/mode_gui.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_282465_) {
        super.render(guiGraphics, mouseX, mouseY, p_282465_);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics) {
        super.renderBackground(guiGraphics);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    protected void init() {
        super.init();
        leftPos = (this.width - this.imageWidth) / 2;
        topPos = (this.height - this.imageHeight) / 2;
        button_empty = Button.builder(Component.literal("<"), e -> {
        }).bounds(this.leftPos + 11, this.topPos + 43, 30, 20).build(builder -> new Button(builder) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
                super.renderWidget(guiGraphics, gx, gy, ticks);
            }
        });
        guistate.put("button:button_empty", button_empty);
        this.addRenderableWidget(button_empty);
        button_empty1 = Button.builder(Component.literal(">"), e -> {
            minecraft.setScreen(new SuperModeScreen(Component.translatable("")));
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
