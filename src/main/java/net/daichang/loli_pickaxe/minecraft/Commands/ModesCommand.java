package net.daichang.loli_pickaxe.minecraft.Commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.daichang.loli_pickaxe.minecraft.Commands.Modes.CanBlueScreen;
import net.daichang.loli_pickaxe.minecraft.Commands.Modes.ClassTargetCommand;
import net.daichang.loli_pickaxe.minecraft.Commands.Modes.KickPlayerCommands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import static net.daichang.loli_pickaxe.util.Util.remove;

public class ModesCommand {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
        return Commands.literal("loliPickaxeModes")
                .then(SuperModeCommand.register())
                .then(CanBlueScreen.register())
                .then(ClassTargetCommand.register())
                .then(RevmoeEntityCommand.register())
                .then(KickPlayerCommands.register())
                ;
    }

    static class RevmoeEntityCommand{
        static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
            return Commands.literal("removeEntity").executes(cs -> {
                remove = !remove;
              return 0;
            });
        }
    }
}
