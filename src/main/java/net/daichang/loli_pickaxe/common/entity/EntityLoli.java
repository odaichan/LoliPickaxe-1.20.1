package net.daichang.loli_pickaxe.common.entity;

import com.google.common.collect.ImmutableList;
import net.daichang.loli_pickaxe.common.register.AttributesRegister;
import net.daichang.loli_pickaxe.common.register.EntityRegistry;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.minecraft.DeathList;
import net.daichang.loli_pickaxe.util.LoliAttackUtil;
import net.daichang.loli_pickaxe.util.Util;
import net.daichang.loli_pickaxe.util.core.EntityCategory;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class EntityLoli extends Monster{
    private static final float loliHealth = 20.0F;
    private static final int loliDeathTime = 0;
    private static final EntityDataAccessor<Integer> DATA_TARGET_A;
    private static final EntityDataAccessor<Integer> DATA_TARGET_B;
    private static final EntityDataAccessor<Integer> DATA_TARGET_C;
    private static final EntityDataAccessor<Integer> DATA_ID_INV;
    private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR;
    private static final List<EntityDataAccessor<Integer>> DATA_TARGETS;
    private static final TargetingConditions TARGETING_CONDITIONS;
    public EntityLoli(PlayMessages.SpawnEntity packet, Level world) {
        this(EntityRegistry.LOLI.get(), world);
        this.aiStep();
        this.registerGoals();
        this.hurtMarked = true;
        this.hurtTime = -2;
        this.xpReward = Integer.MAX_VALUE;
        this.isBaby();
        this.invulnerableTime = -2;
        this.heal(loliHealth);
        this.deathTime = loliDeathTime;
    }

    public EntityLoli(EntityType<? extends Monster> loli, Level world) {
        super(loli, world);
    }


    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public float getHealth() {
        return loliHealth;
    }

    @Override
    public void setHealth(float p_21154_) {
        super.setHealth(loliHealth);
    }

    @Override
    public void tick() {
        super.tick();
        Util.setCategory(this, EntityCategory.isLoliEntity);
        Level level = this.level();
        double a = new Random().nextDouble(0.1, 0.5);
        for (Entity entity : level.getEntities(this, this.getBoundingBox().inflate(1.0D))) {
            if (entity!= this && entity instanceof LivingEntity livingEntity && !Util.isLoliEntity(livingEntity) && !(entity instanceof EntityLoli) && !(entity instanceof Player)) {
                Util.Override_DATA_HEALTH_ID(livingEntity, 0.0F);
                DeathList.addList(livingEntity);
                Util.setCategory(livingEntity, EntityCategory.notLoliEntity);
                this.setPos(livingEntity.getX() + a, livingEntity.getY() + a, livingEntity.getZ() + a);
            }else if(entity instanceof ServerPlayer player && !player.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get())) && player.isAlive()) {
                LoliAttackUtil.killEntity(this, player);
                this.setPos(player.getX() + a, player.getY() + a, player.getZ() + a);
            }
        }
        for (Entity entity : level.getEntities(this, this.getBoundingBox().inflate(200D))){
            if(entity instanceof LivingEntity living && !Util.isLoliEntity(living) && !(entity instanceof Player)){
                this.setTarget(living);
            } else if (entity instanceof Player player && !player.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))) {
                this.setTarget(player);
            }
        }
        this.isAlive();
        this.deathTime = -2;
        this.fallDistance = 0;
        this.canUpdate(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, loliHealth)
                .add(Attributes.MOVEMENT_SPEED, 0.7D)
                .add(Attributes.ATTACK_DAMAGE, Double.POSITIVE_INFINITY)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.KNOCKBACK_RESISTANCE, Integer.MAX_VALUE)
                .add(AttributesRegister.LoliDamageType.get(), Integer.MAX_VALUE)
                ;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(2
                , new NearestAttackableTargetGoal<>(this
                        , LivingEntity.class
                        , 0
                        , false
                        , false
                        , LIVING_ENTITY_SELECTOR));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new FloatGoal(this));
    }

    public static void init() {

    }

    @Override
    public boolean isBaby() {
        return true;
    }

    @Override
    public boolean hurt(@NotNull DamageSource p_21016_, float p_21017_) {
        return false;
    }

    public int getAlternativeTarget(int p_31513_) {
        return (Integer)this.entityData.get((EntityDataAccessor)DATA_TARGETS.get(p_31513_));
    }

    public void setAlternativeTarget(int p_31455_, int p_31456_) {
        this.entityData.set((EntityDataAccessor)DATA_TARGETS.get(p_31455_), p_31456_);
    }

    @Override
    public void kill() {
        this.isAlive();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.6, 1.0);
        if (!this.level().isClientSide && this.getAlternativeTarget(0) > 0) {
            Entity entity = this.level().getEntity(this.getAlternativeTarget(0));
            if (entity != null) {
                double d0 = vec3.y;
                if (this.getY() < entity.getY()  && this.getY() < entity.getY() + 5.0) {
                    d0 = Math.max(0.0, d0);
                    d0 += 0.3 - d0 * 0.6000000238418579;
                }

                vec3 = new Vec3(vec3.x, d0, vec3.z);
                Vec3 vec31 = new Vec3(entity.getX() - this.getX(), 0.0, entity.getZ() - this.getZ());
                if (vec31.horizontalDistanceSqr() > 9.0) {
                    Vec3 vec32 = vec31.normalize();
                    vec3 = vec3.add(vec32.x * 0.3 - vec3.x * 0.6, 0.0, vec32.z * 0.3 - vec3.z * 0.6);
                }
            }
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        List<LivingEntity> list = this.level().getNearbyEntities(LivingEntity.class, TARGETING_CONDITIONS, this, this.getBoundingBox().inflate(20.0, 8.0, 20.0));
        if (!list.isEmpty()) {
            LivingEntity livingentity1 = list.get(this.random.nextInt(list.size()));
            this.setAlternativeTarget(0, livingentity1.getId());
            this.setTarget(livingentity1);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TARGET_A, 0);
        this.entityData.define(DATA_TARGET_B, 0);
        this.entityData.define(DATA_TARGET_C, 0);
        this.entityData.define(DATA_ID_INV, 0);
    }

    static {
        DATA_TARGET_A = SynchedEntityData.defineId(EntityLoli.class, EntityDataSerializers.INT);
        DATA_TARGET_B = SynchedEntityData.defineId(EntityLoli.class, EntityDataSerializers.INT);
        DATA_TARGET_C = SynchedEntityData.defineId(EntityLoli.class, EntityDataSerializers.INT);
        DATA_ID_INV = SynchedEntityData.defineId(EntityLoli.class, EntityDataSerializers.INT);
        DATA_TARGETS = ImmutableList.of(DATA_TARGET_A, DATA_TARGET_B, DATA_TARGET_C);
        LIVING_ENTITY_SELECTOR = (p_31504_) -> p_31504_.getMobType() != MobType.UNDEAD && p_31504_.attackable();
        TARGETING_CONDITIONS = TargetingConditions.forCombat().range(20.0).selector(LIVING_ENTITY_SELECTOR);
    }

    @Override
    public @NotNull Brain<?> getBrain() {
        return brain;
    }

    @Override
    protected void tickDeath() {
        deathTime = loliDeathTime;
        setHealth(loliHealth);
        heal(loliHealth);
        super.tickDeath();
    }
}
