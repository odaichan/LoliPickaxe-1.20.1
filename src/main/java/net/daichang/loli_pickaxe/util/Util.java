package net.daichang.loli_pickaxe.util;

import net.daichang.loli_pickaxe.LoliPickaxeMod;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.util.core.EntityCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Util {
    public static boolean isBlocking(@NotNull Player target) {
        return target.getUseItem().getItem() == ItemRegister.Test.get().getDefaultInstance().getItem() && target.isUsingItem() && target.getUseItem().getItem().getUseAnimation(target.getUseItem()) == Util.getUseAnim();
    }

    public static UseAnim getUseAnim() {
        return UseAnim.valueOf(LoliPickaxeMod.MOD_ID + ":BLOCK");
    }

    public static void copyProperties(Class<?> clazz, Object source, Object target) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers()))
                    field.set(target, field.get(source));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void Override_DATA_HEALTH_ID(LivingEntity livingEntity, final float X) {
        SynchedEntityData data = new SynchedEntityData((Entity)livingEntity) {
            @NotNull
            public <T> T get(@NotNull EntityDataAccessor<T> p_135371_) {
                return (p_135371_ == LivingEntity.DATA_HEALTH_ID) ? (T)Float.valueOf(X) : (T)super.get(p_135371_);
            }
        };
        copyProperties(SynchedEntityData.class, livingEntity.entityData, data);
        livingEntity.entityData = data;
    }

    public static void Override_DATA_HEALTH_ID(Player player, final float X) {
        SynchedEntityData data = new SynchedEntityData((Entity)player) {
            @NotNull
            public <T> T m_135370_(@NotNull EntityDataAccessor<T> p_135371_) {
                return (p_135371_ == LivingEntity.DATA_HEALTH_ID) ? (T)Float.valueOf(X) : (T)super.get(p_135371_);
            }
        };
        copyProperties(SynchedEntityData.class, player.entityData, data);
        player.entityData = data;
    }

    public static void Override_DATA_HEALTH_ID(Entity entity, final float X) {
        SynchedEntityData data = new SynchedEntityData(entity) {
            @NotNull
            public <T> T m_135370_(@NotNull EntityDataAccessor<T> p_135371_) {
                return (p_135371_ == LivingEntity.DATA_HEALTH_ID) ? (T)Float.valueOf(X) : (T)super.get(p_135371_);
            }
        };
        copyProperties(SynchedEntityData.class, entity.entityData, data);
        entity.entityData = data;
    }

    public static boolean isLoliEntity(LivingEntity l){
        return l.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get() && !(l instanceof Player) && !(l instanceof EnderDragon);
    }

    public static void playAttackSound(Level level, Entity playSound){
        if(level.isClientSide() && playSound.isAlive()){
            double x = playSound.getX();
            double y =  playSound.getY();
            double z = playSound.getZ();
            if(level.isClientSide()) level.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("loli_pickaxe:loli_succrss"))), SoundSource.PLAYERS, 1.0F, 1.0F, false);
        }
    }

    public static void screen(Minecraft mc){
        DeathScreen screen =  new DeathScreen(Component.translatable("death.attack.loli_pickaxe"), false);
        mc.setScreen(screen);
    }

    public static int loliDeathTime(){
        final int[] deathTime = {0};
        (new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                if (deathTime[0] <=200){
                    ++deathTime[0];
                }
            }
        }, 30L, 30L);
        return deathTime[0];
    }

    public static void loliPickaxeKickPlayer(ServerPlayer serverPlayer, Component component){
        Connection connection = serverPlayer.connection.connection;
        connection.send(new ClientboundDisconnectPacket(component), PacketSendListener.thenRun(() -> connection.disconnect(component)));
        connection.setReadOnly();
        MinecraftServer var10000 = serverPlayer.server;
        Objects.requireNonNull(connection);
        var10000.executeBlocking(connection::handleDisconnection);
    }

    public static EntityCategory getCategory(Entity entity){
        try {
            return HelperLib.getFieldValue(entity.getUUID(), "entityCategory", EntityCategory.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setCategory(Entity entity, EntityCategory info){
        try {
            HelperLib.setFieldValue(entity.getUUID(), "entityCategory", info);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isHasLoliPickaxe(Player player){
        return player.getInventory().contains(ItemRegister.LoliPickaxe.get().getDefaultInstance());
    }
}
