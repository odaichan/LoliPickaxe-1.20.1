package net.daichang.loli_pickaxe.common.client.keyPress;


import net.daichang.loli_pickaxe.LoliPickaxeMod;
import net.daichang.loli_pickaxe.common.client.screens.LoliModeScreen;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LoliOpenMode {
    int type, pressedms;

    public LoliOpenMode(int type, int pressedms) {
        this.type = type;
        this.pressedms = pressedms;
    }

    public LoliOpenMode(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
        this.pressedms = buffer.readInt();
    }

    public static void buffer(LoliOpenMode message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
        buffer.writeInt(message.pressedms);
    }

    public static void handler(LoliOpenMode message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            pressAction(context.getSender(), message.type, message.pressedms);
        });
        context.setPacketHandled(true);
    }

    public static void pressAction(Player entity, int type, int pressedms) {
        Level world = entity.level();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        if (!world.hasChunkAt(entity.blockPosition()))
            return;
        if (type == 0) {
            if (entity.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get()) {
                Minecraft mc = Minecraft.getInstance();
                mc.setScreen(new LoliModeScreen(Component.translatable("list.loli_pickaxe.blue_screen")));
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        LoliPickaxeMod.addNetworkMessage(LoliOpenMode.class, LoliOpenMode::buffer, LoliOpenMode::new, LoliOpenMode::handler);
    }
}
