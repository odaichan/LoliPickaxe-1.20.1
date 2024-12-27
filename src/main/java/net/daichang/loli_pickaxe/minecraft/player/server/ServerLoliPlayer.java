package net.daichang.loli_pickaxe.minecraft.player.server;

import com.mojang.authlib.GameProfile;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.util.LoliDefenseUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;

public class ServerLoliPlayer extends ServerPlayer {
    public ServerLoliPlayer(MinecraftServer p_254143_, ServerLevel p_254435_, GameProfile p_253651_) {
        super(p_254143_, p_254435_, p_253651_);
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
}