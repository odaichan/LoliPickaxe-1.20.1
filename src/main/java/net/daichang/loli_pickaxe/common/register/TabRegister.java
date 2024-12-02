package net.daichang.loli_pickaxe.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TabRegister {
    public static final DeferredRegister<CreativeModeTab> tab = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "loli_pickaxe");

    public static final RegistryObject<CreativeModeTab> LOLI_Pickaxe_Tab = tab.register("snow_sword_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("mods.loli_pickaxe,tab"))
                    .icon(() -> new ItemStack(Items.WOODEN_SWORD))
                    .displayItems((parameters, tabData) -> {
                        //Tools
                        tabData.accept(ItemRegister.LoliPickaxe.get());
                        tabData.accept(ItemRegister.TimeStop.get());
                        tabData.accept(ItemRegister.Test.get());

                        //Spawn Eggs
                        tabData.accept(ItemRegister.LoliSpawnEgg.get());

                        //Blocks
                        tabData.accept(Blocks.COMMAND_BLOCK);

                        //Items
                    }).build());
}
