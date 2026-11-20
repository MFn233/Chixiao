package com.mfn233.chixiao;

import com.mfn233.chixiao.specialattack.JueYing;
import com.mfn233.chixiao.specialattack.LieFengShuang;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;

/**
 * SA（特殊攻击）的 id 分配。
 *
 * SlashBlade 的注册表是 {@code Map<Integer, SpecialAttackBase>}，id 全局共享。
 * 旧版本这里用了 10 / 11，而 Housamo 附属会无条件占用 11（冰刀三尺）与 12，
 * 结果 11 被覆盖、或本模组静默跳过，赤零挥出来的就是别人的 SA。
 *
 * 现在：
 * 1. 改用高位 id 段（0x4D46 = "MF"）降低碰撞概率；
 * 2. 一旦发现占用就顺延取下一个空闲 id，并打 ERROR 日志（旧实现是静默跳过）；
 * 3. 解析结果写进静态字段，供刀注册与存档迁移使用。
 */
public final class SaRegistry {

    /** 'M','F'。本模组 SA id 段的起点。 */
    public static final int ID_BASE = 0x4D46; // 19782
    /** 顺延搜索的窗口大小。 */
    public static final int ID_SPAN = 32;

    /** 冽风霜。未分配时为 -1。 */
    public static int LIE_FENG_SHUANG = -1;
    /** 绝影。未分配时为 -1。 */
    public static int JUE_YING = -1;

    private SaRegistry() {
    }

    public static void registerAll() {
        LIE_FENG_SHUANG = register("冽风霜 LieFengShuang", new LieFengShuang(), ID_BASE);
        JUE_YING = register("绝影 JueYing", new JueYing(), ID_BASE + 1);
        ChixiaoMod.LOGGER.info("SA id 分配完成：冽风霜={} 绝影={}", LIE_FENG_SHUANG, JUE_YING);
    }

    private static int register(String label, SpecialAttackBase sa, int preferred) {
        for (int id = preferred; id < preferred + ID_SPAN; id++) {
            SpecialAttackBase existing = ItemSlashBlade.specialAttacks.get(id);
            if (existing == null) {
                ItemSlashBlade.specialAttacks.put(id, sa);
                if (id != preferred) {
                    ChixiaoMod.LOGGER.error("SA id {} 已被占用，{} 顺延使用 {}", preferred, label, id);
                }
                return id;
            }
            if (existing.getClass() == sa.getClass()) {
                // 重复注册（例如重载），直接复用
                return id;
            }
            ChixiaoMod.LOGGER.error("SA id {} 已被 {} 占用，{} 继续寻找空闲 id",
                    id, existing.getClass().getName(), label);
        }
        // 理论上不会发生；兜底为覆盖首选 id，至少保证本模组可用
        ChixiaoMod.LOGGER.error("SA id 段 {}~{} 全部被占用，{} 将覆盖首选 id", preferred, preferred + ID_SPAN - 1, label);
        ItemSlashBlade.specialAttacks.put(preferred, sa);
        return preferred;
    }
}