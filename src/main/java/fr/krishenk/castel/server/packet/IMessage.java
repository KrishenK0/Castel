package fr.krishenk.castel.server.packet;

import io.netty.buffer.ByteBuf;

public interface IMessage {
	public void fromBytes(ByteBuf buf);
	
	public void toBytes(ByteBuf buf);
}
