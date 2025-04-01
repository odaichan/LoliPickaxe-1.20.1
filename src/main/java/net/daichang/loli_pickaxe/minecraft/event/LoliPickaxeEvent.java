package net.daichang.loli_pickaxe.minecraft.event;

import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.minecraft.Commands.LoliEntityCommand;
import net.daichang.loli_pickaxe.minecraft.Commands.ModesCommand;
import net.daichang.loli_pickaxe.minecraft.Commands.OtherCommand;
import net.daichang.loli_pickaxe.util.HavenUtil.HavenUtil;
import net.daichang.loli_pickaxe.util.LoliDefenseUtil;
import net.daichang.loli_pickaxe.util.TextUtil;
import net.daichang.loli_pickaxe.util.handler.TimeDataHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class LoliPickaxeEvent {

    @SubscribeEvent
    public static void CommandRegister(RegisterCommandsEvent event){
        event.getDispatcher().register(Commands.literal("loliPickaxe")
                .then(ModesCommand.register())
                .then(LoliEntityCommand.register())
                .then(OtherCommand.register())
        );
    }

    @SubscribeEvent
    public static void renderTooltipEvent(ItemTooltipEvent tooltipEvent){
        if (tooltipEvent.getItemStack().getItem() == ItemRegister.LoliPickaxe.get()) {
            List<Component> tooltip = tooltipEvent.getToolTip();
            int size = tooltip.size();
            MutableComponent mutableComponent1 = Component.translatable("attribute.name.generic.attack_damage");
            MutableComponent mutableComponent2 = Component.translatable("attribute.name.generic.attack_speed");
            String var10000 = TextUtil.GetColor("TREE(3) ");
            MutableComponent mutableComponent3 = Component.literal(" " + var10000 + ChatFormatting.DARK_GREEN + " 攻击伤害");
            MutableComponent mutableComponent4 = Component.literal(" " + var10000 + ChatFormatting.DARK_GREEN + " 攻击速度");
            for (int i = 0; i < size; i++) {
                Component line = tooltip.get(i);
                if (line.contains(mutableComponent1))
                    tooltip.set(i, mutableComponent3);
                if (line.contains(mutableComponent2))
                    tooltip.set(i, mutableComponent4);
            }
        }
    }

    @SubscribeEvent
    public static void renderPlayerEvent(RenderPlayerEvent event){
        Player player = event.getEntity();
        if (player.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))) LoliDefenseUtil.loliDefense(player);
    }

    @SubscribeEvent
    public static void sendMessageOfPlayer(PlayerEvent.PlayerLoggedInEvent e){
        Player player = e.getEntity();
        player.displayClientMessage(Component.translatable("chat.loli_pickaxe.chat_1"), false);
        player.displayClientMessage(Component.translatable("chat.loli_pickaxe.chat_2"), false);
        player.displayClientMessage(Component.translatable("chat.loli_pickaxe.chat_3"), false);
        Level level = player.level();
        if (level instanceof ServerLevel serverLevel) {

        }
    }

    @SubscribeEvent
    public static void levelTick(TickEvent.PlayerTickEvent event){
        Level level = event.player.level();
        if(HavenUtil.isHaven() && level instanceof ServerLevel server && !TimeDataHandler.get().isTimeStopped()){
            HavenUtil.setDayTime(server);
        }
    }

    private static long targetTime = 12000L;
    // 当前时间
    private static long currentTime = 6000L;
    // 每tick时间变化的幅度
    private static final long TIME_CHANGE_STEP = 10L;

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.level instanceof ServerLevel level) {
            // 检查是否需要改变时间
            if (currentTime != targetTime) {
                // 根据目标时间和当前时间的差值来决定时间变化的方向
                long newTime = currentTime + (targetTime > currentTime ? TIME_CHANGE_STEP : -TIME_CHANGE_STEP);
                // 确保时间不会超出合理范围
                newTime = Math.min(Math.max(newTime, 0L), 24000L);
                // 设置新的游戏时间
                level.setDayTime(newTime);
                // 更新当前时间
                currentTime = newTime;
            }
        }
    }

    // 设置目标时间为白天
    public static void setTargetTimeToDay() {
        targetTime = 6000L;
    }

    // 设置目标时间为夜晚
    public static void setTargetTimeToNight() {
        targetTime = 18000L;
    }
}