package net.daichang.loli_pickaxe.common.register;


import net.daichang.loli_pickaxe.LoliPickaxeMod;
import net.daichang.loli_pickaxe.common.client.keyPress.LoliOpenMode;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class LoliKeyHandler {
    public static final KeyMapping N = new KeyMapping("key.loli_pickaxe.loli_pickaxe_mode", GLFW.GLFW_KEY_N, "key.categories.loli_pickaxe") {
        private boolean isDownOld = false;
        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown) {
                LoliPickaxeMod.PACKET_HANDLER.sendToServer(new LoliOpenMode(0, 0));
                LoliOpenMode.pressAction(Minecraft.getInstance().player, 0, 0);
            }
            isDownOld = isDown;
        }
    };

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(N);
    }

    @Mod.EventBusSubscriber({Dist.CLIENT})
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (Minecraft.getInstance().screen == null) {
                N.consumeClick();
            }
        }
    }
}
