package net.daichang.loli_pickaxe;

import com.mojang.logging.LogUtils;
import net.daichang.loli_pickaxe.common.client.entityModel.ModelLoliEntity;
import net.daichang.loli_pickaxe.common.register.*;
import net.daichang.loli_pickaxe.minecraft.DeathList;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.daichang.loli_pickaxe.Config.Config.*;
import static net.daichang.loli_pickaxe.LoliPickaxeMod.MOD_ID;

@Mod(MOD_ID)
public class LoliPickaxeMod {
    public static final String MOD_ID = "loli_pickaxe";
    private static int messageID = 0;
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, MOD_ID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    public static final Logger LOGGER = LogUtils.getLogger();

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

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModelLoliEntity.LAYER_LOCATION, ModelLoliEntity::createBodyLayer);
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

    @SubscribeEvent
    public void leftClickBlock(PlayerInteractEvent.LeftClickBlock e){
        Player player = e.getEntity();
        Level level = e.getLevel();
        BlockPos pos = e.getPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (player.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get() && forcedExcavation){
            ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), (new ItemStack(level.getBlockState(pos).getBlock())));
            level.addFreshEntity(item);
            item.setPickUpDelay(0);
            level.destroyBlock(pos, false, player);
//            for (int itemCount = 1; itemCount <=64; itemCount++ ) {
//            Block.dropResources(level.getBlockState(pos), level, BlockPos.containing(pos.getX(), pos.getY(), pos.getZ()), null);
//            }
            if (block instanceof LiquidBlock){
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
            }
        }
    }

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }
}