package net.daichang.loli_pickaxe.minecraft.Commands.other;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.daichang.loli_pickaxe.util.handler.TimeDataHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class TimeStopCommand {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
        return Commands.literal("time_stop")
                .executes(cs -> timeStop(cs.getSource(), cs.getSource().getPlayer().level(), cs.getSource().getPlayer()));
    }

    private static int timeStop(CommandSourceStack stack , Level level, ServerPlayer p){
        TimeDataHandler handler = TimeDataHandler.get();
        if (handler.stopTime > 1) {
            level.playLocalSound(p.getX(), p.getY(), p.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("loli_pickaxe:time_resume"))), SoundSource.PLAYERS, 1.0F, 1.0F, false);
            handler.setTimeStopped(false);
            handler.stopTime = -1;
        } else {
            level.playLocalSound(p.getX(), p.getY(), p.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("loli_pickaxe:time_stop"))), SoundSource.PLAYERS, 1.0F, 1.0F, false);
            handler.setTimeStopped(true);
            handler.stopTime = Integer.MAX_VALUE;
        }
        return 0;
    }
}
