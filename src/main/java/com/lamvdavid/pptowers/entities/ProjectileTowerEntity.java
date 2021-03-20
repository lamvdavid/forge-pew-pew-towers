package com.lamvdavid.pptowers.entities;

import net.minecraft.dispenser.Position;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;

public abstract class ProjectileTowerEntity extends TowerBlockEntity {

    public ProjectileTowerEntity(TileEntityType<?> type, double xRange, double yRange, double zRange, int fireRate) {
        super(type, xRange, yRange, zRange, fireRate);
    }

    @Override
    protected abstract void shoot();
    protected abstract ProjectileEntity createProjectile(World world, Position position);

}
