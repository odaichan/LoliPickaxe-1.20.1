package net.daichang.loli_pickaxe.common.items.addons.Core;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemAddon extends Item {
    public ItemAddon(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        if (nbt != null) {
            nbt.putInt("level", 0);
            nbt.putInt("level", 1);
            nbt.putInt("level", 2);
            nbt.putInt("level", 3);
            nbt.putInt("level", 4);
            nbt.putInt("level", 5);
            nbt.putInt("level", 6);
            nbt.putInt("level", 7);
            nbt.putInt("level", 8);
            nbt.putInt("level", 9);
        }
        super.readShareTag(stack, nbt);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
