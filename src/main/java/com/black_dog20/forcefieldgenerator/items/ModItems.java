package com.black_dog20.forcefieldgenerator.items;

import com.black_dog20.forcefieldgenerator.ForceFieldGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final Item.Properties ITEM_GROUP = new Item.Properties().tab(ForceFieldGenerator.itemGroup);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ForceFieldGenerator.MOD_ID);

    public static final RegistryObject<Item> FORCE_FIELD_GENERATOR = ITEMS.register("force_field_generator", () -> new ForceFieldGeneratorItem(ITEM_GROUP));
    public static final RegistryObject<Item> STAR_INFUSED_NETHERITE_INGOT = ITEMS.register("star_infused_netherite_ingot", () -> new Item(ITEM_GROUP.stacksTo(64)));
    public static final RegistryObject<Item> FORCE_FIELD_GENERATOR_PLATE = ITEMS.register("force_field_generator_plate", () -> new Item(ITEM_GROUP.stacksTo(64)));
    public static final RegistryObject<Item> FORCE_FIELD_GENERATOR_CORE = ITEMS.register("force_field_generator_core", () -> new Item(ITEM_GROUP.stacksTo(64)));
}
