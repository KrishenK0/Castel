package fr.krishenk.castel.server.network.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FactionFlCSPacket {
    String flag;
    public FactionFlCSPacket(String flag) {
        this.flag = flag;
    }

    public static void encode(FactionFlCSPacket pkt, PacketBuffer buf) {
        buf.writeString("update-faction");
        buf.writeString("flag");
        buf.writeString(pkt.flag);
    }

    public static FactionFlCSPacket decode(PacketBuffer buf) { return null; }

    public static class Handler {
        public static void handle(FactionFlCSPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {});
            ctx.get().setPacketHandled(true);
        }
    }
}
