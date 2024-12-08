package net.daichang.loli_pickaxe.common.items.tools;

import net.daichang.loli_pickaxe.util.HavenUtil.HavenUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemMadeInHeaven extends Item {
    public ItemMadeInHeaven() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41434_) {
        if(player.isShiftKeyDown()){
            HavenUtil.setMadeInHaven(false);
        }else {
            HavenUtil.setMadeInHaven(true);
            if(level.isClientSide())player.displayClientMessage(Component.literal(   ChatFormatting.YELLOW +"『Made in heaven, time begins to accelerate!』"), false);
        }
        return super.use(level, player, p_41434_);
    }

    @Override
    public void onUseTick(Level level, LivingEntity p_41429_, ItemStack p_41430_, int p_41431_) {
        super.onUseTick(level, p_41429_, p_41430_, p_41431_);
    }
}
