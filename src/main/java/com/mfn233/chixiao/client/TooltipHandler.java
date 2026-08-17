package com.mfn233.chixiao.client;

import com.mfn233.chixiao.Tags;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
        }
    }
}
