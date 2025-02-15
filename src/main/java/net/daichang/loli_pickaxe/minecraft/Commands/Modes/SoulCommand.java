package net.daichang.loli_pickaxe.minecraft.Commands.Modes;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.daichang.loli_pickaxe.Config.Config;
import net.daichang.loli_pickaxe.minecraft.SoulList;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SoulCommand {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> addList(){
        return Commands.literal("addList").then(Commands.argument("player", EntityArgument.players()).executes(cs ->
        {
            Player target = EntityArgument.getPlayer(cs, "player");
            SoulList.addTarget(target);
            return 0;
        }));
    }

    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> removeOne(){
        return Commands.literal("removeList").then(Commands.argument("player", EntityArgument.players()).executes(cs ->
        {
            Player target = EntityArgument.getPlayer(cs, "player");
            SoulList.removePlayer(target);
            return 0;
        }));
    }

    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> removeAll(){
        return Commands.literal("removeAll").executes(cs->{
            SoulList.clearSoulList();
            return 0;
        });
    }

    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> openOrClose(){
        return Commands.literal("set").then(Commands.argument("boolean", BoolArgumentType.bool()).executes(cs-> {
            Config.soulAssumption = BoolArgumentType.getBool(cs, "boolean");
            return 0;
        }));
    }

    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> getList(){
        return Commands.literal("getList").executes(cs-> {
            Level level = cs.getSource().getUnsidedLevel();
            level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("灵魂超度UUID：" + SoulList.getList()), false);
            return 0;
        });
    }
}
