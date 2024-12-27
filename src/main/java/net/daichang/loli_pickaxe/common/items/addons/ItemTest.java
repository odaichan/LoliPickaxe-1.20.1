package net.daichang.loli_pickaxe.common.items.addons;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.daichang.loli_pickaxe.common.entity.EntityLoli;
import net.daichang.loli_pickaxe.common.register.AttributesRegister;
import net.daichang.loli_pickaxe.minecraft.DeathList;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ItemTest extends Item {
    public ItemTest() {
        super(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("list.loli_pickaxe.removeLoli"));
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof EntityLoli loli){
            loli.onClientRemoval();
            loli.onRemovedFromWorld();
            loli.remove(Entity.RemovalReason.KILLED);
            loli.setRemoved(Entity.RemovalReason.KILLED);
        }else {
            if (!(entity instanceof Player)){
                DeathList.addList(entity);
            }else {
                Util.Override_DATA_HEALTH_ID(entity, 0.0F);
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getAttributeModifiers(equipmentSlot, stack));
            builder.put(AttributesRegister.LoliDamageType.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 23.7D, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(equipmentSlot, stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return Util.getUseAnim();
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return Integer.MAX_VALUE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player player, InteractionHand p_41434_) {
        player.startUsingItem(p_41434_);
        return super.use(p_41432_, player, p_41434_);
    }
}
