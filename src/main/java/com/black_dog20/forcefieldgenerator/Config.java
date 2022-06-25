package com.black_dog20.forcefieldgenerator;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {
    public static final String CATEGORY_GENERAL = "general";

    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.IntValue DEFAULT_MAX_POWER;
    public static ForgeConfigSpec.IntValue DEFAULT_POWER_COST;

    static {
        com.electronwill.nightconfig.core.Config.setInsertionOrderPreserved(true); //Needed until https://github.com/TheElectronWill/night-config/issues/85 is released

        SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        DEFAULT_MAX_POWER = SERVER_BUILDER.comment("Default maximum power for force-field generator")
                .defineInRange("defaultMaxPower", 1_000_000, 0, Integer.MAX_VALUE);
        DEFAULT_POWER_COST = SERVER_BUILDER.comment("Default cost per hit for force-field generator")
                .defineInRange("defaultPowerCost", 10_000, 0, Integer.MAX_VALUE);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .preserveInsertionOrder()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
    }


}
