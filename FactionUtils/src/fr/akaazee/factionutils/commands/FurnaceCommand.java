package fr.akaazee.factionutils.commands;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class FurnaceCommand implements CommandExecutor {
	
	private HashMap<UUID, Short> utilizations;
	private LocalDate lastcheck;
	
	public FurnaceCommand() {
		this.utilizations = new HashMap<UUID, Short>();
		this.lastcheck = null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
	if(sender instanceof Player) {
		Player player = (Player)sender;	
		ItemStack[] items = player.getInventory().getContents();
		if(isNewDay()) {
			this.utilizations = new HashMap<UUID, Short>();
		}
		if(!this.utilizations.containsKey(player.getUniqueId())) {
			
			this.utilizations.put(player.getUniqueId(), (short) 4);
			this.furnace(items, player);
			player.sendMessage("§cIl vous reste §l4 utilisations§r§c de cette commande aujourd'hui");
			
		}else if (this.utilizations.get(player.getUniqueId()) > 0) {
			
			this.utilizations.replace(player.getUniqueId(), (short) (this.utilizations.get(player.getUniqueId())-1));
			this.furnace(items, player);
			player.sendMessage("§cIl vous reste §l" + this.utilizations.get(player.getUniqueId()) + " utilisations§r§c de cette commande aujourd'hui");
		}else {
			player.sendMessage("§cVous n'avez plus d'utillisations de cette commande aujourd'hui");
		}
		return true;
	}
	return false;
	}
	
	private void furnace(ItemStack[] items, Player player) {
		
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
	}
	
	public boolean isNewDay() {
	  LocalDate today = LocalDate.now();
	  boolean ret = this.lastcheck == null || today.isAfter(this.lastcheck);
	  this.lastcheck = today;
	  return ret;
	}

}
