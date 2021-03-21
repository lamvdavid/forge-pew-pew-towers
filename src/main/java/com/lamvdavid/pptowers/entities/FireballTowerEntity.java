package com.lamvdavid.pptowers.entities;

import com.lamvdavid.pptowers.registry.ModTileEntities;
import net.minecraft.dispenser.Position;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

public class FireballTowerEntity extends LineTowerEntity{
    //Hard coded stats. May change later if doing tower upgrades
    public static final double X_RANGE = 10.0;
    public static final double Y_RANGE = 8.0;
    public static final double Z_RANGE = 10.0;
    public static final int FIRE_RATE = 20;
    public static final int STRENGTH = 2;

    public FireballTowerEntity() {
        super(ModTileEntities.FIREBALL_TOWER_ENTITY.get(), X_RANGE, Y_RANGE, Z_RANGE, FIRE_RATE);
    }

    @Override
    protected ProjectileEntity createProjectile(World world, Position position) {
        AoEProjectileEntity fireball = new AoEProjectileEntity(world, position.x(), position.y(), position.z(), target.getX() - position.x(), target.getY() - position.y(), target.getZ() - position.z());
        fireball.explosionPower = STRENGTH;
        return fireball;
    }
}
