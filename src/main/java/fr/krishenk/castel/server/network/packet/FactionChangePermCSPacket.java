package fr.krishenk.castel.server.network.packet;

import fr.krishenk.castel.common.fperms.Access;
import fr.krishenk.castel.common.fperms.PermissableAction;
import fr.krishenk.castel.common.fperms.Rank;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FactionChangePermCSPacket {
    Rank rank;
    PermissableAction permAction;
    Boolean access;

    public FactionChangePermCSPacket(Rank rank, PermissableAction permAction, Boolean access) {
        this.rank = rank;
        this.permAction = permAction;
        this.access = access;
    }

    public static void encode(FactionChangePermCSPacket pkt, PacketBuffer buf) {
        buf.writeString("changeperm-faction");
        buf.writeInt(pkt.rank.getPriority());
        buf.writeString(pkt.permAction.toString());
        buf.writeBoolean(pkt.access);
    }

    public static FactionChangePermCSPacket decode(PacketBuffer buf) { return null; }

    public static class Handler {
        public static void handle(FactionChangePermCSPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {});
            ctx.get().setPacketHandled(true);
        }
    }
}
