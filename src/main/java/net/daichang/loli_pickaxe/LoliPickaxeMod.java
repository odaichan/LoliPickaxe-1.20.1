package net.daichang.loli_pickaxe;

import com.mojang.logging.LogUtils;
import net.daichang.loli_pickaxe.common.register.*;
import net.daichang.loli_pickaxe.minecraft.DeathList;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("loli_pickaxe")
public class LoliPickaxeMod {
    public static final String MOD_ID = "loli_pickaxe";

    private static final Logger LOGGER = LogUtils.getLogger();

    public LoliPickaxeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AttributesRegister.attribute.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        ItemRegister.items.register(modEventBus);
        SoundRegister.sounds.register(modEventBus);
        EntityRegistry.ENTITIES.register(modEventBus);
        TabRegister.tab.register(modEventBus);
        EnchantmentRegistry.ench.register(modEventBus);
        AddonItems.deferred.register(modEventBus);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Daichang LoliPickaxe mod loading");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("DaiChang's LoliPickaxe Mod");
        }
    }

    @SubscribeEvent
    public void livingDeathEvent(LivingDeathEvent e){
        LivingEntity living = e.getEntity();
        DamageSource source = e.getSource();
        Entity attack = source.getEntity();
        if (attack instanceof Player player && player.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get())) && !(living instanceof Player)) {
            DeathList.addList(living);
            Util.Override_DATA_HEALTH_ID(living, 0.0F);
        }
    }
}