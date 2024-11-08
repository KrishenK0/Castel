package fr.krishenk.castel.client.utils;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public interface IProxy {
	String localize(String unlocalized);
	String localize(String unlocalized, Object... args);
	
	default void logPhysicalSide(Logger logger) {
		logger.info("Physical Side: " + getPhysicalSide());
	}
	
	void onPreInit();
	
	void load();
	
	void registerItem(Item item);
	
	void spawnParticle(EntityPlayer player, String string, Object... ob);
	
	EntityPlayer getPlayerEntity(MessageContext ctx);
	
	Side getPhysicalSide();
}
