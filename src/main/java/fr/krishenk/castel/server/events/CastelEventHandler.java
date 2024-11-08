package fr.krishenk.castel.server.events;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.client.KeyHandler;
import fr.krishenk.castel.client.SyncPlayerPropsMessage;
import fr.krishenk.castel.server.packet.ExtendedPlayer;
import fr.krishenk.castel.server.packet.PacketDispatcher;
import fr.krishenk.castel.server.packet.impl.OpenGuiPacket;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CastelEventHandler {

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			if (ExtendedPlayer.get((EntityPlayer) event.entity) == null) {
				ExtendedPlayer.register((EntityPlayer) event.entity);
			}
		}
	}

	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayerMP) {
			PacketDispatcher.sendTo(new SyncPlayerPropsMessage((EntityPlayer) event.entity),
					(EntityPlayerMP) event.entity);
		}
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		ExtendedPlayer.get(event.entityPlayer).copy(ExtendedPlayer.get(event.original));
	}
}
