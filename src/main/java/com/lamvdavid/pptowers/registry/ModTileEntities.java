package com.lamvdavid.pptowers.registry;

import com.lamvdavid.pptowers.PPTowers;
import com.lamvdavid.pptowers.entities.ArrowTowerEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, PPTowers.MOD_ID);

    public static void init() {
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<TileEntityType<?>> ARROW_TOWER_ENTITY = TILE_ENTITY_TYPES.register("arrow_tower_entity", () -> TileEntityType.Builder.of(ArrowTowerEntity::new, ModBlocks.ARROW_TOWER_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> CHAINING_TOWER_ENTITY = TILE_ENTITY_TYPES.register("chaining_tower_entity", () -> TileEntityType.Builder.of(ArrowTowerEntity::new, ModBlocks.CHAINING_TOWER_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> FIREBALL_TOWER_ENTITY = TILE_ENTITY_TYPES.register("fireball_tower_entity", () -> TileEntityType.Builder.of(ArrowTowerEntity::new, ModBlocks.FIREBALL_TOWER_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> LIGHTNING_TOWER_ENTITY = TILE_ENTITY_TYPES.register("lightning_tower_entity", () -> TileEntityType.Builder.of(ArrowTowerEntity::new, ModBlocks.LIGHTNING_TOWER_BLOCK.get()).build(null));
}
