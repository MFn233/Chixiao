package com.mfn233.chixiao.specialattack;

import com.mfn233.chixiao.Tags;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * SA「绝影」: fires 7 phantom swords in succession along the player's sight.
 * The first 6 swords deal 10 damage each (red glow), the final one deals 30 (blue glow).
 */
public class JueYing extends SpecialAttackBase {

    public static final int SA_TYPE = Integer.parseInt(Tags.SA_TYPE_JUEYING);
    public static final int SHOT_COUNT = 7;

    private static final float[] DAMAGE = {10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 30.0f};
    private static final int[] COLORS = {0xFF4040, 0xFF4040, 0xFF4040, 0xFF4040, 0xFF4040, 0xFF4040, 0x33A0FF};

    @Override
    public String toString() {
        return Tags.SA_NAME_JUEYING;
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
            ConsecutiveFireScheduler.schedule(player, SHOT_COUNT, DAMAGE, COLORS, false);
        }

        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.SlashEdge);
    }
}