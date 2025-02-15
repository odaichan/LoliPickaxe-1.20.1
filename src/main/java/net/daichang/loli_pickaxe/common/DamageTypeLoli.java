package net.daichang.loli_pickaxe.common;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DamageTypeLoli extends DamageSource {
    public DamageTypeLoli(Holder<DamageType> damageTypeHolder, @Nullable Entity entity) {
        super(damageTypeHolder, entity);
    }

    @Override
    public @NotNull Component getLocalizedDeathMessage(LivingEntity attacked) {
        int type = attacked.getRandom().nextInt(3);
        LivingEntity livingentity = attacked.getKillCredit();
        String s = "death.attack." + this.getMsgId() + "." + type;
        String s1 = "death.attack." + this.getMsgId() + ".player." + type;
        return livingentity != null ? Component.translatable(s1, attacked.getDisplayName(), livingentity.getDisplayName()) : Component.translatable(s, attacked.getDisplayName());
    }
}
