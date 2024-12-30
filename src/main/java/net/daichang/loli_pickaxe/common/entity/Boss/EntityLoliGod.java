package net.daichang.loli_pickaxe.common.entity.Boss;

import com.google.common.collect.Iterables;
import net.daichang.loli_pickaxe.common.register.AttributesRegister;
import net.daichang.loli_pickaxe.common.register.EntityRegistry;
import net.daichang.loli_pickaxe.util.LoliAttackUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.UUID;

public class EntityLoliGod extends Monster {
    public EntityLoliGod(PlayMessages.SpawnEntity packet, Level world) {
        super(EntityRegistry.LOLI_God.get(), world);
    }

    public EntityLoliGod(EntityType<? extends Monster> loli, Level world) {
        super(loli, world);
    }

    @Override
    public void setPos(double x, double y, double z) {
        super.setPos(0, y, 0);
    }

    @Override
    public void remove(@NotNull RemovalReason p_276115_) {

    }

    @Override
    public @Nullable RemovalReason getRemovalReason() {
        return null;
    }

    @Override
    public void onClientRemoval() {
    }

    @Override
    public void onRemovedFromWorld() {
    }

    @Override
    public void kill() {
    }

    @Override
    public void tick() {
        super.tick();
        setPos(0, -54, 0);
        Level level = this.level();
        if (level instanceof ServerLevel serverLevel){
            Iterables.unmodifiableIterable(serverLevel.getAllEntities()).forEach(target -> {
                if (!(target instanceof Player) && !(target instanceof EntityLoliGod)){
                    LoliAttackUtil.removeEntity(target);
                }else {
                    serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal("世界迎来了终点..."), false);
                }
            });
        }
        isAddedToWorld = true;
        MinecraftForge.EVENT_BUS.shutdown();
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.7D)
                .add(Attributes.ATTACK_DAMAGE, Double.POSITIVE_INFINITY)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.KNOCKBACK_RESISTANCE, Integer.MAX_VALUE)
                .add(AttributesRegister.LoliDamageType.get(), Integer.MAX_VALUE)
                ;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("entity.loli_pickaxe.loli_god");
    }

    @Override
    public boolean isCustomNameVisible() {
        return true;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public boolean isDeadOrDying() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource souce, float damage) {
        return false;
    }

    @Override
    public @Nullable Component getCustomName() {
        return Component.translatable("entity.loli_pickaxe.loli_god");
    }

    @Override
    public UUID getUUID() {
        return UUID.randomUUID();
    }

    @Override
    public int getId() {
        return new Random().nextInt();
    }
}
