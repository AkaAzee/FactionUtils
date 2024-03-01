package fr.akaazee.factionutils;

import java.util.Iterator;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.Damageable;

public class Commands implements CommandExecutor {

	private FileConfiguration config;
	
	public Commands(FactionUtils main, FileConfiguration config) {
		this.config = config;
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			switch(cmd.getName().toLowerCase()) {
			
				case"furnace":
					ItemStack[] items = player.getInventory().getContents();
					for(int i = 0; i < items.length; i++) {
						ItemStack result = null;
						Iterator<Recipe> iter = Bukkit.recipeIterator();
						while(iter.hasNext()) {
							Recipe recipe = iter.next();
							if(recipe instanceof FurnaceRecipe) {
								if(((FurnaceRecipe) recipe).getInput().isSimilar(items[i])){
									
									result = recipe.getResult();
									result.setAmount(items[i].getAmount());
	
									player.getInventory().setItem(i, result);
								}
							}
						}
					}
					
					
					
					return true;
				
				case"repair":
					
					ItemStack item = player.getInventory().getItemInMainHand();
					Damageable damageable = (Damageable)item.getItemMeta();
					damageable.setDamage(0);
					item.setItemMeta(damageable);
					player.getInventory().setItemInMainHand(item);
					
					player.updateInventory();
					
					return true;
					
				case"rtp":
					Random r = new Random();
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
		}
		
		return false;
	
    }
	
	
	
}
