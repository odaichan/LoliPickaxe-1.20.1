package net.daichang.loli_pickaxe.mixins;

import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class MixinItemEntity{
    @Unique
    private final ItemEntity loli_pickaxe$targetItem = (ItemEntity) (Object) this;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci){
        if(loli_pickaxe$targetItem.getItem().getItem() == ItemRegister.LoliPickaxe.get()){
            Level level = loli_pickaxe$targetItem.level();
            level.addParticle(ParticleTypes.HEART, (loli_pickaxe$targetItem.getX()), (loli_pickaxe$targetItem.getY()), (loli_pickaxe$targetItem.getZ()), 0, 1, 0);
        }
    }
}
