package xyz.rgnt.smp.mojang;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class DummyClientboundPacketListener extends ServerGamePacketListenerImpl {
    public DummyClientboundPacketListener(ServerPlayer player) {
        super(MinecraftServer.getServer(), new DummyClientboundConnection(), player);
    }
}
