package com.lamvdavid.pptowers.entities;

import net.minecraft.dispenser.Position;
import net.minecraft.tileentity.TileEntityType;

public abstract class LineTowerEntity extends ProjectileTowerEntity {

    public LineTowerEntity(TileEntityType<?> type, double xRange, double yRange, double zRange, int fireRate) {
        super(type, xRange, yRange, zRange, fireRate);
    }
    @Override
    protected void shoot() {
        Position position = new Position(worldPosition.getX() + xFace,worldPosition.getY() + yFace,worldPosition.getZ() + zFace);
        level.addFreshEntity(this.createProjectile(level, position));
    }
}
