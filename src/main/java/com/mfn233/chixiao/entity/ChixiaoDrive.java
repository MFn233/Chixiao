package com.mfn233.chixiao.entity;

import mods.flammpfeil.slashblade.entity.EntityDrive;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class ChixiaoDrive extends EntityDrive {

    private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(ChixiaoDrive.class, DataSerializers.VARINT);

    public ChixiaoDrive(World par1World) {
        super(par1World);
    }

    public ChixiaoDrive(World par1World, EntityLivingBase entityLiving, float AttackLevel, boolean multiHit, float roll) {
        super(par1World, entityLiving, AttackLevel, multiHit, roll);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(COLOR, 0x33A0FF);
    }

    public int getColor() {
        return this.getDataManager().get(COLOR);
    }

    public void setColor(int color) {
        this.getDataManager().set(COLOR, color);
    }
}