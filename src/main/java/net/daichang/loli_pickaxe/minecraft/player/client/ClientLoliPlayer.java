package net.daichang.loli_pickaxe.minecraft.player.client;

import net.daichang.loli_pickaxe.Config.Config;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.util.LoliDefenseUtil;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;

public class ClientLoliPlayer extends LocalPlayer {
    public ClientLoliPlayer(Minecraft p_108621_, ClientLevel p_108622_, ClientPacketListener p_108623_, StatsCounter p_108624_, ClientRecipeBook p_108625_, boolean p_108626_, boolean p_108627_) {
        super(p_108621_, p_108622_, p_108623_, p_108624_, p_108625_, p_108626_, p_108627_);
    }

    @Override
    public float getHealth() {
        if (this.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))){
            return this.getMaxHealth();
        }else {
            return super.getHealth();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))){
            LoliDefenseUtil.loliDefense(this);
        }
    }

    @Override
    public boolean isAlive() {
        if (this.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))){
            return true;
        }else {
            return super.isAlive();
        }
    }

    @Override
    public boolean isDeadOrDying() {
        if (this.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))){
            return false;
        }else {
            return super.isDeadOrDying();
        }
    }

    @Override
    public boolean hurt(DamageSource p_108662_, float p_108663_) {
        if (this.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))){
            return false;
        }else {
            return super.hurt(p_108662_, p_108663_);
        }
    }

    @Override
    public double getBlockReach() {
        if (this.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get() && Util.breakRange){
            return Config.breakBlockRange;
        }else {
            return super.getBlockReach();
        }
    }
}
