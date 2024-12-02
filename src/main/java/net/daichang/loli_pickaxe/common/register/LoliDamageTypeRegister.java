package net.daichang.loli_pickaxe.common.register;

import net.daichang.loli_pickaxe.minecraft.DamageType.LoliDamageSorce;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

public class LoliDamageTypeRegister {
    public static final ResourceKey<DamageType> damage = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("loli_attack", "loli_pickaxe"));

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(damage, new DamageType("loli_attack", DamageScaling.ALWAYS, 0.1F));
    }

    public static DamageSource causeRandomDamage(Entity attacker) {
        return new LoliDamageSorce((Holder<DamageType>)attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damage), attacker);
    }
}
