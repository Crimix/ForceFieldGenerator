package com.black_dog20.forcefieldgenerator.items;

import com.black_dog20.forcefieldgenerator.Config;
import com.black_dog20.forcefieldgenerator.capabilities.ForceFieldCapability;
import com.black_dog20.forcefieldgenerator.utils.ModUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;

import static com.black_dog20.forcefieldgenerator.utils.Translations.INFO;

public class ForceFieldGeneratorItem extends Item {

    public ForceFieldGeneratorItem(Properties builder) {
        super(builder.durability(-1).setNoRepair().stacksTo(1));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ForceFieldCapability(stack, Config.DEFAULT_MAX_POWER.get());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(INFO.get());
        ModUtil.getFormattedEnergyStored(stack).ifPresent(tooltip::add);
        ModUtil.getFormattedEnergyCost(stack).ifPresent(tooltip::add);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY, null)
                .orElse(null);
        return (energy.getEnergyStored() < energy.getMaxEnergyStored());
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY, null)
                .orElse(null);
        if (energy == null)
            return 0;

        double widthPercentage = (1D - (energy.getEnergyStored() / (double) energy.getMaxEnergyStored()));
        return Math.round(13.0F - (float)widthPercentage * 13.0F) ;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY, null)
                .orElse(null);
        if (energy == null)
            return super.getBarColor(stack);

        return Mth.hsvToRgb(Math.max(0.0F, (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F);
    }
}
