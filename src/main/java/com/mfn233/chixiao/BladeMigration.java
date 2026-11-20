package com.mfn233.chixiao;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.List;

/**
 * 把旧存档里刀的 SA id 迁移到新的高位 id。
 *
 * 旧版本把冽风霜写成 11、绝影写成 10；换成 0x4D46 段之后，老存档里的刀
 * 仍指向旧 id，会变成"没有 SA"或别人的 SA。这里在玩家登录时扫一次背包修正。
 */
public class BladeMigration {

    /** 旧版本使用的 SA id。 */
    private static final int LEGACY_LIE_FENG_SHUANG = 11;
    private static final int LEGACY_JUE_YING = 10;

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        if (player == null || player.world == null || player.world.isRemote) {
            return;
        }

        int fixed = 0;
        fixed += migrate(player.inventory.mainInventory);
        fixed += migrate(player.inventory.armorInventory);
        fixed += migrate(player.inventory.offHandInventory);

        if (fixed > 0) {
            ChixiaoMod.LOGGER.info("已把 {} 把赤零/赤霄的 SA id 迁移到新编号", fixed);
        }
    }

    private int migrate(List<ItemStack> stacks) {
        int fixed = 0;
        for (ItemStack stack : stacks) {
            if (migrateOne(stack)) {
                fixed++;
            }
        }
        return fixed;
    }

    private boolean migrateOne(ItemStack stack) {
        if (stack == null || stack.isEmpty() || !(stack.getItem() instanceof ItemSlashBlade)) {
            return false;
        }
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
        if (tag == null) {
            return false;
        }

        String name = ItemSlashBladeNamed.CurrentItemName.get(tag);
        if (name == null) {
            return false;
        }

        Integer stored = ItemSlashBlade.SpecialAttackType.get(tag);
        int current = stored == null ? -1 : stored;

        int target = -1;
        if (Tags.BLADE_KEY_CHILING.equals(name) && current == LEGACY_LIE_FENG_SHUANG) {
            target = SaRegistry.LIE_FENG_SHUANG;
        } else if (Tags.BLADE_KEY_CHIXIAO.equals(name) && current == LEGACY_JUE_YING) {
            target = SaRegistry.JUE_YING;
        }

        if (target < 0 || target == current) {
            return false;
        }
        ItemSlashBlade.SpecialAttackType.set(tag, target);
        return true;
    }
}