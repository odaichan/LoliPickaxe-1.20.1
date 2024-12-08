package net.daichang.loli_pickaxe.common.register;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributesRegister {
    public static final DeferredRegister<Attribute> attribute = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, "loli_pickaxe");

    public static final RegistryObject<Attribute> LoliDamageType;

    @SubscribeEvent
    public static void addAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(entity -> event.add(entity, LoliDamageType.get()));
    }

    @Mod.EventBusSubscriber
    public static class PlayerAttributesSync {
        @SubscribeEvent
        public static void playerClone(PlayerEvent.Clone event) {
            Player oldPlayer = event.getOriginal();
            Player newPlayer = event.getEntity();
            newPlayer.getAttribute(LoliDamageType.get()).setBaseValue(oldPlayer.getAttribute(LoliDamageType.get()).getBaseValue());
        }
    }

    static {
        LoliDamageType = attribute.register("loli_damage_type",  () -> new RangedAttribute("attribute.loli_pickaxe.damage", 0, 0, Double.POSITIVE_INFINITY).setSyncable(true));
    }
}
