package fr.krishenk.castel;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.krishenk.castel.commands.CommandTest;

public class main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		System.out.println("[CASTEL] Plugin on");
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "castel");
		Bukkit.getMessenger().registerIncomingPluginChannel(this, "castel", new MessageHandler());
		getCommand("test").setExecutor(new CommandTest());	
	}
	
	@Override
	public void onDisable() {
		System.out.println("[CASTEL] Plugin off");
	}
}
