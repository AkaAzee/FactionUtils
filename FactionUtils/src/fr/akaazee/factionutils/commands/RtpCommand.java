package fr.akaazee.factionutils.commands;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RtpCommand implements CommandExecutor{
	
	private Random r;
	private FileConfiguration config;
	
	public RtpCommand(final FileConfiguration config) {
		this.r = new Random();
		this.config = config;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			
			int min = config.getConfigurationSection("rtp").getInt("minRange");
			int max = config.getConfigurationSection("rtp").getInt("maxRange");
			
			int x = r.nextInt(max - min) + min;
			int z = r.nextInt(max - min) + min;
			
			int y = player.getWorld().getHighestBlockYAt(x, z);
			while(y > 200) {
				x = r.nextInt(max - min) + min;
				z = r.nextInt(max - min) + min;
				y = player.getWorld().getHighestBlockYAt(x, z);
			}
			
			Location location = new Location(player.getWorld(), x, y + 2, z);
			player.teleport(location);
			
			return true;
			
		}
		
		return false;
	}
	
}
