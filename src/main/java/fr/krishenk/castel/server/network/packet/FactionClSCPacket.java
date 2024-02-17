package fr.krishenk.castel.server.network.packet;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import fr.krishenk.castel.client.gui.faction.GuiFactionClaims;
import fr.krishenk.castel.common.constants.group.Guild;
import fr.krishenk.castel.common.utils.SimpleChunkLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Set;
import java.util.function.Supplier;

public class FactionClSCPacket {
    Guild guild;
    String markersType;
    public FactionClSCPacket(Guild guild, String markersType) {
        this.guild = guild;
        this.markersType = markersType;
    }

    public static void encode(FactionClSCPacket pkt, PacketBuffer buf) { }

    public static FactionClSCPacket decode(PacketBuffer buf) {
        Guild guild = Guild.getInstance();
        guild.setLands(new Gson().fromJson(buf.readString(), new TypeToken<Set<SimpleChunkLocation>>(){}.getType()));
        String markersType = buf.readString();
        return new FactionClSCPacket(guild, markersType);
    }

    public static class Handler {
        public static void handle(FactionClSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FactionClSCPacket.Handler.handlePacket(msg, ctx)));
            ctx.get().setPacketHandled(true);
        }

        public static void handlePacket(FactionClSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> Minecraft.getInstance().displayGuiScreen(new GuiFactionClaims(msg.markersType)));
            ctx.get().setPacketHandled(true);
        }
    }
}
