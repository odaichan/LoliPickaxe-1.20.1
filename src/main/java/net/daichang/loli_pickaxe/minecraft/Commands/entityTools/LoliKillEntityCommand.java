package net.daichang.loli_pickaxe.minecraft.Commands.entityTools;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Collection;

public class LoliKillEntityCommand {
    public static ArgumentBuilder register(){
        return Commands.literal("kill").requires((cs) -> cs.hasPermission(0))
                .executes(commandContext -> {
                    killed((CommandSourceStack)commandContext.getSource(),
                            (Collection<? extends Entity>) ImmutableList.of(((CommandSourceStack)commandContext.getSource()).getEntityOrException()));
                    ;return 0;})
                .then(Commands.argument("targets", (ArgumentType) EntityArgument.entities())).executes(p_137810_ -> killed((CommandSourceStack)p_137810_.getSource(), EntityArgument.getEntities(p_137810_, "targets")));
    }

    private static int killed(CommandSourceStack stack, Collection<? extends Entity> p_137815_){
        for (Entity entity : p_137815_){
            if(!(entity instanceof Player)){
                Util.Override_DATA_HEALTH_ID(entity, 0.0F);
                entity.fallDistance =Integer.MAX_VALUE;
                entity.gameEvent(GameEvent.ENTITY_DIE);
            }else {
                entity.gameEvent(GameEvent.ENTITY_DIE);
            }
        }
    return p_137815_.size();}
}
