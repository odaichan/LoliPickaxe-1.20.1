package net.daichang.loli_pickaxe.common.items.items;

import net.daichang.loli_pickaxe.util.FieldTrace;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemDeath extends Item {
    public ItemDeath() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player player, InteractionHand p_41434_) {
        FieldTrace.traceAllFields(Player.class);
        return super.use(p_41432_, player, p_41434_);
    }
}
