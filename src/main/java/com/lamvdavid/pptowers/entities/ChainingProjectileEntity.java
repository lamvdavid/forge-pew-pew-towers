package com.lamvdavid.pptowers.entities;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
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
        MobEntity target = getClosestMonsterEntity(sourceEntity);
        if(target != null && numOfChains > 0) {

            ChainingProjectileEntity chainedArrow = new ChainingProjectileEntity(level, sourceEntity.getX() + targetX, sourceEntity.getEyeY() + targetY, sourceEntity.getZ() + targetZ, numOfChains - 1);
            double x = target.getX() - chainedArrow.getX();
            double y = target.getY(0.33333333D) - chainedArrow.getY();
            double z = target.getZ() - chainedArrow.getZ();
            double d = MathHelper.sqrt(x * x + z * z);
            chainedArrow.shoot(x, y + d * 0.1D, z, 1.6F, 0);
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
    public List<MobEntity> getHostileEntities() {
        List<MobEntity> hostiles = this.level.getEntitiesOfClass(MobEntity.class, range, null);
        hostiles.removeIf(e -> e instanceof AgeableEntity || e instanceof GolemEntity);
        return hostiles;
    }

    //Gets the closest hostile mob in sight that isn't the source entity from the position from the source entity
    public MobEntity getClosestMonsterEntity(Entity sourceEntity) {
        List<MobEntity> hostileEntities = getHostileEntities();
        hostileEntities.remove(sourceEntity);
        hostileEntities = hostileEntities.stream().filter(e -> e.hurtTime == 0).collect(Collectors.toList());

        MobEntity target = null;
        double targetDistance = 10000.0;
        double testDistance;
        double[] offset;

        for(MobEntity e : hostileEntities) {
            testDistance = e.distanceToSqr(sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ());
            if(testDistance < targetDistance) {
                offset = getTargetDirection(sourceEntity, e);
                if(checkSightLine(e, offset[0], offset[1], offset[2])) {
                    setTargetDirection(offset[0], offset[1], offset[2]);
                    target = e;
                    targetDistance = testDistance;
                }
            }
        }

        return target;
    }

    //Checks if the tower block can see the mob
    public boolean checkSightLine(Entity entity, double x, double y, double z) {
        Vector3d vec3d = new Vector3d(this.getX() + x, this.getY() + y, this.getZ() + z);
        Vector3d vec3d2 = new Vector3d(entity.getX(), entity.getEyeY(), entity.getZ());
        RayTraceResult.Type test = this.level.clip(new RayTraceContext(vec3d2, vec3d, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getType();
        return test == RayTraceResult.Type.MISS;
    }
}
