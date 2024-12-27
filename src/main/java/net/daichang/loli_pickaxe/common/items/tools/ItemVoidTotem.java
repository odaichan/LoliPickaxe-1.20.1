package net.daichang.loli_pickaxe.common.items.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemVoidTotem extends Item {
    private final Minecraft mc = Minecraft.getInstance();
    public ItemVoidTotem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().durability(4));
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        if (player.getY() < -70 && !player.getCooldowns().isOnCooldown(this)) {
            Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
            player.setDeltaMovement(new Vec3(0, 6, 0));
            player.getCooldowns().addCooldown(this, 100);
            level.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1, 1, false);
            stack.setDamageValue(stack.getDamageValue()-1);
        }
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        Player player = mc.player;
        if (player != null && player.isShiftKeyDown()){
            list.add(Component.translatable("list.loli_pickaxe.void_totem_1"));
            list.add(Component.translatable("list.loli_pickaxe.void_totem_2"));
        }else {
            list.add(Component.translatable("list.loli_piakaxe.void_totem_3"));
        }
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }
}
