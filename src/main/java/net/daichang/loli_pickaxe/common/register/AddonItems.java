package net.daichang.loli_pickaxe.common.register;

import net.daichang.loli_pickaxe.common.items.addons.Core.ItemAddon;
import net.daichang.loli_pickaxe.util.item.LoliAddon;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class AddonItems {
    public static final DeferredRegister<Item> deferred = DeferredRegister.create(ForgeRegistries.ITEMS, "loli_pickaxe");
    public static Map<String, RegistryObject<Item>> addonItems = createItem();

    public static Map<String, RegistryObject<Item>> createItem(){
        Map<String, RegistryObject<Item>> loliAddons = new LinkedHashMap<>();
        for (String itemName : LoliAddon.getLoliAddons()){
            loliAddons.put(itemName, registryItem(itemName + "_addon"));
        }
        return loliAddons;
    }
    private static RegistryObject<Item> registryItem(String name){
        Item.Properties properties = new Item.Properties();
        return createItems(name, properties);
    }

    private static RegistryObject<Item> createItems(String name, Item.Properties properties){
        return deferred.register(name, () -> new ItemAddon(properties.stacksTo(64).rarity(Rarity.EPIC)));
    }
}
