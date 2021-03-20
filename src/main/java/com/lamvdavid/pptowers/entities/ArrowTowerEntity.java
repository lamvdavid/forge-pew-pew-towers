package com.lamvdavid.pptowers.entities;

import com.lamvdavid.pptowers.registry.ModEntities;
import net.minecraft.dispenser.Position;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

public class ArrowTowerEntity extends TrajectoryTowerEntity {
    //Hard coded stats. May change later if doing tower upgrades
    public static final double X_RANGE = 10.0;
    public static final double Y_RANGE = 8.0;
    public static final double Z_RANGE = 10.0;
    public static final int FIRE_RATE = 20;

    public ArrowTowerEntity() {
        super(ModEntities.ARROW_TOWER_ENTITY.get(), X_RANGE, Y_RANGE, Z_RANGE, FIRE_RATE);
    }

    protected ProjectileEntity createProjectile(World world, Position position) {
        ArrowEntity arrow = new ArrowEntity(world, position.x(), position.y(), position.z());
        return arrow;
    }
}
