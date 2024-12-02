package net.daichang.loli_pickaxe.common.items.addons;

import net.daichang.loli_pickaxe.common.entity.EntityLoli;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemTest extends Item {
    public ItemTest() {
        super(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("list.loli_pickaxe.add"));
        list.add(Component.translatable("list.loli_pickaxe.removeLoli"));
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof LivingEntity living && !(living instanceof EntityLoli) && !(living instanceof Player)){
            living.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegister.LoliPickaxe.get()));
        }else if(entity instanceof EntityLoli loli){
            loli.setPos(Double.NaN, Double.NaN, Double.NaN);
            loli.onClientRemoval();
            loli.onRemovedFromWorld();
            loli.remove(Entity.RemovalReason.KILLED);
            loli.setRemoved(Entity.RemovalReason.KILLED);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}
