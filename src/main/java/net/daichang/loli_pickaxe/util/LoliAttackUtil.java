package net.daichang.loli_pickaxe.util;

import net.daichang.loli_pickaxe.Config.Config;
import net.daichang.loli_pickaxe.api.BlueScreenAPI;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.minecraft.ClassTargetList;
import net.daichang.loli_pickaxe.minecraft.DeathList;
import net.daichang.loli_pickaxe.minecraft.SoulList;
import net.daichang.loli_pickaxe.util.core.enums.EntityDeleteReasonManager;
import net.daichang.loli_pickaxe.util.core.enums.IRemove;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

import static net.daichang.loli_pickaxe.Config.Config.*;

public class LoliAttackUtil {
    public static void killEntity(LivingEntity isLoli, Entity targetEntity){
        DamageSource damageSource = new DamageSource(targetEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD), isLoli);
        if (!(targetEntity instanceof Player player)) {
            targetEntity.gameEvent(GameEvent.ENTITY_DIE);
            if (sMode) {
                DeathList.addList(targetEntity);
                targetEntity.kill();
                Util.Override_DATA_HEALTH_ID(targetEntity, 0.0F);
                if (targetEntity instanceof LivingEntity living && living.level() instanceof ServerLevel serverLevel && living.isAlive()){
                    living.setHealth(0.0F);
                    living.dropAllDeathLoot(damageSource);
                    serverLevel.broadcastEntityEvent(living, (byte) 3);
                    isLoli.killedEntity(serverLevel, living);
                    living.getBrain().clearMemories();
                    for (int loli = 0; loli < 20; loli ++){
                        living.deathTime = loli;
                        int finalLoli = loli;
                        new Thread(() -> {
                            try {
                                if (finalLoli == 19){
                                    living.remove(Entity.RemovalReason.KILLED);
                                }
                            }catch (Exception ignored){

                            }
                        });
                    }
                }
            }else {
                targetEntity.level().broadcastDamageEvent(targetEntity, (new DamageSource(targetEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), isLoli)));
                if (classTarget) {
                    ClassTargetList.addTarget(targetEntity);
                }
                if (remove) {
                    removeEntity(targetEntity);
                }
                targetEntity.hurt(damageSource, Float.POSITIVE_INFINITY);
                if (targetEntity instanceof LivingEntity targetLiving && !Util.isLoliEntity(targetLiving)) {
                    targetLiving.setHealth(0.0F);
                    targetLiving.isDeadOrDying();
                    Util.Override_DATA_HEALTH_ID(targetLiving, 0.0F);
                    if (targetLiving instanceof EnderDragon dragon) {
                        dragon.dragonDeathTime = 190;
                        dragon.ambientSoundTime = -2;
                        dragon.flapTime = -2;
                    }
                }
            }
            if (disarm && targetEntity instanceof LivingEntity living){
                Level level = living.level();
                ItemStack  rightHandStack = living.getMainHandItem();
                ItemEntity entity = new ItemEntity(level, living.getX(), living.getY(), living.getZ(), rightHandStack);
                level.addFreshEntity(entity);
                living.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            }
        }else {
            Level level = player.level();
            if (level instanceof ServerLevel serverLevel && !player.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))){
                killPlayer(serverLevel, isLoli, player);
            }
        }
    }

    public static void KillEntitle(Level world, double x, double y, double z, Player player){
        int entityCount  = 0;
        Vec3 _center = new Vec3(x, y, z);
        List<Entity> targetClass = world.getEntitiesOfClass(Entity.class, (new AABB(_center, _center)).inflate(Config.attackRange), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
        for (Entity target : targetClass){
            if (!(target instanceof Player)) {
                entityCount++;
                LoliAttackUtil.killEntity(player, target);
            }
        }
        if (world.isClientSide()){
            player.displayClientMessage(Component.literal("攻击了100x100x100范围内的 " + entityCount + " 个实体"), false);
        }
    }

    public static void killPlayer(ServerLevel level , Entity attack, Player player){
        Util.Override_DATA_HEALTH_ID(player, 0.0F);
        player.setHealth(0.0F);
        player.hurtTime = -2;
        level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(player.getDisplayName().getString() +" 被 " + attack.getDisplayName().getString() + " 使用氪金萝莉抹杀了"), false);
        if (player.level().isClientSide() && blueScreen) {
            BlueScreenAPI.API.BlueScreen(true);
            System.out.println("You Windows was killed by" + attack.getDisplayName());
            Minecraft mc = Minecraft.getInstance();
            Util.screen(mc);
        }
        if (player instanceof ServerPlayer serverPlayer && kickPlayer){
            Util.loliPickaxeKickPlayer(serverPlayer, Component.translatable("command.loli_pickaxe.kick_player"));
        }
        if (clearInventory){
            player.inventory = new Inventory(player);
            player.inventory.dropAll();
            player.inventory.items.clear();
            player.getInventory().items.clear();
        }
        if (disarm){
            for (ItemStack item : player.getInventory().items.list){
                ItemEntity entity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), item);
                entity.setPickUpDelay(10);
                player.level().addFreshEntity(entity);
                player.inventory = new Inventory(player);
            }
        }
        if (soulAssumption){
            SoulList.addTarget(player);
        }
    }

    public static void removeEntity(Entity entity){
        Entity.RemovalReason removalReason = Entity.RemovalReason.UNLOADED_TO_CHUNK;
        IRemove deleteReasonManager = IRemove.LOLI_PLAYER_KILLED;
        entity.setRemoved(removalReason);
        entity.remove(removalReason);
        entity.onClientRemoval();
        entity.onRemovedFromWorld();
        entity.setPos(Double.NaN, Double.NaN, Double.NaN);
        entity.discard();
        entity.setInvisible(true);
        EntityDeleteReasonManager.setDeleteReason(entity, deleteReasonManager);
    }
}
