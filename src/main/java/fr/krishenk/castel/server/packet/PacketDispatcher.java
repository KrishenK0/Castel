package fr.krishenk.castel.server.packet;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.server.packet.impl.FactionInfosPacket;
import fr.krishenk.castel.server.packet.impl.OpenGuiPacket;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketDispatcher {
	
	private static byte packetId = 0;
	
	private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(Castel.MODID);
	
	public static final void registerPackets() {
		registerMessage(FactionInfosPacket.class);
		registerMessage(OpenGuiPacket.class);
	}
	
	public static void sendTo(IMessage message, EntityPlayerMP player) {
		PacketDispatcher.dispatcher.sendTo(message, player);
	}
	
	public static void sendToAll(IMessage message) {
		PacketDispatcher.dispatcher.sendToAll(message);
	}
	
	public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
		 PacketDispatcher.dispatcher.sendToAllAround(message, point);
	}
	
	public static final void sendToDimension(IMessage message, int dimensionId) {
		PacketDispatcher.dispatcher.sendToDimension(message, dimensionId);
	}
	
	public static final void sendToServer(IMessage message) {
		PacketDispatcher.dispatcher.sendToServer(message);
	}
	
	private static final <T extends AbstractMessage<T> & IMessageHandler<T, IMessage>> void registerMessage(Class<T> cls) {
		if(AbstractMessage.AbstractClientMessage.class.isAssignableFrom(cls)) {
			PacketDispatcher.dispatcher.registerMessage(cls, cls, packetId++, Side.CLIENT);
		} else if (AbstractMessage.AbstractServerMessage.class.isAssignableFrom(cls)) {
			PacketDispatcher.dispatcher.registerMessage(cls, cls, packetId++, Side.SERVER);
		} else {
			PacketDispatcher.dispatcher.registerMessage(cls, cls, packetId++, Side.CLIENT);
			PacketDispatcher.dispatcher.registerMessage(cls, cls, packetId++, Side.SERVER);
		}
	}
}
