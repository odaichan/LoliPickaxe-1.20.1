package net.daichang.loli_pickaxe.minecraft.Commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class KilledPlayerCommand {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
        return Commands.literal("killedPlayer").requires((cs) -> cs.hasPermission(0))
                .executes(commandContext -> {
                    ServerPlayer player = commandContext.getSource().getPlayer();
                    if (player != null) killPlayer(player);
                    return 0;
                });
    }

    private static void killPlayer(ServerPlayer player) {
        player.setHealth(0.0F);
        Util.Override_DATA_HEALTH_ID(player, 0.0F);
    }
}
