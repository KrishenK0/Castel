package fr.krishenk.castel.server.network.packet;

import fr.krishenk.castel.client.gui.faction.FactionTab;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FactionGuiCSPacket {
    private final FactionTab.Tab guiName;
    public FactionGuiCSPacket(FactionTab.Tab guiName) { this.guiName = guiName; }

    public static void encode(FactionGuiCSPacket pkt, PacketBuffer buf) {
        buf.writeString("opengui-faction");
        buf.writeString(pkt.guiName.toString());
    }

    public static FactionGuiCSPacket decode(PacketBuffer buf) { return null; }

    public static class Handler {
        public static void handle(FactionGuiCSPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {});
            ctx.get().setPacketHandled(true);
        }
    }
}
