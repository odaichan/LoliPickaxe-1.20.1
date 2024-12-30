package net.daichang.loli_pickaxe.minecraft.Commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.daichang.loli_pickaxe.minecraft.Commands.entityTools.EntityListCommand;
import net.daichang.loli_pickaxe.minecraft.Commands.entityTools.KilledPlayerCommand;
import net.daichang.loli_pickaxe.minecraft.Commands.entityTools.LoliDefenseCommand;
import net.daichang.loli_pickaxe.minecraft.Commands.entityTools.LoliKillEntityCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import static net.daichang.loli_pickaxe.util.Util.canRemoval;

public class LoliEntityCommand {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
        return Commands.literal("entity")
                .then(LoliKillEntityCommand.register())
                .then(EntityListCommand.register())
                .then(LoliDefenseCommand.register())
                .then(LoliCanRemovedCommand.register())
                .then(KilledPlayerCommand.register())
                ;
    }

    static class LoliCanRemovedCommand{
        public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
            return Commands.literal("loliCanRemoval").executes(cs -> {
                canRemoval = !canRemoval;
                return 0;
            });
        }
    }
}
