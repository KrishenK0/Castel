package fr.krishenk.castel.server.network.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FactionRiCSPacket {
    boolean value;

    public FactionRiCSPacket(boolean value) {
        this.value = value;
    }

    public static void encode(FactionRiCSPacket pkt, PacketBuffer buf) {
        buf.writeString("update-faction");
        buf.writeString("require-invite");
        buf.writeBoolean(pkt.value);
    }

    public static FactionRiCSPacket decode(PacketBuffer buf) { return null; }

    public static class Handler {
        public static void handle(FactionRiCSPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {});
            ctx.get().setPacketHandled(true);
        }
    }
}
