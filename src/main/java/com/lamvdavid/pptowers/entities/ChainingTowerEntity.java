package com.lamvdavid.pptowers.entities;

import com.lamvdavid.pptowers.registry.ModTileEntities;
import net.minecraft.dispenser.Position;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

public class ChainingTowerEntity extends TrajectoryTowerEntity {
    //Hard coded stats. May change later if doing tower upgrades
    public static final double X_RANGE = 10.0;
    public static final double Y_RANGE = 8.0;
    public static final double Z_RANGE = 10.0;
    public static final int FIRE_RATE = 20;
    public static final int NUM_OF_CHAINS = 3;

    public ChainingTowerEntity() {
        super(ModTileEntities.CHAINING_TOWER_ENTITY.get(), X_RANGE, Y_RANGE, Z_RANGE, FIRE_RATE);
    }

    @Override
    protected ProjectileEntity createProjectile(World world, Position position) {
        return new ChainingProjectileEntity(world, position.x(), position.y(), position.z(), NUM_OF_CHAINS);

    }
}
