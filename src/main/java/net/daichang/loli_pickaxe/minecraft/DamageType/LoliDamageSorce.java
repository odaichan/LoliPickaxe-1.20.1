package net.daichang.loli_pickaxe.minecraft.DamageType;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LoliDamageSorce extends DamageSource {
    public LoliDamageSorce(Holder<DamageType> p_270811_, @Nullable Entity p_270660_) {
        super(p_270811_, p_270660_);
    }

    @Override
    public @NotNull Component getLocalizedDeathMessage(@NotNull LivingEntity attack) {
        LivingEntity livingentity = attack.getKillCredit();
        if (livingentity != null) return Component.literal(livingentity.getDisplayName().getString() + " 被 " + attack.getDisplayName().getString() + "  使用氪金萝莉抹杀了");
        return super.getLocalizedDeathMessage(attack);
    }
}
