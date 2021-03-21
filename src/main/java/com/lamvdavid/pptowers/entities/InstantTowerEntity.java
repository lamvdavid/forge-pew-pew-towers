package com.lamvdavid.pptowers.entities;

import net.minecraft.tileentity.TileEntityType;

/*  Used for non-projectile entities
 */
public abstract class InstantTowerEntity extends TowerBlockEntity {
    public InstantTowerEntity(TileEntityType<?> type, double xRange, double yRange, double zRange, int fireRate) {
        super(type, xRange, yRange, zRange, fireRate);
    }
}
