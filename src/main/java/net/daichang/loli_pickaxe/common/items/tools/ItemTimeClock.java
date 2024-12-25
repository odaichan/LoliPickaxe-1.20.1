package net.daichang.loli_pickaxe.common.items.tools;

import net.daichang.loli_pickaxe.common.items.IToolItem;
import net.daichang.loli_pickaxe.util.handler.TimeDataHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ItemTimeClock extends IToolItem {
    public ItemTimeClock() {
        super(new Properties());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player p, InteractionHand p_41434_) {
        if(level.isClientSide()) {
            TimeDataHandler handler = TimeDataHandler.get();
            if (handler.stopTime > 1) {
                level.playLocalSound(p.getX(), p.getY(), p.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("loli_pickaxe:time_resume"))), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                handler.setTimeStopped(false);
                handler.stopTime = -1;
                p.displayClientMessage(Component.literal(ChatFormatting.GOLD + "『Time Begins To Flow！』"), false);

            } else {
                level.playLocalSound(p.getX(), p.getY(), p.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("loli_pickaxe:time_stop"))), SoundSource.PLAYERS, 1.0F, 1.0F, false);
                handler.setTimeStopped(true);
                handler.stopTime = Integer.MAX_VALUE;
                p.displayClientMessage(Component.literal(ChatFormatting.GOLD + "『The World！』"), false);
            }
        }
        return super.use(level, p, p_41434_);
    }
}
