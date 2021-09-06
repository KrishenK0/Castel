package fr.krishenk.castel.client;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import fr.krishenk.castel.server.packet.ExtendedPlayer;
import fr.krishenk.castel.server.packet.AbstractMessage.AbstractClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class SyncPlayerPropsMessage extends AbstractClientMessage<SyncPlayerPropsMessage> {

	private NBTTagCompound data;
	
	public SyncPlayerPropsMessage() {
	}
	
	public SyncPlayerPropsMessage(EntityPlayer player) {
		data = new NBTTagCompound();
		ExtendedPlayer.get(player).saveNBTData(data);
	}
	
	@Override
	protected void read(PacketBuffer buf) throws IOException {
		data = buf.readNBTTagCompoundFromBuffer();
		
	}

	@Override
	protected void write(PacketBuffer buf) throws IOException {
		buf.writeNBTTagCompoundToBuffer(data);
		
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		ExtendedPlayer.get(player).loadNBTData(data);
	}

}
