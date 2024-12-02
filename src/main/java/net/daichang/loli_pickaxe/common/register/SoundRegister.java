package net.daichang.loli_pickaxe.common.register;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegister {
    public static final DeferredRegister<SoundEvent> sounds = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "loli_pickaxe");

    public static final RegistryObject<SoundEvent> LOLIRECORD = sounds.register("lolirecord", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("loli_pickaxe", "lolirecord")));

    public static final RegistryObject<SoundEvent> LOLI_SUCCRSS = sounds.register("loli_succrss", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("loli_pickaxe", "loli_succrss")));

    public static final RegistryObject<SoundEvent> TIME_STOP = sounds.register("time_stop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("loli_pickaxe", "time_stop")));

    public static final RegistryObject<SoundEvent> TIME_RESUME = sounds.register("time_resume", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("loli_pickaxe", "time_resume")));
}
