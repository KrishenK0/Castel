package fr.krishenk.castel.server.network.packet;

import com.google.gson.Gson;
import fr.krishenk.castel.client.gui.faction.GuiFactionInvite;
import fr.krishenk.castel.common.constants.group.Guild;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class FactionInSCPacket {
    Guild guild;
    private List<String> player;
    private Map<String, String> invites;

    public FactionInSCPacket(Guild guild, List<String> player, Map<String, String> invites) {
        this.guild = guild;
        this.player = player;
        this.invites = invites;
    }

    public static void encode(FactionInSCPacket pkt, PacketBuffer buf) { }

    public static FactionInSCPacket decode(PacketBuffer buf) {
        Guild guild = Guild.getInstance();
        guild.setRequiresInvite(buf.readBoolean());
        List<String> player = new Gson().fromJson(buf.readString(), List.class);
        Map<String, String> invites = new Gson().fromJson(buf.readString(), Map.class);
        return new FactionInSCPacket(guild, player, invites);
    }

    public static class Handler {
        public static void handle(FactionInSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FactionInSCPacket.Handler.handlePacket(msg, ctx)));
            ctx.get().setPacketHandled(true);
        }

        public static void handlePacket(FactionInSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                if (GuiFactionInvite.getINSTANCE() != null) {
                    GuiFactionInvite.getINSTANCE().setInvites(msg.invites);
                    GuiFactionInvite.getINSTANCE().setPlayerList(msg.player);
                } else Minecraft.getInstance().displayGuiScreen(new GuiFactionInvite(msg.player, msg.invites));
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
