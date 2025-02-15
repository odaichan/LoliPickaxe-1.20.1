package net.daichang.loli_pickaxe.mixins.command;

import net.daichang.loli_pickaxe.minecraft.DeathList;
import net.daichang.loli_pickaxe.util.LoliAttackUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.KillCommand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

import static net.daichang.loli_pickaxe.Config.Config.sMode;

public class CommandMixins {
    @Mixin(KillCommand.class)
    public static class KillMixin{
        @Inject(method = "kill", at=  @At("RETURN"))
        private static void kill(CommandSourceStack sourceStack, Collection<? extends Entity> entities, CallbackInfoReturnable<Integer> cir){
            for (Entity entity : entities){
                if (sMode) {
                    if (!(entity instanceof Player)) {
                        DeathList.addList(entity);
                    } else if (entity instanceof Player player) {
                        LoliAttackUtil.killEntity(player, player);
                    }
                }
            }
        }
    }
}
