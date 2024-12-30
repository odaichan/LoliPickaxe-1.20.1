package net.daichang.loli_pickaxe.mixins.command;

import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.minecraft.DeathList;
import net.daichang.loli_pickaxe.util.LoliAttackUtil;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.KickCommand;
import net.minecraft.server.commands.KillCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

public class CommandMixins {
    @Mixin(KillCommand.class)
    public static class KillMixin{
        @Inject(method = "kill", at=  @At("RETURN"))
        private static void kill(CommandSourceStack sourceStack, Collection<? extends Entity> entities, CallbackInfoReturnable<Integer> cir){
            for (Entity entity : entities){
                if (Util.sMode) {
                    if (!(entity instanceof Player)) {
                        DeathList.addList(entity);
                    } else if (entity instanceof Player player) {
                        LoliAttackUtil.killEntity(player, player);
                    }
                }
            }
        }
    }

    @Mixin(KickCommand.class)
    public static class KickCommandMixin{
        @Inject(method = "kickPlayers", at = @At("RETURN"))
        private static void kickPlayers(CommandSourceStack sourceStack, Collection<ServerPlayer> serverPlayers, Component component, CallbackInfoReturnable<Integer> cir){
            for (ServerPlayer serverPlayer : serverPlayers){
                if (sourceStack.isPlayer() && sourceStack.getPlayer() != null && sourceStack.getPlayer().getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))){
                    serverPlayer.connection.disconnect(Component.translatable("command.loli_pickaxe.kick_player"));
                }
                if (serverPlayer.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))){
                    cir.cancel();
                }
            }
        }
    }
}
