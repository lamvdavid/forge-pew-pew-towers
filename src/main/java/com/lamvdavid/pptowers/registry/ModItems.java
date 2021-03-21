package com.lamvdavid.pptowers.registry;

import com.lamvdavid.pptowers.PPTowers;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PPTowers.MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Item> ARROW_TOWER_ITEM = ITEMS.register("arrow_tower_item", () ->
            new BlockItem(ModBlocks.ARROW_TOWER_BLOCK.get(), new Item.Properties().tab(PPTowers.PPTOWERS_ITEM_GROUP)));
    public static final RegistryObject<Item> CHAINING_TOWER_ITEM = ITEMS.register("chaining_tower_item", () ->
            new BlockItem(ModBlocks.CHAINING_TOWER_BLOCK.get(), new Item.Properties().tab(PPTowers.PPTOWERS_ITEM_GROUP)));
    public static final RegistryObject<Item> FIREBALL_TOWER_ITEM = ITEMS.register("fireball_tower_item", () ->
            new BlockItem(ModBlocks.FIREBALL_TOWER_BLOCK.get(), new Item.Properties().tab(PPTowers.PPTOWERS_ITEM_GROUP)));
    public static final RegistryObject<Item> LIGHTNING_TOWER_ITEM = ITEMS.register("lightning_tower_item", () ->
            new BlockItem(ModBlocks.LIGHTNING_TOWER_BLOCK.get(), new Item.Properties().tab(PPTowers.PPTOWERS_ITEM_GROUP)));


}
