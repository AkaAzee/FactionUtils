package fr.akaazee.factionutils;

import org.bukkit.plugin.java.JavaPlugin;

public class FactionUtils extends JavaPlugin{
	
	@Override
	public void onEnable() {
		
		
		saveDefaultConfig();
		getCommand("rtp").setExecutor(new Commands(this, getConfig()));
		getCommand("smelt").setExecutor(new Commands(this, getConfig()));
		getCommand("furnace").setExecutor(new Commands(this, getConfig()));
		getCommand("repair").setExecutor(new Commands(this, getConfig()));
		
		getServer().getPluginManager().registerEvents(new FactionListener(getConfig()), this);

    }
}
	
