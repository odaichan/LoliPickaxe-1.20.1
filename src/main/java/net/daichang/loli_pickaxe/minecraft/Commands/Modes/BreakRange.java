package net.daichang.loli_pickaxe.minecraft.Commands.Modes;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.daichang.loli_pickaxe.Config.Config;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static net.daichang.loli_pickaxe.util.Util.breakRange;

public class BreakRange {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> breakRangeregister(){
        return Commands.literal("breakRangeSet").then(Commands.argument("range", DoubleArgumentType.doubleArg(0, 256)).executes(cs ->
                {
                    double range =  DoubleArgumentType.getDouble(cs, "range");
                    Config.breakBlockRange = range;
                    Entity entity = cs.getSource().getEntity();
                    Level level = cs.getSource().getUnsidedLevel();
                    if(entity instanceof Player player && level.isClientSide()){
                        player.displayClientMessage(Component.literal("挖掘范围设置为：" + range), false);
                    }
                    return 0;
                }));
    }

    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> isTrueOrFalseregister(){
        return Commands.literal("breakRangeOpenOrClose").requires((cs) -> cs.hasPermission(0))
                .executes(commandContext -> {
                    breakRange = !breakRange;
                    return 0;
                });
    }
}
