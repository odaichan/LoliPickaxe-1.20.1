package net.daichang.loli_pickaxe.minecraft.event;

import net.daichang.loli_pickaxe.common.client.entityRender.LoliRender;
import net.daichang.loli_pickaxe.common.register.EntityRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "loli_pickaxe", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvent {
    @SubscribeEvent
    public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.LOLI.get(), LoliRender :: new);
    }
}
