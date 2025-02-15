package net.daichang.loli_pickaxe.minecraft.Commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import static net.daichang.loli_pickaxe.Config.Config.sMode;

public class SuperModeCommand {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
        return Commands.literal("superMode").requires((cs) -> cs.hasPermission(0))
                .executes(commandContext -> {
                    sMode = !sMode;
                    Entity entity = commandContext.getSource().getEntity();
                    if(entity instanceof Player player && player.level().isClientSide() && player.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))){
                        player.displayClientMessage(Component.literal("灵魂打击：" + sMode), false);
                    }else {
                        System.out.println("EEEOR: You don't have LoliPickaxe, can't use this command");
                    }
                    return 0;
                });
    }
}
