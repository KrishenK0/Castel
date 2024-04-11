package fr.krishenk.castel.server.network.packet;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import fr.krishenk.castel.client.gui.faction.GuiFationPerm;
import fr.krishenk.castel.common.constants.group.Guild;
import fr.krishenk.castel.common.fperms.Rank;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class FactionPeSCPacket {
    Guild guild;
    public FactionPeSCPacket(Guild guild) {
        this.guild = guild;
    }

    public static void encode(FactionPeSCPacket pkt, PacketBuffer buf) { }

    public static FactionPeSCPacket decode(PacketBuffer buf) {
        Guild guild = Guild.getInstance();
        guild.getGroup().setName(buf.readString());
        guild.setRanks(new Gson().fromJson(buf.readString(), new TypeToken<List<Rank>>(){}.getType()));
        return new FactionPeSCPacket(guild);
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
