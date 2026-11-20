package com.mfn233.chixiao;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * 平衡性配置。
 *
 * SA 的实际伤害 = 代码里的基础值 × 这里的倍率，方便整合包作者按自己的数值环境调整，
 * 不必重新编译。两条 SA 的基础总伤害分别是：冽风霜 91、绝影 90。
 */
public final class ChixiaoConfig {

    private static final String CATEGORY = "balance";

    private static float lieFengShuangScale = 1.0F;
    private static float jueYingScale = 1.0F;

    private ChixiaoConfig() {
    }

    public static void load(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        lieFengShuangScale = config.getFloat("liefengshuangDamageScale", CATEGORY, 1.0F, 0.0F, 10.0F,
                "Damage multiplier for the special attack LieFengShuang (base total: 91). "
                        + "Set 0.7 for a tamer pack, 2.0 for a power-fantasy pack.");
        jueYingScale = config.getFloat("jueyingDamageScale", CATEGORY, 1.0F, 0.0F, 10.0F,
                "Damage multiplier for the special attack JueYing (base total: 90).");

        if (config.hasChanged()) {
            config.save();
        }

        ChixiaoMod.LOGGER.info("平衡倍率：冽风霜 x{}，绝影 x{}", lieFengShuangScale, jueYingScale);
    }

    public static float lieFengShuangScale() {
        return lieFengShuangScale;
    }

    public static float jueYingScale() {
        return jueYingScale;
    }
}