package com.lamvdavid.pptowers.entities;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public abstract class TowerBlockEntity extends TileEntity implements ITickableTileEntity {
    public AxisAlignedBB range;
    protected boolean isRangeSet = false;
    public MobEntity target = null;
    public double xRange;
    public double yRange;
    public double zRange;
    public int fireRate;
    public int fireRateCounter;
    public int checkTargetCounter = 0;
    public double xFace;
    public double yFace;
    public double zFace;

    public TowerBlockEntity(TileEntityType<?> type, double xRange, double yRange, double zRange, int fireRate) {
        super(type);
        this.xRange = xRange;
        this.yRange = yRange;
        this.zRange = zRange;
        this.fireRate = fireRate;
        this.fireRateCounter = fireRate;
    }

    //Shoot a projectile at the target mob
    protected abstract void shoot();

    //Checks if the tower block can see the mob
    public boolean checkSightLine(Entity entity, double x, double y, double z) {
        Vector3d vec3d = new Vector3d(worldPosition.getX() + x, worldPosition.getY() + y, worldPosition.getZ() + z);
        Vector3d vec3d2 = new Vector3d(entity.getX(), entity.getEyeY(), entity.getZ());
        RayTraceResult.Type test = this.level.clip(new RayTraceContext(vec3d2, vec3d, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getType();
        return test == RayTraceResult.Type.MISS;
    }



    @Override
    public void tick() {
        if(!this.level.isClientSide){
            if(checkTargetCounter == 20) {
                target = getClosestHostileEntity();
                checkTargetCounter = 0;
            } else {
                checkTargetCounter++;
            }

        }
        if(target != null) {
            if(fireRateCounter == fireRate) {
                shoot();
                fireRateCounter = 0;
            } else {
                fireRateCounter++;
            }
        }
    }

    //Offsets the position of the block for calculations
    public double[] getTargetDirection(Entity e) {
        double[] xyz = new double[3];
        double x = e.getX() - worldPosition.getX();
        double y = e.getY() - worldPosition.getY();
        double z = e.getZ() - worldPosition.getZ();

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

    //Offsets the direction of the block for spawning projectiles
    public void setTargetDirection(double x, double y, double z) {
        xFace = x;
        yFace = y;
        zFace = z;
    }

    //Retrieves all hostiles entities within range of the block
    public List<MobEntity> getHostileEntities() {
        if(!isRangeSet) {
            this.range = new AxisAlignedBB(this.worldPosition.getX() - xRange, this.worldPosition.getY() - yRange, this.worldPosition.getZ() - zRange, this.worldPosition.getX() + xRange, this.worldPosition.getY() + yRange, this.worldPosition.getZ() + zRange);
            isRangeSet = true;
        }
        List<MobEntity> hostiles = this.getLevel().getEntitiesOfClass(MobEntity.class, range, null);
        hostiles.removeIf(e -> e instanceof AgeableEntity || e instanceof GolemEntity);
        return hostiles;

    }

    //Gets the closest hostile mob in sight
    public MobEntity getClosestHostileEntity() {
        List<MobEntity> hostileEntities = getHostileEntities();
        MobEntity target = null;
        double targetDistance = 10000.0;
        double testDistance;
        double[] offset;

        for(Entity e : hostileEntities) {
            testDistance = e.distanceToSqr(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ());
            if(testDistance < targetDistance) {
                offset = getTargetDirection(e);
                if(checkSightLine(e, offset[0], offset[1], offset[2])) {
                    setTargetDirection(offset[0], offset[1], offset[2]);
                    target = (MobEntity) e;
                    targetDistance = testDistance;
                }
            }
        }

        return target;
    }
}
