package com.lamvdavid.pptowers.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

/*
    Custom projectile that creates another projectile after hitting an entity
    Uses ArrowEntity as base
 */
public class ChainingProjectileEntity extends ArrowEntity {
    //Hard coded stats. May change later if doing tower upgrades
    public static final double X_RANGE = 10.0;
    public static final double Y_RANGE = 8.0;
    public static final double Z_RANGE = 10.0;
    public double targetX;
    public double targetY;
    public double targetZ;
    public AxisAlignedBB range = null;
    public int numOfChains;

    public ChainingProjectileEntity(World world, double x, double y, double z, int numOfChains) {
        super(world, x, y, z);
        this.numOfChains = numOfChains;
    }

    protected void onHitEntity(EntityRayTraceResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        range = new AxisAlignedBB(this.position().x() - X_RANGE, this.position().y() - Y_RANGE, this.position().z() - Z_RANGE, this.position().x() + X_RANGE, this.position().y() + Y_RANGE, this.position().z() + Z_RANGE);
        Entity sourceEntity = entityHitResult.getEntity();
        MonsterEntity target = getClosestMonsterEntity(sourceEntity);
        if(target != null && numOfChains > 0) {

            ChainingProjectileEntity chainedArrow = new ChainingProjectileEntity(level, sourceEntity.getX() + targetX, sourceEntity.getEyeY() + targetY, sourceEntity.getZ() + targetZ, numOfChains - 1);
            double x = target.getX() - chainedArrow.getX();
            double y = target.getY(0.33333333D) - chainedArrow.getY();
            double z = target.getZ() - chainedArrow.getZ();
            double d = MathHelper.sqrt(x * x + z * z);
            chainedArrow.shoot(x, y + d * 0.20000000298023224D, z, 1.6F, 0);
            level.addFreshEntity(chainedArrow);
        }

    }

    //Offsets the position of the entity for calculations
    public double[] getTargetDirection(Entity source, Entity target) {
        double[] xyz = new double[3];
        double x = target.getX() - source.getX();
        double y = target.getY() - source.getY();
        double z = target.getZ() - source.getZ();

        xyz[1] = 0.5;

        if(y >= 3) {
            xyz[0] = 0.5;
            xyz[1] = 1.05;
            xyz[2] = 0.5;
        } else if(y <= -3) {
            xyz[0] = 0.5;
            xyz[1] = -0.1;
            xyz[2] = 0.5;
        } else {
            if(Math.abs(x) > Math.abs(z)) {
                xyz[0] = x > 0 ? 1.05 : -0.1;
                xyz[2] = 0.5;
            } else {
                xyz[2] = z > 0 ? 1.05 : -0.1;
                xyz[0] = 0.5;
            }
        }

        return xyz;
    }

    //Offsets the direction of the entity for spawning projectiles
    public void setTargetDirection(double x, double y, double z) {
        targetX = x;
        targetY = y;
        targetZ = z;
    }

    //Returns list of HostileEntities in range
    public List<MonsterEntity> getHostileEntities() {
        return this.level.getEntitiesOfClass(MonsterEntity.class,range,null);
    }

    //Gets the closest hostile mob in sight that isn't the source entity from the position from the source entity
    public MonsterEntity getClosestMonsterEntity(Entity sourceEntity) {
        List<MonsterEntity> hostileEntities = getHostileEntities();
        hostileEntities.remove(sourceEntity);
        hostileEntities = hostileEntities.stream().filter(e -> e.hurtTime == 0).collect(Collectors.toList());

        MonsterEntity target = null;
        double targetDistance = 10000.0;
        double testDistance;
        double[] offset;

        for(MonsterEntity e : hostileEntities) {
            testDistance = e.distanceToSqr(sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ());
            if(testDistance < targetDistance) {
                offset = getTargetDirection(sourceEntity, e);
                if(e.canSee(sourceEntity)) {
                    setTargetDirection(offset[0], offset[1], offset[2]);
                    target = e;
                    targetDistance = testDistance;
                }
            }
        }

        return target;
    }
}
