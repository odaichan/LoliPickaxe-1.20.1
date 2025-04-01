package net.daichang.loli_pickaxe.common.entity;

import net.daichang.loli_pickaxe.common.register.AttributesRegister;
import net.daichang.loli_pickaxe.common.register.EntityRegistry;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.util.LoliAttackUtil;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class EntityLoli extends Monster{
    private static final float loliHealth = 20.0F;
    private static int loliDeathTime = -2;
    private static final String DEATH_TIME_HANDLER = "LoliDeathTime''";
    public static final EntityDataAccessor<Boolean> LOLI_REMOVE = SynchedEntityData.defineId(EntityLoli.class, EntityDataSerializers.BOOLEAN);
    private static final RemovalReason reason = RemovalReason.KILLED;

    public EntityLoli(PlayMessages.SpawnEntity packet, Level world) {
        this(EntityRegistry.LOLI.get(), world);
        this.registerGoals();
        this.hurtMarked = true;
        this.hurtTime = -2;
        this.xpReward = Integer.MAX_VALUE;
        this.invulnerableTime = -2;
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
        Level level = this.level();
        double a = new Random().nextDouble(0.1, 0.5);
        for (Entity entity : level.getEntities(this, this.getBoundingBox().inflate(1.0D))) {
            if (entity!= this && entity instanceof LivingEntity livingEntity && !Util.isLoliEntity(livingEntity) && !(entity instanceof EntityLoli) && !(entity instanceof Player) && livingEntity.isAlive()) {
                Util.Override_DATA_HEALTH_ID(livingEntity, 0.0F);
                if (livingEntity.getHealth() == 0){
                    livingEntity.isDeadOrDying();
                    livingEntity.heal(-100.0F);
                }
                this.setPos(entity.getX() + a, entity.getY() + a, entity.getZ() + a);
            }else if(entity instanceof Player player && !player.getInventory().contains(ItemRegister.LoliPickaxe.get().getDefaultInstance()) && player.isAlive()) {
                LoliAttackUtil.killEntity(this, player);
                this.setPos(entity.getX() + a, entity.getY() + a, entity.getZ() + a);
            }
        }
        this.deathTime = -2;
        Util.Override_DATA_HEALTH_ID(this, loliHealth);
        this.setHealth(loliHealth);
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

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Mob.class, false, false));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));

    }


    public static void init() {

    }

    @Override
    public boolean hurt(@NotNull DamageSource p_21016_, float p_21017_) {
        return false;
    }

    @Override
    public void kill() {
        this.isAlive();
    }

    @Override
    protected void tickDeath() {
        setHealth(loliHealth);
        heal(loliHealth);
        Util.Override_DATA_HEALTH_ID(this, loliHealth);
        if (loliDeathTime ==1){
            ItemEntity item = new ItemEntity(this.level() , this.getX(), this.getY(), this.getZ(), new ItemStack(ItemRegister.LoliPickaxe.get()));
            this.level().addFreshEntity(item);
            item.setPickUpDelay(10);
        }
        if (loliDeathTime > 30000){
            this.setPos(Double.NaN, Double.NaN, Double.NaN);
        }
    }

    @Override
    public @Nullable LivingEntity getTarget() {
        Level level = this.level();
        Vec3 vec3 = new Vec3(getX(), getY(), getZ());
        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, (new AABB(vec3, vec3)).inflate(30))){
            return entity;
        }
        return super.getTarget();
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public boolean canUpdate() {
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
    public boolean isFreezing() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt(DEATH_TIME_HANDLER, loliDeathTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(DEATH_TIME_HANDLER)){
            loliDeathTime = tag.getInt(DEATH_TIME_HANDLER);
        }
    }

    @Override
    public void remove(@NotNull RemovalReason p_276115_) {
        super.remove(reason);
    }

    @Override
    public void onRemovedFromWorld() {
        this.isAddedToWorld = true;
    }

    @Override
    public void setPos(double x, double y, double z) {
        super.setPos(x, y, z);
        Level level = this.level();
        Vec3 vec3 = new Vec3(x, y, z);
        for (Entity entity : level.getEntitiesOfClass(Entity.class, new AABB(vec3, vec3))){
            LoliAttackUtil.killEntity(this, entity);
        }
    }

    @Override
    protected @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        if (player.getMainHandItem().getItem() == ItemRegister.Test.get()){
            this.entityData.set(LOLI_REMOVE, Boolean.valueOf(true));
            this.remove(reason);
            this.setRemoved(reason);
            player.swing(InteractionHand.MAIN_HAND, true);
            player.swingingArm = InteractionHand.MAIN_HAND;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LOLI_REMOVE, Boolean.valueOf(false));
    }

    @Override
    public @Nullable RemovalReason getRemovalReason() {
        if (this.entityData.get(LOLI_REMOVE))
            return reason;
        return null;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource p_21385_, int p_21386_, boolean p_21387_) {}
}
