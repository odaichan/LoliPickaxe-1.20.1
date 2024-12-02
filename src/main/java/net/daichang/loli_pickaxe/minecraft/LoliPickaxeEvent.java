package net.daichang.loli_pickaxe.minecraft;

import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.minecraft.Commands.*;
import net.daichang.loli_pickaxe.util.LoliAttackUtil;
import net.daichang.loli_pickaxe.util.LoliDefenseUtil;
import net.daichang.loli_pickaxe.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class LoliPickaxeEvent {
    @SubscribeEvent
    public static void CommandRegister(RegisterCommandsEvent event){
        event.getDispatcher().register(Commands.literal("loliPickaxe")
                .requires(s -> s.hasPermission(4))
                .then(BlueScreenCommand.register())
                .then(KilledPlayerCommand.register())
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
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT){
            event.accept(ItemRegister.LoliPickaxe.get());
        }
    }

    @SubscribeEvent
    public static void deathEvent(LivingDeathEvent e){
        Item loli_pickaxe = ItemRegister.LoliPickaxe.get();
        ItemStack loli_pickaxe_stack = new ItemStack(loli_pickaxe);
        LivingEntity living = e.getEntity();
        DamageSource source = e.getSource();
        Entity attacked = source.getEntity();
        if(living.getMainHandItem().getItem() == loli_pickaxe){
            LoliAttackUtil.killEntity(living, attacked);
            e.setCanceled(true);
        }
        if(living instanceof Player player && player.getInventory().contains(loli_pickaxe_stack)){
            LoliAttackUtil.killEntity(player, attacked);
            e.setCanceled(true);
        }
    }
}