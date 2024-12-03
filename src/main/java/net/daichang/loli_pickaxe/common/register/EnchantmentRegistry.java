package net.daichang.loli_pickaxe.common.register;

import net.daichang.loli_pickaxe.common.ench.TheSunFixEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentRegistry {
    public static final DeferredRegister<Enchantment> ench = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "loli_pickaxe");

    public static final RegistryObject<Enchantment> SunFix ;

    static {
        SunFix =  ench.register("sun_fix", () -> new TheSunFixEnchantment());
    }
}
