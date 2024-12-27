package net.daichang.loli_pickaxe.util;

import net.daichang.loli_pickaxe.api.BlueScreenAPI;
import net.daichang.loli_pickaxe.minecraft.ClassTargetList;
import net.daichang.loli_pickaxe.minecraft.DeathList;
import net.daichang.loli_pickaxe.util.core.EntityCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

import static net.daichang.loli_pickaxe.util.Util.*;

public class LoliAttackUtil {

    public static void killEntity(LivingEntity isLoli, Entity targetEntity){
        targetEntity.gameEvent(GameEvent.ENTITY_DIE);
        targetEntity.level().broadcastDamageEvent(targetEntity, (new DamageSource(targetEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD), isLoli)));
        for (int attack = 0; attack <100; attack++) {
            if (sMode && !(targetEntity instanceof Player)) {
                DeathList.addList(targetEntity);
            }
            if (!(targetEntity instanceof Player) && classTarget) {
                ClassTargetList.addTarget(targetEntity);
            }
            if (!(targetEntity instanceof Player) && remove) {
                removeEntity(targetEntity);
            }
            if (!(targetEntity instanceof Player)){
                Util.setCategory(targetEntity, EntityCategory.notLoliEntity);
            }
            targetEntity.hurt(new DamageSource(targetEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD), isLoli), Float.POSITIVE_INFINITY);
            targetEntity.hurt(new DamageSource(targetEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK), isLoli), Float.POSITIVE_INFINITY);
            targetEntity.hurt(new DamageSource(targetEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("loli_pickaxe:loli_pickaxe"))), isLoli), Float.POSITIVE_INFINITY);
            if (targetEntity instanceof LivingEntity targetLiving && !Util.isLoliEntity(targetLiving)) {
                targetLiving.deathTime = Util.loliDeathTime();
                targetLiving.setHealth(0.0F);
                targetLiving.isDeadOrDying();
                targetLiving.hurtTime =0;
                targetLiving.hurtDuration = 0;
                targetLiving.invulnerableTime = 0;
                targetLiving.invulnerableDuration = 0;
                targetLiving.heal(Float.NEGATIVE_INFINITY);
                Util.Override_DATA_HEALTH_ID(targetLiving, 0.0F);
                if (targetEntity instanceof WitherBoss boss) {
                    boss.setNoAi(true);
                }
                if (targetLiving instanceof EnderDragon dragon) {
                    dragon.dragonDeathTime = 190;
                    dragon.ambientSoundTime = -2;
                    dragon.flapTime = -2;
                }
                if (targetLiving instanceof Player player && player.level().isClientSide() && blueScreen) {
                    BlueScreenAPI.API.BlueScreen(true);
                    System.out.println("You Windows was killed by" + isLoli.getDisplayName());
                    Minecraft mc = Minecraft.getInstance();
                    Util.screen(mc);
                }
            }
        }
    }

    public static void KillEntitle(Level world, double x, double y, double z, Player player){
        int entityCount  = 0;
        Vec3 _center = new Vec3(x, y, z);
        List<Entity> targetClass = world.getEntitiesOfClass(Entity.class, (new AABB(_center, _center)).inflate(2100.0D), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
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

    public static void removeEntity(Entity entity){
        Entity.RemovalReason removalReason = Entity.RemovalReason.KILLED;
        entity.setRemoved(removalReason);
        entity.remove(removalReason);
        entity.onClientRemoval();
        entity.onRemovedFromWorld();
        Level level = entity.level();
        if (level instanceof ServerLevel serverLevel){
            try {
                serverLevel.getEntities().get(entity.getId()).remove(removalReason);
                serverLevel.getEntities().get(entity.getUUID()).remove(removalReason);
            } catch (Exception ignored) {
            }
        }else {
            ClientLevel clientLevel = (ClientLevel) level;
            clientLevel.removeEntity(entity.getId(), removalReason);
            clientLevel.getEntity(entity.getId()).remove(removalReason);
            clientLevel.entityStorage.getEntityGetter().get(entity.getUUID()).remove(removalReason);
        }
    }
}
