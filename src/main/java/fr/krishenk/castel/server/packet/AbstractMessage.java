package fr.krishenk.castel.server.packet;

import java.io.IOException;

import com.google.common.base.Throwables;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import fr.krishenk.castel.Castel;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public abstract class AbstractMessage<T extends AbstractMessage<T>> implements IMessage, IMessageHandler<T, IMessage> {
	protected abstract void read(PacketBuffer buf) throws IOException;
	
	protected abstract void write(PacketBuffer buf) throws IOException;
	
	public abstract void process(EntityPlayer player, Side side);
	
	protected boolean isValidOnSide(Side side) {
		return true;
	}
	
	protected boolean requiresMainThread() {
		return true;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			read(new PacketBuffer(buf));
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		try {
			write(new PacketBuffer(buf));
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}
	
	@Override
	public final IMessage onMessage(T msg, MessageContext ctx) {
		if (!msg.isValidOnSide(ctx.side)) {
			throw new RuntimeException("Invalid side " + ctx.side.name() + " for " + msg.getClass().getSimpleName());
		} else {
			msg.process(Castel.proxy.getPlayerEntity(ctx), ctx.side);
		}		
		return null;
	}

	/**
	 * Messages that can only be sent from the server to the client should use this class
	 */
	public static abstract class AbstractClientMessage<T extends AbstractMessage<T>> extends AbstractMessage<T> {
		@Override
		protected final boolean isValidOnSide(Side side) {
			return side.isClient();
		}
	}

	/**
	 * Messages that can only be sent from the client to the server should use this class
	 */
	public static abstract class AbstractServerMessage<T extends AbstractMessage<T>> extends AbstractMessage<T> {
		@Override
		protected final boolean isValidOnSide(Side side) {
			return side.isServer();
		}
	}

}
