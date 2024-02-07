package fr.krishenk.castel.server.network.packet;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import fr.krishenk.castel.client.gui.faction.GuiFactionBank;
import fr.krishenk.castel.common.constants.group.Guild;
import fr.krishenk.castel.common.constants.group.models.BankLog;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class FactionBaSCPacket {
    Guild guild;
    List<BankLog> logs;

    public FactionBaSCPacket(Guild guild, List<BankLog> logs) {
        this.guild = guild;
        this.logs = logs;
    }

    public static void encode(FactionBaSCPacket pkt, PacketBuffer buf) { }

    public static FactionBaSCPacket decode(PacketBuffer buf) {
        Guild guild = Guild.getInstance();
        guild.getGroup().setMembersOnline(new Gson().fromJson(buf.readString(), List.class));
        guild.getGroup().setMembersOffline(new Gson().fromJson(buf.readString(), List.class));
        List<BankLog> logs = new Gson().fromJson(buf.readString(), new TypeToken<List<BankLog>>(){}.getType());
        return new FactionBaSCPacket(guild, logs);
    }

    public static class Handler {
        public static void handle(FactionBaSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FactionBaSCPacket.Handler.handlePacket(msg, ctx)));
            ctx.get().setPacketHandled(true);
        }

        public static void handlePacket(FactionBaSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> Minecraft.getInstance().displayGuiScreen(new GuiFactionBank(msg.logs)));
            ctx.get().setPacketHandled(true);
        }
    }
}
