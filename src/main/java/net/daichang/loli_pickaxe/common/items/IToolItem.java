package net.daichang.loli_pickaxe.common.items;

import net.minecraft.world.item.Item;

public class IToolItem extends Item {
    public IToolItem(Properties properties) {
        super(properties.stacksTo(1));
    }
}
