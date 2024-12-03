package net.daichang.loli_pickaxe.util.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class ItemUtil {
    public static Tag itemTags(CompoundTag tag){
        return tag.get("");
    }
}
