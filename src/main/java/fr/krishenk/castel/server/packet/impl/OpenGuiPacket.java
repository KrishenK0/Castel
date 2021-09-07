package fr.krishenk.castel.server.packet.impl;

import java.io.IOException;

import org.apache.logging.log4j.core.AbstractServer;

import com.jcraft.jogg.Buffer;

import cpw.mods.fml.relauncher.Side;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.server.packet.AbstractMessage.AbstractServerMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.network.PacketBuffer;

public class OpenGuiPacket extends AbstractServerMessage<OpenGuiPacket> {

	private int id;
	

	public OpenGuiPacket() {
	}

	public OpenGuiPacket(int id) {
		this.id = id;
	}

	@Override
	protected void read(PacketBuffer buf) throws IOException {;
		id = buf.readInt();
	}

	@Override
	protected void write(PacketBuffer buf) throws IOException {
		buf.writeInt(id);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		player.openGui(Castel.INSTANCE, this.id, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
	}

}
