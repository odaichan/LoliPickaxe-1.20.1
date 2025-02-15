package net.daichang.loli_pickaxe.common.items.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.daichang.loli_pickaxe.Config.Config;
import net.daichang.loli_pickaxe.util.LoliAttackUtil;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.daichang.loli_pickaxe.Config.Config.*;

public class ItemLoliPickaxe extends Item {

    public ItemLoliPickaxe() {
        super(new Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        Util.playAttackSound(level, player);
        if (player.isShiftKeyDown()) {
            LoliAttackUtil.KillEntitle(level, player.getX(), player.getY(), player.getX(), player);
        }else {

        }
        return super.use(level, player, hand);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getAttributeModifiers(equipmentSlot, stack));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 1.0D, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4D, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(equipmentSlot, stack);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack p_41395_, @NotNull LivingEntity target, @NotNull LivingEntity player) {
        LoliAttackUtil.killEntity(player, target);
        Util.playAttackSound(player.level(), player);
        return super.hurtEnemy(p_41395_, target, player);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        LoliAttackUtil.killEntity(player, entity);
        Util.playAttackSound(player.level(), player);
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        CompoundTag tag = p_41421_.getOrCreateTag();
        double breakRanges = Config.breakBlockRange;
        list.add(Component.literal("潜行右键攻击距离为 " + Config.attackRange));
        if (sMode) list.add(Component.translatable("list.loli_pickaxe.super_mod").withStyle(ChatFormatting.GRAY));
        if (classTarget) list.add(Component.translatable("list.loli_pickaxe.class_target").withStyle(ChatFormatting.GRAY));
        if (blueScreen) list.add(Component.translatable("list.loli_pickaxe.blue_screen").withStyle(ChatFormatting.GRAY));
        if (remove) list.add(Component.translatable("list.loli_pickaxe.remove").withStyle(ChatFormatting.GRAY));
        if (kickPlayer) list.add(Component.translatable("list.loli_pickaxe.kick_player").withStyle(ChatFormatting.GRAY));
        if (reverseInjury) list.add(Component.translatable("list.loli_pickaxe.reverse_injury").withStyle(ChatFormatting.GRAY));
        if (displayFluidBorder) list.add(Component.translatable("list.loli_pickaxe.display_fluid_border").withStyle(ChatFormatting.GRAY));
        if (forcedExcavation) list.add(Component.translatable("list.loli_pickaxe.forced_excavation").withStyle(ChatFormatting.GRAY));
        if (clearInventory) list.add(Component.translatable("list.loli_pickaxe.clear_inventory").withStyle(ChatFormatting.GRAY));
        if (breakRange) list.add(Component.literal("挖掘距离："+breakRanges).withStyle(ChatFormatting.GRAY));
        if (disarm) list.add(Component.translatable("list.loli_pickaxe.disarm").withStyle(ChatFormatting.GRAY));
        if (soulAssumption) list.add(Component.translatable("list.loli_pickaxe.soul_assumption").withStyle(ChatFormatting.GRAY));
        if (entityReachQ) list.add(Component.literal("左键攻击距离："+ Config.entityAttackRange).withStyle(ChatFormatting.GRAY));
        list.add(Component.literal(""));
        if (Screen.hasShiftDown()){
            list.add(Component.translatable("list.loli_pickaxe.daichang").withStyle(ChatFormatting.DARK_GRAY));
            list.add(Component.translatable("list.loli_pickaxe.daichang_1").withStyle(ChatFormatting.DARK_GRAY));
            list.add(Component.translatable("list.loli_pickaxe.daichang_2").withStyle(ChatFormatting.DARK_GRAY));
        }else {
            list.add(Component.translatable("list.loli_pickaxe.shift").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }

    @Override
    public float getDestroySpeed(ItemStack p_41425_, BlockState p_41426_) {
        return Float.POSITIVE_INFINITY;
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState p_41450_) {
        return true;
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        player.getInventory().add(item);
        return super.onDroppedByPlayer(ItemStack.EMPTY, player);
    }

    @Override
    public @Nullable CompoundTag getShareTag(ItemStack stack) {
        return super.getShareTag(stack);
    }
}
