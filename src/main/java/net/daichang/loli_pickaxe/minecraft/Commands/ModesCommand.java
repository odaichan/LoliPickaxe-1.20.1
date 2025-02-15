package net.daichang.loli_pickaxe.minecraft.Commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.daichang.loli_pickaxe.minecraft.Commands.Modes.*;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import static net.daichang.loli_pickaxe.Config.Config.*;

public class ModesCommand {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
        return Commands.literal("loliPickaxeModes")
                .then(SuperModeCommand.register())
                .then(CanBlueScreen.register())
                .then(ClassTargetCommand.register())
                .then(RevmoeEntityCommand.register())
                .then(KickPlayerCommands.register())
                .then(DisplayFluidBorderCommand.register())
                .then(ForcedExcavationCommand.register())
                .then(BreakRangeCommand.register())
                .then(ClearInventoryCommand.register())
                .then(DisarmCommand.register())
                .then(SoulAssumptionCommand.register())
                .then(ReverseInjuryCommand.register())
                .then(EntityRangeCommand.register())
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

    static class DisplayFluidBorderCommand{
        static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
            return Commands.literal("displayFluidBorder").executes(cs -> {
                displayFluidBorder = !displayFluidBorder;
                return 0;
            });
        }
    }

    static class ForcedExcavationCommand{
        static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
            return Commands.literal("forcedExcavation").executes(cs -> {
                forcedExcavation = !forcedExcavation;
                return 0;
            });
        }
    }

    static class BreakRangeCommand{
        static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
            return Commands.literal("breakRange")
                    .then(BreakRange.breakRangeregister())
                    .then(BreakRange.isTrueOrFalseregister())
                    ;
        }
    }

    static class ClearInventoryCommand{
        static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
            return Commands.literal("clearInventory").executes(cs -> {
                clearInventory = !clearInventory;
                return 0;
            });
        }
    }

    static class DisarmCommand{
        static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
            return Commands.literal("disarm").executes(cs -> {
                disarm = !disarm;
                return 0;
            });
        }
    }

    static class SoulAssumptionCommand{
        static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
            return Commands.literal("soulAssumption")
                    .then(SoulCommand.addList())
                    .then(SoulCommand.removeOne())
                    .then(SoulCommand.removeAll())
                    .then(SoulCommand.openOrClose())
                    .then(SoulCommand.getList())
                    ;
        }
    }

    static class ReverseInjuryCommand{
        static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
            return Commands.literal("reverseInjury").executes(cs -> {
                reverseInjury = !reverseInjury;
                return 0;
            });
        }
    }

    static class EntityRangeCommand{
        static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
            return Commands.literal("entityRange")
                    .then(AttackRange.breakRangeregister())
                    .then(AttackRange.isTrueOrFalseregister())
                    ;
        }
    }
}
