package com.black_dog20.forcefieldgenerator.compat;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;

public class Curios {

    public static NonNullList<ItemStack> getForceFieldGenerators(Player player) {
        return CuriosApi.getCuriosHelper().getCuriosHandler(player)
                .map(c -> c.getStacksHandler("forcefield_generator"))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ICurioStacksHandler::getStacks)
                .map(Curios::getAllFrom)
                .orElse(NonNullList.withSize(1, ItemStack.EMPTY));
    }

    private static NonNullList<ItemStack> getAllFrom(IDynamicStackHandler stackHandler) {
        NonNullList<ItemStack> result = NonNullList.withSize(stackHandler.getSlots(), ItemStack.EMPTY);

        for (int i = 0; i < stackHandler.getSlots(); i++) {
            result.set(i, stackHandler.getStackInSlot(i));
        }

        return result;
    }
}
