package com.black_dog20.forcefieldgenerator.items;

import com.black_dog20.forcefieldgenerator.ForceFieldGenerator;
import com.black_dog20.forcefieldgenerator.utils.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModItems {
    public static final Item.Properties ITEM_GROUP = new Item.Properties();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ForceFieldGenerator.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ForceFieldGenerator.MOD_ID);

    public static final RegistryObject<Item> FORCE_FIELD_GENERATOR = ITEMS.register("force_field_generator", () -> new ForceFieldGeneratorItem(ITEM_GROUP));
    public static final RegistryObject<Item> STAR_INFUSED_NETHERITE_INGOT = ITEMS.register("star_infused_netherite_ingot", () -> new Item(ITEM_GROUP.stacksTo(64)));
    public static final RegistryObject<Item> FORCE_FIELD_GENERATOR_PLATE = ITEMS.register("force_field_generator_plate", () -> new Item(ITEM_GROUP.stacksTo(64)));
    public static final RegistryObject<Item> FORCE_FIELD_GENERATOR_CORE = ITEMS.register("force_field_generator_core", () -> new Item(ITEM_GROUP.stacksTo(64)));

    public static final RegistryObject<Item> FORCE_FIELD_GENERATOR_TEMPLATE = ITEMS.register("force_field_generator_template", () -> new SmithingTemplateItem(Translations.TEMPLATE_SLOT_DESCRIPTION.get(), Translations.TEMPLATE_ADDITIONS_SLOT_DESCRIPTION.get(), Translations.TEMPLATE_DESCRIPTION.get(), Translations.TEMPLATE_SLOT_INFO.get(), Translations.TEMPLATE_ADDITIONS_SLOT_INFO.get(), List.of(), List.of()));

    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("forcefieldgenerator_tab", () -> CreativeModeTab.builder()
            .icon(() -> FORCE_FIELD_GENERATOR.get().getDefaultInstance())
            .title(Translations.ITEM_CATEGORY.get(ChatFormatting.RESET))
            .displayItems((parameters, output) -> {
                for (RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
                    output.accept(item.get());
                }
            }).build());
}
