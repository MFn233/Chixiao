package com.mfn233.chixiao.specialattack;

import com.mfn233.chixiao.Tags;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * SA「冽风霜」: unleashes 7 phantom blades (blade qi) along the player's sight.
 * The qi alternates blue/red/blue/.../blue; damage increases up to 40 on the last wave.
 */
public class LieFengShuang extends SpecialAttackBase {

    public static final int SA_TYPE = Integer.parseInt(Tags.SA_TYPE);
    public static final int SHOT_COUNT = 7;

    private static final float[] DAMAGE = {10.0f, 15.0f, 20.0f, 25.0f, 30.0f, 35.0f, 40.0f};
    private static final int[] COLORS = {0x33A0FF, 0xFF4040, 0x33A0FF, 0xFF4040, 0x33A0FF, 0xFF4040, 0x33A0FF};

    @Override
    public String toString() {
        return Tags.SA_NAME;
    }

    @Override
    public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
        World world = player.world;
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);

        if (!world.isRemote) {
            final int cost = -15;
            if (!ItemSlashBlade.ProudSoul.tryAdd(tag, cost, false)) {
                ItemSlashBlade.damageItem(stack, 10, player);
            }
            ConsecutiveFireScheduler.schedule(player, SHOT_COUNT, DAMAGE, COLORS, true);
        }

        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.SlashEdge);
    }
}