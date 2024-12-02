package net.daichang.loli_pickaxe.common.register;

import net.daichang.loli_pickaxe.common.items.addons.ItemTest;
import net.daichang.loli_pickaxe.common.items.tools.ItemLoliPickaxe;
import net.daichang.loli_pickaxe.common.items.tools.ItemTimeClock;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegister {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, "loli_pickaxe");

    public static final RegistryObject<Item> LoliPickaxe ;
    public static final RegistryObject<Item> TimeStop;
    public static final RegistryObject<Item> LoliSpawnEgg;
    public static final RegistryObject<Item> Test;
    static {
        //Tools
        LoliPickaxe = items.register("loli_pickaxe", ItemLoliPickaxe::new);
        TimeStop = items.register("time_clock", ItemTimeClock::new);
        Test = items.register("test", ItemTest::new);

        //SpawnEgg
        LoliSpawnEgg = items.register("loli_spawn_egg",() -> new ForgeSpawnEggItem(EntityRegistry.LOLI, -1, -1, new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant()));
    }
}
