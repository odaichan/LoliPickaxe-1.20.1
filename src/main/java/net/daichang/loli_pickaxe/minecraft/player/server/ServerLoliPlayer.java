package net.daichang.loli_pickaxe.minecraft.player.server;

import com.mojang.authlib.GameProfile;
import net.daichang.loli_pickaxe.Config.Config;
import net.daichang.loli_pickaxe.common.register.ItemRegister;
import net.daichang.loli_pickaxe.minecraft.SoulList;
import net.daichang.loli_pickaxe.util.LoliDefenseUtil;
import net.daichang.loli_pickaxe.util.Util;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ServerLoliPlayer extends ServerPlayer {
    public ServerLoliPlayer(MinecraftServer p_254143_, ServerLevel p_254435_, GameProfile p_253651_) {
        super(p_254143_, p_254435_, p_253651_);
    }

    @Override
    public float getHealth() {
        if (this.getInventory().contains(new ItemStack(ItemRegister.LoliPickaxe.get()))) {
            return this.getMaxHealth();
        }if (SoulList.isSoulList(this)){
            return 0.0F;
        } else {
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
    public double getBlockReach() {
        if (this.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get() && Util.breakRange){
            return Config.breakBlockRange;
        }else {
            return super.getBlockReach();
        }
    }

    @Override
    public double getEntityReach() {
        if (this.getMainHandItem().getItem() == ItemRegister.LoliPickaxe.get() && Util.entityReachQ){
            return Config.entityAttackRange;
        }else {
            return super.getEntityReach();
        }
    }
}
