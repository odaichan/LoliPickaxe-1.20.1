package net.daichang.loli_pickaxe.mixins.items;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ItemStack.class)
public class MixinItemStack {
    @Inject(method = "getTooltipImage", at = @At("RETURN"))
    private void getTooltipImage(CallbackInfoReturnable<Optional<TooltipComponent>> cir){

    }
}
