package fr.krishenk.castel.server.network.packet;

import com.google.gson.Gson;
import fr.krishenk.castel.FactionInfo;
import fr.krishenk.castel.client.gui.faction.GuiFaction;
import fr.krishenk.castel.client.gui.faction.GuiFactionFlag;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class FactionFlSCPacket {
    FactionInfo factionInfo;
    public FactionFlSCPacket(FactionInfo factionInfo) {
        this.factionInfo = factionInfo;
    }

    public static void encode(FactionFlSCPacket pkt, PacketBuffer buf) { }

    public static FactionFlSCPacket decode(PacketBuffer buf) {
        FactionInfo factionInfo1 = FactionInfo.getInstance();
        factionInfo1.setTitle(buf.readString());
        return new FactionFlSCPacket(factionInfo1);
    }

    public static class Handler {
        public static void handle(FactionFlSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FactionFlSCPacket.Handler.handlePacket(msg, ctx)));
            ctx.get().setPacketHandled(true);
        }

        public static void handlePacket(FactionFlSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                Minecraft.getInstance().displayGuiScreen(new GuiFactionFlag());
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
