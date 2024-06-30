package com.black_dog20.forcefieldgenerator.utils;

import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.forcefieldgenerator.ForceFieldGenerator;

public enum Translations implements ITranslation {
    INFO("tooltip.item.info"),
    ITEM_CATEGORY("itemGroup.forcefieldgenerator"),
    STORED_ENERGY("tooltip.item.stored_energy"),
    COST("tooltip.item.energy_cost"),
    TURN_OFF("tooltip.item.turn_off"),
    POWER_LOW("msg.power_low"),


    TEMPLATE_DESCRIPTION("tooltip.template.description"),
    TEMPLATE_SLOT_DESCRIPTION("tooltip.template.slot_description"),
    TEMPLATE_ADDITIONS_SLOT_DESCRIPTION("tooltip.template.additions_slot_description"),
    TEMPLATE_SLOT_INFO("tooltip.template.slot_info"),
    TEMPLATE_ADDITIONS_SLOT_INFO("tooltip.template.additions_slot_info");

    private final String modId;
    private final String key;

    Translations(String key) {
        this.modId = ForceFieldGenerator.MOD_ID;
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getModId() {
        return modId;
    }
}
