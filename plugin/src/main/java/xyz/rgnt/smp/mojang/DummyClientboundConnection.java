package xyz.rgnt.smp.mojang;

import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;

public class DummyClientboundConnection extends Connection {
    public DummyClientboundConnection() {
        super(PacketFlow.CLIENTBOUND);
    }
}
