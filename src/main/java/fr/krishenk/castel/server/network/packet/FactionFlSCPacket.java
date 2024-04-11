package fr.krishenk.castel.server.network.packet;

import fr.krishenk.castel.client.gui.faction.GuiFactionFlag;
import fr.krishenk.castel.common.constants.group.Guild;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FactionFlSCPacket {
    Guild guild;
    public FactionFlSCPacket(Guild guild) {
        this.guild = guild;
    }

    public static void encode(FactionFlSCPacket pkt, PacketBuffer buf) { }

    public static FactionFlSCPacket decode(PacketBuffer buf) {
        Guild guild = Guild.getInstance();
        guild.getGroup().setName(buf.readString());
        guild.getGroup().setFlag(buf.readString());

        return new FactionFlSCPacket(guild);
    }

    public static class Handler {
        public static void handle(FactionFlSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FactionFlSCPacket.Handler.handlePacket(msg, ctx)));
            ctx.get().setPacketHandled(true);
        }

        public static void handlePacket(FactionFlSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> Minecraft.getInstance().displayGuiScreen(new GuiFactionFlag()));
            ctx.get().setPacketHandled(true);
        }
    }
}
