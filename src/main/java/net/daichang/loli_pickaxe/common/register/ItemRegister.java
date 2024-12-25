package net.daichang.loli_pickaxe.common.register;

import net.daichang.loli_pickaxe.common.items.IToolItem;
import net.daichang.loli_pickaxe.common.items.addons.ItemSuperItem;
import net.daichang.loli_pickaxe.common.items.addons.ItemTest;
import net.daichang.loli_pickaxe.common.items.items.ItemDeath;
import net.daichang.loli_pickaxe.common.items.tools.ItemLoliPickaxe;
import net.daichang.loli_pickaxe.common.items.tools.ItemMadeInHeaven;
import net.daichang.loli_pickaxe.common.items.tools.ItemTimeClock;
import net.daichang.loli_pickaxe.common.items.tools.ItemVoidTotem;
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
    public static final RegistryObject<Item> MadeInHeaven;
    public static final RegistryObject<Item> SuperItem;
    public static final RegistryObject<Item> DeathItem;
    public static final RegistryObject<Item> VoidTotemItem;
    public static final RegistryObject<Item> NULLItem;
    static {
        //Tools
        LoliPickaxe = items.register("loli_pickaxe", ItemLoliPickaxe::new);
        TimeStop = items.register("time_clock", ItemTimeClock::new);
        Test = items.register("test", ItemTest::new);
        MadeInHeaven = items.register("made_in_heaven", ItemMadeInHeaven::new);
        VoidTotemItem = items.register("void_totem", ItemVoidTotem::new);

        //SpawnEgg
        LoliSpawnEgg = items.register("loli_spawn_egg",() -> new ForgeSpawnEggItem(EntityRegistry.LOLI, -1, -1, new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant()));

        //addon
        SuperItem = items.register("super_test", ItemSuperItem::new);
        DeathItem = items.register("death", ItemDeath::new);
        NULLItem = items.register("null", ()-> new IToolItem(new Item.Properties()));
    }
}
