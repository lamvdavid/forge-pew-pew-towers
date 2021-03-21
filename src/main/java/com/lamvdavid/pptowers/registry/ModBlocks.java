package com.lamvdavid.pptowers.registry;

import com.lamvdavid.pptowers.PPTowers;
import com.lamvdavid.pptowers.blocks.ArrowTowerBlock;
import com.lamvdavid.pptowers.blocks.ChainingTowerBlock;
import com.lamvdavid.pptowers.blocks.FireballTowerBlock;
import com.lamvdavid.pptowers.blocks.LightningTowerBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PPTowers.MOD_ID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Block> ARROW_TOWER_BLOCK = BLOCKS.register("arrow_tower_block", () ->
            new ArrowTowerBlock(AbstractBlock.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> CHAINING_TOWER_BLOCK = BLOCKS.register("chaining_tower_block", () ->
            new ChainingTowerBlock(AbstractBlock.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> FIREBALL_TOWER_BLOCK = BLOCKS.register("fireball_tower_block", () ->
            new FireballTowerBlock(AbstractBlock.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> LIGHTNING_TOWER_BLOCK = BLOCKS.register("lightning_tower_block", () ->
            new LightningTowerBlock(AbstractBlock.Properties.of(Material.STONE)));
}
