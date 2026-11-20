package com.mfn233.chixiao.client;

import com.mfn233.chixiao.ChixiaoBlade;
import com.mfn233.chixiao.Tags;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TooltipHandler {

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemSlashBladeNamed)) {
            return;
        }

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
        String name = ItemSlashBladeNamed.CurrentItemName.get(tag);
        if (Tags.BLADE_KEY_CHILING.equals(name)) {
            event.getToolTip().add(1, I18n.format("chixiao.tooltip.chiling"));

            // 觉醒门槛很高（2000 击杀 / 5000 耀魂），直接把进度写出来，否则玩家无从判断
            int kills = ItemSlashBlade.KillCount.get(tag);
            int soul = ItemSlashBlade.ProudSoul.get(tag);
            boolean ready = kills >= ChixiaoBlade.REQUIRED_KILLS && soul >= ChixiaoBlade.REQUIRED_PROUD_SOUL;
            TextFormatting color = ready ? TextFormatting.GREEN : TextFormatting.GRAY;
            event.getToolTip().add(2, color + I18n.format("chixiao.tooltip.awaken",
                    kills, ChixiaoBlade.REQUIRED_KILLS, soul, ChixiaoBlade.REQUIRED_PROUD_SOUL));
        }
    }
}
