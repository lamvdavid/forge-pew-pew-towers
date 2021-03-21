package com.lamvdavid.pptowers.blocks;

import com.lamvdavid.pptowers.entities.LightningTowerEntity;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class LightningTowerBlock extends TowerBlock {
    public LightningTowerBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LightningTowerEntity();
    }
}
