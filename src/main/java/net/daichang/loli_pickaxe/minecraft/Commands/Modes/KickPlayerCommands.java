package net.daichang.loli_pickaxe.minecraft.Commands.Modes;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import static net.daichang.loli_pickaxe.Config.Config.*;

public class KickPlayerCommands {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
        return Commands.literal("kickPlayer").requires((cs) -> cs.hasPermission(0))
                .executes(commandContext -> {
                    kickPlayer = !kickPlayer;
                    return 0;
                });
    }
}
