package fr.akaazee.factionutils.commands;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class FurnaceCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
	if(sender instanceof Player) {
		Player player = (Player)sender;	
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
		}
	return false;
	}

}
