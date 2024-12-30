package net.daichang.loli_pickaxe.minecraft.Commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.daichang.loli_pickaxe.minecraft.Commands.other.BlueScreenCommand;
import net.daichang.loli_pickaxe.minecraft.Commands.other.TimeStopCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class OtherCommand {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
        return Commands.literal("other")
                .then(TimeStopCommand.register())
                .then(BlueScreenCommand.register())
                ;
    }
}
