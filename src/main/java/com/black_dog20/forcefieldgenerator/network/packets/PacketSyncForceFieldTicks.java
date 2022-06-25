package com.black_dog20.forcefieldgenerator.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PacketSyncForceFieldTicks {
    private final UUID uuid;
    private final int tick;

    public PacketSyncForceFieldTicks(UUID uuid, int tick) {
        this.uuid = uuid;
        this.tick = tick;
    }

    public static void encode(PacketSyncForceFieldTicks msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.uuid.toString());
        buffer.writeInt(msg.tick);

    }

    public static PacketSyncForceFieldTicks decode(FriendlyByteBuf buffer) {
        return new PacketSyncForceFieldTicks(UUID.fromString(buffer.readUtf()), buffer.readInt());
    }

    public static class Handler {
        public static void handle(PacketSyncForceFieldTicks msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                Player player = Minecraft.getInstance().level.getPlayerByUUID(msg.uuid);
                if (player == null)
                    return;
                player.getPersistentData().putInt("forcefieldTicks", msg.tick);
            }));

            ctx.get().setPacketHandled(true);
        }
    }
}
