package com.black_dog20.forcefieldgenerator.datagen;

import com.black_dog20.bml.datagen.BaseLanguageProvider;
import com.black_dog20.forcefieldgenerator.ForceFieldGenerator;
import com.black_dog20.forcefieldgenerator.items.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.data.DataGenerator;

import static com.black_dog20.forcefieldgenerator.utils.Translations.*;

public class GeneratorLanguageEnglish extends BaseLanguageProvider {

    public GeneratorLanguageEnglish(DataGenerator gen) {
        super(gen.getPackOutput(), ForceFieldGenerator.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addPrefixed(ITEM_CATEGORY, "Force-Field Generator");
        add("curios.identifier.forcefield_generator", "Force-Field Generator");

        addPrefixed(INFO, "Protects the wearer from harm", ChatFormatting.GRAY);
        addPrefixed(STORED_ENERGY, "Stored energy:", ChatFormatting.GRAY);
        addPrefixed(COST, "Cost per hit:", ChatFormatting.GRAY);
        addPrefixed(TURN_OFF, "Use %s to toggle force-field generator on/off", ChatFormatting.GRAY);

        add(ModItems.FORCE_FIELD_GENERATOR.get(), "Force-Field Generator");
        add(ModItems.FORCE_FIELD_GENERATOR_PLATE.get(), "Force-Field Generator Plate");
        add(ModItems.FORCE_FIELD_GENERATOR_CORE.get(), "Force-Field Generator Core");
        add(ModItems.STAR_INFUSED_NETHERITE_INGOT.get(), "Start-Infused Netherite");

        addPrefixed(TEMPLATE_DESCRIPTION, "Force-Field Generator Crafting", ChatFormatting.GRAY);
        addPrefixed(TEMPLATE_SLOT_DESCRIPTION, "Netherite Ingot", ChatFormatting.BLUE);
        addPrefixed(TEMPLATE_ADDITIONS_SLOT_DESCRIPTION, "Nether Star", ChatFormatting.BLUE);
        addPrefixed(TEMPLATE_SLOT_INFO, "Add Netherite Ingot");
        addPrefixed(TEMPLATE_ADDITIONS_SLOT_INFO, "Add Nether Star");

        addPrefixed(POWER_LOW, "WARNING Force-field power low!!", ChatFormatting.RED);
    }

    @Override
    public String getName() {
        return "Force-field Generator: Languages: en_us";
    }
}
