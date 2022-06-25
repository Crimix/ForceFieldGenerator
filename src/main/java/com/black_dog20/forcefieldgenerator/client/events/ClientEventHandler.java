package com.black_dog20.forcefieldgenerator.client.events;

import com.black_dog20.forcefieldgenerator.ForceFieldGenerator;
import com.black_dog20.forcefieldgenerator.utils.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

import static com.black_dog20.forcefieldgenerator.utils.Translations.POWER_LOW;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = ForceFieldGenerator.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {

    private static int tickCounter = 0;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START)
            return;
        Player player = Minecraft.getInstance().player;
        if (player == null || player.isCreative())
            return;

        Optional<ItemStack> stack =  ModUtil.findForceFieldGenerator(player);
        stack.ifPresent(generator -> {
            if (tickCounter % 240 == 0) {
                int percent = ModUtil.getBatteryPercentage(generator);
                if (percent < 20) {
                    Minecraft.getInstance().gui.setOverlayMessage(POWER_LOW.get(), false);
                }
                tickCounter = 1;
            }

            tickCounter++;
        });
    }
}
