package com.black_dog20.forcefieldgenerator.client;

import com.black_dog20.forcefieldgenerator.ForceFieldGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber( modid = ForceFieldGenerator.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientStartup {

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        //ClientRegistry.registerKeyBinding(Keybinds.keyMode);
    }

    @SubscribeEvent
    public static void setupClient(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location() == InventoryMenu.BLOCK_ATLAS) {
            event.addSprite(new ResourceLocation(ForceFieldGenerator.MOD_ID, "item/empty_forcefield_generator_slot"));
        }
    }
}