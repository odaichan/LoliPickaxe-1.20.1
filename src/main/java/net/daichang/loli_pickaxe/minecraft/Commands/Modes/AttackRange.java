package net.daichang.loli_pickaxe.minecraft.Commands.Modes;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.daichang.loli_pickaxe.Config.Config;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import static net.daichang.loli_pickaxe.Config.Config.*;

public class AttackRange {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> breakRangeregister(){
        return Commands.literal("attackRangeSet").then(Commands.argument("range", DoubleArgumentType.doubleArg(0, 256)).executes(cs ->
                {
                    Config.entityAttackRange = DoubleArgumentType.getDouble(cs, "range");
                    return 0;
                }));
    }

    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> isTrueOrFalseregister(){
        return Commands.literal("attackRange").requires((cs) -> cs.hasPermission(0))
                .executes(commandContext -> {
                    entityReachQ = !entityReachQ;
                    return 0;
                });
    }
}
