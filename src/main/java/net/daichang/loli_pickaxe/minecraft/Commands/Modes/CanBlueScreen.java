package net.daichang.loli_pickaxe.minecraft.Commands.Modes;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import static net.daichang.loli_pickaxe.Config.Config.*;

public class CanBlueScreen {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
        return Commands.literal("blueScreen").requires((cs) -> cs.hasPermission(0))
                .executes(commandContext -> {
                    blueScreen = !blueScreen;
                    Entity entity = commandContext.getSource().getEntity();
                    if(entity instanceof Player player && player.level().isClientSide()){
                        player.displayClientMessage(Component.literal("蓝屏打击：" + blueScreen), false);
                    }
                    return 0;
                });
    }
}
