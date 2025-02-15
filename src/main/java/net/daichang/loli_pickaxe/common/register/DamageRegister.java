package net.daichang.loli_pickaxe.common.register;

import net.daichang.loli_pickaxe.common.DamageTypeLoli;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

public class DamageRegister {
    public static final ResourceKey<DamageType> damage = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("loli_pickaxe", "infinity"));

    public static final RegistrySetBuilder DAMAGE_BUILDER = new RegistrySetBuilder().add(Registries.DAMAGE_TYPE, DamageRegister::bootstrap);

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(damage, new DamageType("infinity", DamageScaling.ALWAYS, 0.1F));
    }

    public static DamageSource causeRandomDamage(Entity attacker) {
        return new DamageTypeLoli(attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damage), attacker);
    }
}
