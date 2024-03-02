package fr.akaazee.factionutils;

import org.bukkit.plugin.java.JavaPlugin;

import fr.akaazee.factionutils.commands.FurnaceCommand;
import fr.akaazee.factionutils.commands.RepairCommand;
import fr.akaazee.factionutils.commands.RtpCommand;
import fr.akaazee.factionutils.listeners.SpawnerListener;

public class FactionUtils extends JavaPlugin{
	
	@Override
	public void onEnable() {
		
		
		saveDefaultConfig();
		getCommand("rtp").setExecutor(new RtpCommand(getConfig()));
		getCommand("furnace").setExecutor(new FurnaceCommand());
		getCommand("repair").setExecutor(new RepairCommand());
		
		getServer().getPluginManager().registerEvents(new SpawnerListener(getConfig()), this);

    }
}
	
