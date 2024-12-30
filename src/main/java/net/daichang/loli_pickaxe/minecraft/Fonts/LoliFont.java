package net.daichang.loli_pickaxe.minecraft.Fonts;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.function.Function;

public class LoliFont extends Font {
    public LoliFont(Function<ResourceLocation, FontSet> p_243253_, boolean p_243245_) {
        super(p_243253_, p_243245_);
    }

    public static LoliFont getFont() {
        return new LoliFont(Minecraft.getInstance().font.fonts, false);
    }

    long getSystemTime() {
        return System.currentTimeMillis() * 1000L / System.nanoTime();
    }

    public int drawInBatch(@NotNull FormattedCharSequence formattedCharSequence, float x, float y, int rgb, boolean b1, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource multiBufferSource, @NotNull DisplayMode mode, int i, int i1) {
        StringBuilder stringBuilder = new StringBuilder();
        formattedCharSequence.accept((index, style, codePoint) -> {
            stringBuilder.appendCodePoint(codePoint);
            return true;
        });
        String text = ChatFormatting.stripFormatting(stringBuilder.toString());
        if (text != null) {
            float hueOffset = (float)Util.getMillis() / 800.0F;
            for (int index = 0; index < text.length(); index++) {
                String s = String.valueOf(text.charAt(index));
                float hue = (hueOffset + index / text.length()) % 1.0F;
                int c = rgb & 0xFF000000 | Mth.hsvToRgb(hue, 0.8F, 1.0F);
                super.drawInBatch(s, x, y, c, b1, matrix4f, multiBufferSource, mode, i, i1);
                x += width(s);
            }
        }
        return (int) x;
    }

    public int drawInBatch(@NotNull String text, float x, float y, int rgb, boolean b, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource source, @NotNull DisplayMode mode, int i, int i1) {
        return this.drawInBatch(Component.literal(text).getVisualOrderText(), x, y, rgb, b, matrix4f, source, mode, i, i1);
    }

    public int drawInBatch(@NotNull Component component, float x, float y, int rgb, boolean b, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource source, @NotNull DisplayMode mode, int i, int i1) {
        return this.drawInBatch(component.getVisualOrderText(), x, y, rgb, b, matrix4f, source, mode, i, i1);
    }
    public static long milliTime() {
        return System.nanoTime() / 1000000L;
    }

    public static int getColor(float index) {
        return (Color.HSBtoRGB((float) milliTime() / 700.0F % 1.0F, 0.8f, 0.8f));
    }
}
