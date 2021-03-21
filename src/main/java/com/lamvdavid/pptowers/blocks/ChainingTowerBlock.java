package com.lamvdavid.pptowers.blocks;

import com.lamvdavid.pptowers.entities.ChainingTowerEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ChainingTowerBlock extends TowerBlock{
    public ChainingTowerBlock(AbstractBlock.Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ChainingTowerEntity();
    }
}
