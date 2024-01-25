package fr.krishenk.castel.server.network.packet;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import fr.krishenk.castel.FactionInfo;
import fr.krishenk.castel.client.gui.faction.GuiFaction;
import fr.krishenk.castel.common.fperms.Rank;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.*;
import java.util.function.Supplier;

public class FactionMaSCPacket {
    FactionInfo factionInfo;
    public FactionMaSCPacket(FactionInfo factionInfo) {
        this.factionInfo = factionInfo;
    }

    public static void encode(FactionMaSCPacket pkt, PacketBuffer buf) { }

    public static FactionMaSCPacket decode(PacketBuffer buf) {
        FactionInfo factionInfo1 = FactionInfo.getInstance();
        factionInfo1.setTitle(buf.readString());
        factionInfo1.setPower(buf.readInt());
        factionInfo1.setPowerMax(buf.readInt());
        factionInfo1.setLeaderName(buf.readString());
        factionInfo1.setLeaderId(buf.readString());
        factionInfo1.setRanks(new Gson().fromJson(buf.readString(), new TypeToken<List<Rank>>(){}.getType()));
        factionInfo1.setPlayerOnline(new Gson().fromJson(buf.readString(), List.class));
        factionInfo1.setPlayerOffline(new Gson().fromJson(buf.readString(), List.class));
        return new FactionMaSCPacket(factionInfo1);
    }

    public static class Handler {
        public static void handle(FactionMaSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FactionMaSCPacket.Handler.handlePacket(msg, ctx)));
            ctx.get().setPacketHandled(true);
        }

        public static void handlePacket(FactionMaSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                Minecraft.getInstance().displayGuiScreen(new GuiFaction());
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
