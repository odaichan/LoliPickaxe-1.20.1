package net.daichang.loli_pickaxe.minecraft.Commands.entityTools;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.daichang.loli_pickaxe.util.LoliAttackUtil;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;

public class LoliKillEntityCommand {
    public static ArgumentBuilder register(){
        return Commands.literal("kill").executes(cs ->{
            Entity entity = cs.getSource().getEntity();
            killed(entity);
            return 0;}).then(Commands.argument("entity", EntityArgument.entities()).executes(cs -> {
                    for (Entity entity : EntityArgument.getEntities(cs, "entity")){
                        killed(entity);
                    }
            return 0;
        }));
    }

    static void killed(Entity entity) {
        if (!(entity instanceof Player)) {
            entity.gameEvent(GameEvent.ENTITY_DIE);
            entity.kill();
            if (entity instanceof LivingEntity living){
                LoliAttackUtil.killEntity(living, living);
                if ( living.deathTime > 20 && living.getHealth() == 0) {
                    LoliAttackUtil.removeEntity(living);
                }
            }
        } else if (entity instanceof Player player && player.level() instanceof ServerLevel serverLevel){
            LoliAttackUtil.killPlayer(serverLevel, player, player);
        }else {
            LoliAttackUtil.removeEntity(entity);
        }
    }
}
