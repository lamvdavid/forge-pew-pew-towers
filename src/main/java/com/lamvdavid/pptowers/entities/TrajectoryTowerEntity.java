package com.lamvdavid.pptowers.entities;

import net.minecraft.dispenser.Position;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.MathHelper;

public abstract class TrajectoryTowerEntity extends ProjectileTowerEntity{
    public TrajectoryTowerEntity(TileEntityType<?> type, double xRange, double yRange, double zRange, int fireRate) {
        super(type, xRange, yRange, zRange, fireRate);
    }

    @Override
    protected void shoot() {
        Position position = new Position(worldPosition.getX() + xFace, worldPosition.getY() + yFace, worldPosition.getZ() + zFace);
        ProjectileEntity proj = this.createProjectile(level, position);
        double x = target.getX() - proj.getX();
        double y = target.getY(0.33333333D) - proj.getY();
        double z = target.getZ() - proj.getZ();
        double d = MathHelper.sqrt(x * x + z * z);
        proj.shoot(x, y + d * 0.20000000298023224D, z, 1.6F, 0);
        level.addFreshEntity(proj);
    }
}
