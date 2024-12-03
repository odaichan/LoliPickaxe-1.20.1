package net.daichang.loli_pickaxe.common.items.tools;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemSmallLoliPickaxe extends Item {
    public ItemSmallLoliPickaxe() {
        super(new Properties().fireResistant().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41434_) {
        return super.use(level, player, p_41434_);
    }
}
