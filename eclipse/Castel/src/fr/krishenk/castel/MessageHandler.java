package fr.krishenk.castel;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class MessageHandler implements PluginMessageListener {
	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
		System.out.println(arg0);
		System.out.println(arg1);
		System.out.println(arg2);
		
	}
}
