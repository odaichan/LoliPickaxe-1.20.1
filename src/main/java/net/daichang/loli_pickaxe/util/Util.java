package net.daichang.loli_pickaxe.util;

import net.daichang.loli_pickaxe.common.entity.EntityLoli;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Util {
    public static boolean sMode = false;

    public static boolean blueScreen = false;

    public static boolean classTarget = false;

    public static boolean remove = false;

    public static boolean canRemoval = false;

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
            public <T> T m_135370_(@NotNull EntityDataAccessor<T> p_135371_) {
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

    public static boolean notLoli(Level level, double x, double y, double z, Entity loli){
        Vec3 _center = new Vec3(x, y, z);
        List<Entity> targetClass = level.getEntitiesOfClass(Entity.class, (new AABB(_center, _center)).inflate(2100.0D), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
        for (Entity target : targetClass){
            if (target instanceof LivingEntity){
                if (target instanceof Player player && !(player.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get())))){
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean isLoliEntity(LivingEntity l){
        return l.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get() && !(l instanceof EntityLoli);
    }

    public static void playAttackSound(Level level, Entity playSound){
        if(level.isClientSide() && playSound.isAlive()){
            double x = playSound.getX();
            double y =  playSound.getY();
            double z = playSound.getZ();
            if(level.isClientSide()) level.playLocalSound(x, y, z, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("loli_pickaxe:loli_succrss"))), SoundSource.PLAYERS, 1.0F, 1.0F, false);
        }
    }

    public boolean canRemoval() {
        return canRemoval;
    }
}
