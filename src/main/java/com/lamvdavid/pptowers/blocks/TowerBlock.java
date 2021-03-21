package com.lamvdavid.pptowers.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public abstract class TowerBlock extends Block {
    public TowerBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

}
