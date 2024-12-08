package net.daichang.loli_pickaxe.common.ench;

import net.daichang.loli_pickaxe.common.register.EnchantmentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TheSunFixEnchantment extends Enchantment {
    private static final EnchantmentCategory CATEGORY = EnchantmentCategory.create("mymodtest_my_test_enchament", item -> Ingredient.of(ItemTags.create(new ResourceLocation("minecraft:category/tag"))).test(new ItemStack(item)));

    public TheSunFixEnchantment() {
        super(Rarity.VERY_RARE, CATEGORY, EquipmentSlot.values());
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public void doPostAttack(LivingEntity p_44686_, Entity p_44687_, int p_44688_) {
        super.doPostAttack(p_44686_, p_44687_, p_44688_);
    }

    @Override
    public boolean isTreasureOnly() {
        return super.isTreasureOnly();
    }

    @SubscribeEvent
    public static void sunFixEvent(TickEvent.PlayerTickEvent event){
        Player player = event.player;
        Level level = player.level();
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        for(EquipmentSlot slots : EquipmentSlot.values()){
            ItemStack stack = player.getItemBySlot(slots);
            if(level.isDay()
                    && level.canSeeSkyFromBelowWater(BlockPos.containing(x , y ,z))
                    && EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.SunFix.get(), stack) != 0
                    && player instanceof ServerPlayer serverPlayer
                    && stack.hurt(-1, RandomSource.create(), serverPlayer)){
                stack.shrink(1);
                stack.setDamageValue(0);
            }
        }
    }
}
