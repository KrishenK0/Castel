package fr.krishenk.castel.server.network.packet;

import com.google.gson.Gson;
import fr.krishenk.castel.FactionInfo;
import fr.krishenk.castel.client.gui.faction.GuiFaction;
import fr.krishenk.castel.client.gui.faction.GuiFactionBank;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class FactionBaSCPacket {
    FactionInfo factionInfo;
    public FactionBaSCPacket(FactionInfo factionInfo) {
        this.factionInfo = factionInfo;
    }

    public static void encode(FactionBaSCPacket pkt, PacketBuffer buf) { }

    public static FactionBaSCPacket decode(PacketBuffer buf) {
        FactionInfo factionInfo1 = FactionInfo.getInstance();
        factionInfo1.setPlayerOnline(new Gson().fromJson(buf.readString(), List.class));
        factionInfo1.setPlayerOffline(new Gson().fromJson(buf.readString(), List.class));
        return new FactionBaSCPacket(factionInfo1);
    }

    public static class Handler {
        public static void handle(FactionBaSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FactionBaSCPacket.Handler.handlePacket(msg, ctx)));
            ctx.get().setPacketHandled(true);
        }

        public static void handlePacket(FactionBaSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                Minecraft.getInstance().displayGuiScreen(new GuiFactionBank());
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
