package fr.krishenk.castel.server.packet.impl;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fr.krishenk.castel.client.gui.faction.GUIFactionMain;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;

public class GenericMessage implements IMessage {
	public String text;

	public GenericMessage() {
		System.out.println("Incoming message");
	}

	public GenericMessage(String s) {
		this.text = s;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		System.out.println(ByteBufUtils.getContentDump(buf));
		text = ByteBufUtils.readUTF8String(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		// Converts the message from the outgoing constructor to bytes for sending.
		ByteBufUtils.writeUTF8String(buf, text);
	}

	public String getMessage() {
		return text;
	}
}