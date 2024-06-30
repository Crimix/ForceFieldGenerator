package com.black_dog20.forcefieldgenerator;

import com.black_dog20.forcefieldgenerator.items.ModItems;
import com.black_dog20.forcefieldgenerator.network.PacketHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod(ForceFieldGenerator.MOD_ID)
public class ForceFieldGenerator {

    public static final String MOD_ID = "forcefieldgenerator";

    public ForceFieldGenerator() {
        IEventBus event = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
        Config.loadConfig(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-server.toml"));

        ModItems.ITEMS.register(event);
        ModItems.CREATIVE_MODE_TABS.register(event);

        event.addListener(this::setup);
        event.addListener(this::imc);
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.register();
    }

    private void imc(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("forcefield_generator").icon(new ResourceLocation(MOD_ID, "item/empty_forcefield_generator_slot")).build());
    }
}
