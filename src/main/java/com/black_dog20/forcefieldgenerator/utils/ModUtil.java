package com.black_dog20.forcefieldgenerator.utils;

import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.bml.utils.text.TextComponentBuilder;
import com.black_dog20.forcefieldgenerator.Config;
import com.black_dog20.forcefieldgenerator.compat.Curios;
import com.black_dog20.forcefieldgenerator.items.ForceFieldGeneratorItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.ModList;

import java.util.Optional;
import java.util.stream.Stream;

import static com.black_dog20.forcefieldgenerator.utils.Translations.COST;
import static com.black_dog20.forcefieldgenerator.utils.Translations.STORED_ENERGY;

public class ModUtil {

    public static int getEnergyCost(ItemStack stack) {
        return Config.DEFAULT_POWER_COST.get();
    }

    public static Optional<Component> getFormattedEnergyStored(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY, null).resolve()
                .map(energy -> {
                    String stored = MathUtil.formatThousands(energy.getEnergyStored());
                    String maxStored = MathUtil.formatThousands(energy.getMaxEnergyStored());
                    return TextComponentBuilder.of(STORED_ENERGY)
                            .space()
                            .with(String.format("%s/%s", stored, maxStored))
                            .format(ChatFormatting.GREEN)
                            .build();
                });
    }

    public static Optional<Component> getFormattedEnergyCost(ItemStack stack) {
        int value = getEnergyCost(stack);
        if (value == 0)
            return Optional.empty();
        else {
            Component textComponent = TextComponentBuilder.of(COST)
                    .space()
                    .with(String.format("-%s FE", MathUtil.formatThousands(Math.abs(value))))
                    .format(ChatFormatting.RED)
                    .build();
            return Optional.of(textComponent);
        }
    }

    public static int getBatteryPercentage(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
        if (energy != null)
            return Math.min((int) (((float) energy.getEnergyStored() / energy.getMaxEnergyStored()) * 100), 100);
        return 0;
    }

    public static Optional<ItemStack> findForceFieldGenerator(Player player) {
        return Stream.of(player.getInventory().items, player.getInventory().offhand, getCuriosForceFieldGenerator(player))
                .flatMap(NonNullList::stream)
                .filter(s -> !s.isEmpty())
                .filter(stack -> stack.getItem() instanceof ForceFieldGeneratorItem)
                .findFirst();
    }

    private static NonNullList<ItemStack> getCuriosForceFieldGenerator(Player player) {
        if (ModList.get().isLoaded("curios")) {
            return Curios.getForceFieldGenerators(player);
        } else {
            return NonNullList.withSize(1, ItemStack.EMPTY);
        }
    }
}
