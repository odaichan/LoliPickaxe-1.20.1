package net.daichang.loli_pickaxe.common.items.addons;

import net.daichang.loli_pickaxe.common.items.IToolItem;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemSuperItem extends IToolItem {
    public ItemSuperItem() {
        super(new Properties());
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof LivingEntity living){
            int loliDeathTime = Util.loliDeathTime();
            living.deathTime= loliDeathTime;
            if (living instanceof EnderDragon dragon){
                dragon.dragonDeathTime = loliDeathTime;
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack p_41395_, LivingEntity living, LivingEntity player) {
        int loliDeathTime = Util.loliDeathTime();
        living.deathTime= loliDeathTime;
        if (living instanceof EnderDragon dragon){
            dragon.dragonDeathTime = loliDeathTime;
        }
        return super.hurtEnemy(p_41395_, living, player);
    }
}