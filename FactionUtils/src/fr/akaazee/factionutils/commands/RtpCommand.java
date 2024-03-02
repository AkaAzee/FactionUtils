package fr.akaazee.factionutils.commands;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RtpCommand implements CommandExecutor{
	
	private Random r;
	private FileConfiguration config;
	private final HashMap<UUID, Long> cooldown;
	
	public RtpCommand(final FileConfiguration config) {
		this.r = new Random();
		this.config = config;
		this.cooldown = new HashMap<UUID, Long>();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			
			if(!this.cooldown.containsKey(player.getUniqueId())) {
				this.teleportation(player);
			}else {
				
				long timeElapsed = System.currentTimeMillis() - cooldown.get(player.getUniqueId());
				int cd;
				
				if(player.hasPermission("factionutils.rtp.maitre")) {
					cd = config.getConfigurationSection("cooldowns").getInt("maitre")*1000;
				}else if(player.hasPermission("factionutils.rtp.noble")) {
					cd = config.getConfigurationSection("cooldowns").getInt("noble")*1000;
				}else if(player.hasPermission("factionutils.rtp.citoyen")) {
					cd = config.getConfigurationSection("cooldowns").getInt("citoyen")*1000;
				}else {
					return false;
				}
				
				if(timeElapsed >= cd) {
					
					this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
					
					this.teleportation(player);
				}else {
					player.sendMessage("§cVous devez attendre §l" + (((int)(cd - timeElapsed)/1000) + 1 )+ " secondes§r§c avant de réutiliser cette commmande");
				}
				
			}
			return true;
			
		}
		return false;
	}

	private void teleportation(Player player) {
		this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
		
		
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
	
	}
}
