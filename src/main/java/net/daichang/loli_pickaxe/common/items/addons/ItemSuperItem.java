package net.daichang.loli_pickaxe.common.items.addons;

import net.daichang.loli_pickaxe.api.DaiChangAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemSuperItem extends Item {
    public ItemSuperItem() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        DaiChangAPI dc = DaiChangAPI.INSTANCE;
        dc.setReplacementImagePath(new ResourceLocation("loli_pickaxe", "textures/screen/screen.png"));
        dc.replaceWindowRendering();
        return super.use(p_41432_, p_41433_, p_41434_);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return super.onEntitySwing(stack, entity);
    }
}