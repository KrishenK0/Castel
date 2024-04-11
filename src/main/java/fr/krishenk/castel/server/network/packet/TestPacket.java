package fr.krishenk.castel.server.network.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TestPacket {
    public final String factionId;

    public TestPacket(String factionId) { this.factionId = factionId; }

    public static void encode(TestPacket pkt, PacketBuffer buf) { }

    public static TestPacket decode(PacketBuffer buf) {
        //System.out.println(buf.readByte());
        return new TestPacket(buf.readString());
    }

    public static class Handler {
        public static void handle(TestPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                    // Make sure it's only executed on the physical client
                    System.out.println("RECEIVE PACKET (SERVER)" + msg.factionId);
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> Handler.handlePacket(msg, ctx));
                }
            );
            ctx.get().setPacketHandled(true);
        }

        // In ClientPacketHandlerClass
        public static void handlePacket(TestPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                System.out.println("RECEIVE PACKET");
                //Minecraft.getInstance().displayGuiScreen(new GuiFaction());
                }
            );
            ctx.get().setPacketHandled(true);
        }
    }
}
