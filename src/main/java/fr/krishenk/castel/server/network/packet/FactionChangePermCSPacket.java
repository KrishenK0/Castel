package fr.krishenk.castel.server.network.packet;

import fr.krishenk.castel.common.fperms.Access;
import fr.krishenk.castel.common.fperms.PermissableAction;
import fr.krishenk.castel.common.fperms.Role;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FactionChangePermCSPacket {
    int relation;
    Role role;
    PermissableAction permAction;
    Access access;

    public FactionChangePermCSPacket(Role role, int relation, PermissableAction permAction, Access access) {
        this.role = role;
        this.relation = relation;
        this.permAction = permAction;
        this.access = access;
    }

    public static void encode(FactionChangePermCSPacket pkt, PacketBuffer buf) {
        buf.writeString("changeperm-faction");
        buf.writeInt(pkt.role.value % 4);
        buf.writeInt(pkt.relation);
        buf.writeString(pkt.permAction.toString());
        buf.writeBoolean(pkt.access.equals(Access.ALLOW));
    }

    public static FactionChangePermCSPacket decode(PacketBuffer buf) { return null; }

    public static class Handler {
        public static void handle(FactionChangePermCSPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {});
            ctx.get().setPacketHandled(true);
        }
    }
}
