package fr.krishenk.castel.server.network;

import fr.krishenk.castel.Castel;
import fr.krishenk.castel.server.network.packet.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
    public static final String NETWORK_VERSION = "0.1.0";
    private static int packetId = 0;

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Castel.MODID, "main"),
            () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION),
            version -> version.equals(NETWORK_VERSION)
    );

    public static void init() {
        CHANNEL.registerMessage(packetId++, FactionGuiCSPacket.class, FactionGuiCSPacket::encode, FactionGuiCSPacket::decode, FactionGuiCSPacket.Handler::handle);
        CHANNEL.registerMessage(packetId++, FactionMaSCPacket.class, FactionMaSCPacket::encode, FactionMaSCPacket::decode, FactionMaSCPacket.Handler::handle);
        CHANNEL.registerMessage(packetId++, FactionBaSCPacket.class, FactionBaSCPacket::encode, FactionBaSCPacket::decode, FactionBaSCPacket.Handler::handle);
        CHANNEL.registerMessage(packetId++, FactionFlSCPacket.class, FactionFlSCPacket::encode, FactionFlSCPacket::decode, FactionFlSCPacket.Handler::handle);
        CHANNEL.registerMessage(packetId++, FactionPeSCPacket.class, FactionPeSCPacket::encode, FactionPeSCPacket::decode, FactionPeSCPacket.Handler::handle);
        CHANNEL.registerMessage(packetId++, FactionPeCSPacket.class, FactionPeCSPacket::encode, FactionPeCSPacket::decode, FactionPeCSPacket.Handler::handle);
        CHANNEL.registerMessage(packetId++, FactionFlCSPacket.class, FactionFlCSPacket::encode, FactionFlCSPacket::decode, FactionFlCSPacket.Handler::handle);
        CHANNEL.registerMessage(packetId++, FactionInSCPacket.class, FactionInSCPacket::encode, FactionInSCPacket::decode, FactionInSCPacket.Handler::handle);
    }
}
