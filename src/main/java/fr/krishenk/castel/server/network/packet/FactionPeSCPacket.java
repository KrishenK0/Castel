package fr.krishenk.castel.server.network.packet;

import com.google.gson.Gson;
import fr.krishenk.castel.FactionInfo;
import fr.krishenk.castel.client.gui.GuiCastel;
import fr.krishenk.castel.client.gui.faction.GuiFactionFlag;
import fr.krishenk.castel.client.gui.faction.GuiFationPerm;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FactionPeSCPacket {
    FactionInfo factionInfo;
    public FactionPeSCPacket(FactionInfo factionInfo) {
        this.factionInfo = factionInfo;
    }

    public static void encode(FactionPeSCPacket pkt, PacketBuffer buf) { }

    public static FactionPeSCPacket decode(PacketBuffer buf) {
        FactionInfo factionInfo1 = FactionInfo.getInstance();
        factionInfo1.setTitle(buf.readString());
        Map<String, Map<String, String>> map = new HashMap<>();
        factionInfo1.setPermissions(new Gson().fromJson(buf.readString(), map.getClass()));
        return new FactionPeSCPacket(factionInfo1);
    }

    public static class Handler {
        public static void handle(FactionPeSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FactionPeSCPacket.Handler.handlePacket(msg, ctx)));
            ctx.get().setPacketHandled(true);
        }

        public static void handlePacket(FactionPeSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                if (GuiFationPerm.getInstance() == null) Minecraft.getInstance().displayGuiScreen(new GuiFationPerm());
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
