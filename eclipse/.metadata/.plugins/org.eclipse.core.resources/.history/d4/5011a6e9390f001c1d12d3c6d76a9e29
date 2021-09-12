package fr.krishenk.castel.server.packet.impl;

import java.io.IOException;
import java.util.HashMap;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import cpw.mods.fml.relauncher.Side;
import fr.krishenk.castel.server.packet.AbstractMessage.AbstractServerMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class FactionInfosPacket extends AbstractServerMessage<FactionInfosPacket> {

	private HashMap<String, Object> factionInfos;
	
	private String factionTarget;

	public FactionInfosPacket() {
	}

	public FactionInfosPacket(String factionTarget) {
		this.factionTarget = factionTarget;
	}

	@Override
	protected void read(PacketBuffer buf) throws IOException {
		this.factionInfos = (HashMap<String, Object>)(new Gson()).fromJson(buf.readStringFromBuffer(1024), (new TypeToken<HashMap<String, Object>>() {}).getType());
	}

	@Override
	protected void write(PacketBuffer buf) throws IOException {
		buf.writeStringToBuffer(factionTarget);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		System.out.println(this.factionInfos);

	}

}
