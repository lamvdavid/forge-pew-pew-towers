package com.lamvdavid.pptowers.entities;

import com.lamvdavid.pptowers.registry.ModTileEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.effect.LightningBoltEntity;

import java.util.List;

public class LightningTowerEntity extends InstantTowerEntity {
    //Hard coded stats. May change later if doing tower upgrades
    public static final double X_RANGE = 10.0;
    public static final double Y_RANGE = 8.0;
    public static final double Z_RANGE = 10.0;
    public static final int FIRE_RATE = 20;
    public static final int NUM_OF_ATTACKS = 5;

    public List<MobEntity> hostiles;

    public LightningTowerEntity() {
        super(ModTileEntities.LIGHTNING_TOWER_ENTITY.get(), X_RANGE, Y_RANGE, Z_RANGE, FIRE_RATE);
    }

    @Override
    protected void shoot() {
        hostiles = getHostileEntities();
        for (int i = 0; i < NUM_OF_ATTACKS; i++) {
            int targetIndex = (int) (Math.random() * hostiles.size());
            LightningBoltEntity lightning = EntityType.LIGHTNING_BOLT.create(this.getLevel());
            lightning.moveTo(hostiles.get(targetIndex).position());
            this.getLevel().addFreshEntity(lightning);
        }
    }
}