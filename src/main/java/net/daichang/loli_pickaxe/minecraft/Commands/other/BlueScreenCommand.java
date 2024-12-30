package net.daichang.loli_pickaxe.minecraft.Commands.other;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.daichang.loli_pickaxe.api.BlueScreenAPI;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class BlueScreenCommand {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
        return Commands.literal("blueScreen").requires((cs) -> cs.hasPermission(0))
                .executes(commandContext -> {
                    BlueScreenAPI.API.BlueScreen(true);
                    return 0;
                });
    }
}
