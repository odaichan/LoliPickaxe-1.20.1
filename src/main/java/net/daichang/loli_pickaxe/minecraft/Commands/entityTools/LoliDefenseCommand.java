package net.daichang.loli_pickaxe.minecraft.Commands.entityTools;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public class LoliDefenseCommand {
    public static ArgumentBuilder<CommandSourceStack, LiteralArgumentBuilder<CommandSourceStack>> register(){
        return Commands.literal("loliDefense").requires((cs) -> cs.hasPermission(0))
                .then(Commands.argument("targets", EntityArgument.entities()))
                .executes(cs -> isLoli(cs.getSource(), EntityArgument.getEntities(cs, "targets")));
    }

    public static int isLoli(CommandSourceStack p_12342_, Collection<? extends  Entity> p_12409_){
        for (Entity entity : p_12409_){
            if(entity instanceof LivingEntity){
                LivingEntity living = (LivingEntity) entity;
                living.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegister.LoliPickaxe.get()));
            }
        }
        p_12342_.sendFailure(Component.translatable("commands.loli_pickaxe.defense"));
        return p_12409_.size();
    }
}
