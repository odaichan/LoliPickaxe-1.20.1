package net.daichang.loli_pickaxe.Config;

import net.daichang.loli_pickaxe.LoliPickaxeMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;


@Mod.EventBusSubscriber(modid = LoliPickaxeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue SUPER_MOD;
    private static final ForgeConfigSpec.DoubleValue BreakingRanage;
    private static final ForgeConfigSpec.DoubleValue AttackRange;
    private static final ForgeConfigSpec.DoubleValue RightClickRange;
    private static final ForgeConfigSpec.BooleanValue BlueScreenAttack;
    private static final ForgeConfigSpec.BooleanValue RemoveEntity;
    private static final ForgeConfigSpec.BooleanValue CleanInventory;
    private static final ForgeConfigSpec.BooleanValue ReverseInjury;
    private static final ForgeConfigSpec.BooleanValue KickPlayer;
    private static final ForgeConfigSpec.BooleanValue DisplayFluidBorder;

    public static int breakrange = 0;
    public static double breakBlockRange = 10;
    public static double entityAttackRange = 10;
    public static double attackRange = 100;
    public static boolean sMode;
    public static boolean blueScreen;
    public static boolean classTarget;
    public static boolean remove;
    public static boolean canRemoval;
    public static boolean kickPlayer ;
    public static boolean reverseInjury ;
    public static boolean displayFluidBorder ;
    public static boolean liquidWalk;
    public static boolean forcedExcavation;
    public static boolean breakRange;
    public static boolean clearInventory;
    public static boolean disarm;
    public static boolean soulAssumption;
    public static boolean autoAttack;
    public static boolean entityReachQ;
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        sMode = SUPER_MOD.get();
        breakBlockRange = BreakingRanage.get();
        entityAttackRange = AttackRange.get();
        attackRange = RightClickRange.get();
        blueScreen = BlueScreenAttack.get();
        classTarget = RemoveEntity.get();
        clearInventory = CleanInventory.get();
        reverseInjury = ReverseInjury.get();
        kickPlayer = KickPlayer.get();
        displayFluidBorder = DisplayFluidBorder.get();
    }

    static {
        SUPER_MOD = BUILDER.comment("模式").define("超级模式", false);
        BreakingRanage = BUILDER.comment("模式").defineInRange("挖掘距离", 10.0D, 0.0D, 200.0D);
        AttackRange = BUILDER.comment("模式").defineInRange("攻击距离", 10.0D, 0.0D, 200.0D);
        RightClickRange = BUILDER.comment("模式").defineInRange("潜行右键攻击距离", 10.0D, 0.0D, Double.MAX_VALUE);
        BlueScreenAttack = BUILDER.comment("模式").define("蓝屏打击", false);
        RemoveEntity = BUILDER.comment("模式").define("实体删除", false);
        CleanInventory = BUILDER.comment("模式").define("清除背包", false);
        ReverseInjury = BUILDER.comment("模式").define("反伤", true);
        KickPlayer = BUILDER.comment("模式").define("踢出玩家", false);
        DisplayFluidBorder = BUILDER.comment("模式").define("显示流体边框", false);
    }
}
