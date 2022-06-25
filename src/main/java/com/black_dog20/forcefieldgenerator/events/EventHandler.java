package com.black_dog20.forcefieldgenerator.events;

import com.black_dog20.forcefieldgenerator.ForceFieldGenerator;
import com.black_dog20.forcefieldgenerator.network.PacketHandler;
import com.black_dog20.forcefieldgenerator.network.packets.PacketSyncForceFieldTicks;
import com.black_dog20.forcefieldgenerator.utils.ModUtil;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.Optional;
import java.util.Set;

@Mod.EventBusSubscriber(modid = ForceFieldGenerator.MOD_ID)
public class EventHandler {

    private static final Set<DamageSource> IGNORED_SOURCES = ImmutableSet.of(DamageSource.OUT_OF_WORLD, DamageSource.DROWN,  DamageSource.IN_WALL);

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerKnockBack(LivingKnockBackEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            if (player.level.isClientSide)
                return;
            if (player.isCreative())
                return;

            Optional<ItemStack> optionalStack = ModUtil.findForceFieldGenerator(player);
            Optional<IEnergyStorage> optionalEnergy = optionalStack.flatMap(stack -> stack.getCapability(CapabilityEnergy.ENERGY, null).resolve());

            if (optionalStack.isPresent() && optionalEnergy.isPresent()) {
                ItemStack stack = optionalStack.get();
                IEnergyStorage energy = optionalEnergy.get();
                int cost = ModUtil.getEnergyCost(stack);

                if (energy.getEnergyStored() + cost >= 0) {
                    event.setCanceled(true);
                }

            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerAttack(LivingAttackEvent event) {
        if (event.getAmount() > 0 && event.getEntityLiving() instanceof Player player) {
            if (player.level.isClientSide)
                return;
            if (player.isCreative())
                return;

            if (IGNORED_SOURCES.contains(event.getSource()))
                return;

            Optional<ItemStack> optionalStack = ModUtil.findForceFieldGenerator(player);
            Optional<IEnergyStorage> optionalEnergy = optionalStack.flatMap(stack -> stack.getCapability(CapabilityEnergy.ENERGY, null).resolve());

            if (optionalStack.isPresent() && optionalEnergy.isPresent()) {
                ItemStack stack = optionalStack.get();
                IEnergyStorage energy = optionalEnergy.get();
                int cost = ModUtil.getEnergyCost(stack);

                if (energy.getEnergyStored() - cost >= 0) {
                    if (player.getPersistentData().getInt("forcefieldCooldownTicks") == 0) {
                        energy.extractEnergy(cost, false);
                        player.getPersistentData().putInt("forcefieldTicks", 80);
                        player.getPersistentData().putInt("forcefieldCooldownTicks", 20);
                        PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncForceFieldTicks(player.getUUID(), player.getPersistentData().getInt("forcefieldTicks")));
                    }
                    event.setCanceled(true);
                }

            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdatePlayer(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof Player player && !event.getEntity().level.isClientSide) {
            if (player.getPersistentData().contains("forcefieldTicks")) {
                int ticks = player.getPersistentData().getInt("forcefieldTicks");
                if (ticks > 0) {
                    ticks--;
                    player.getPersistentData().putInt("forcefieldTicks", ticks);
                    PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncForceFieldTicks(player.getUUID(), ticks));
                } else {
                    player.getPersistentData().remove("forcefieldTicks");
                }
            }
            if (player.getPersistentData().contains("forcefieldCooldownTicks")) {
                int ticks = player.getPersistentData().getInt("forcefieldCooldownTicks");
                if (ticks > 0) {
                    ticks--;
                    player.getPersistentData().putInt("forcefieldCooldownTicks", ticks);
                } else {
                    player.getPersistentData().remove("forcefieldCooldownTicks");
                }
            }
        }
    }
}
