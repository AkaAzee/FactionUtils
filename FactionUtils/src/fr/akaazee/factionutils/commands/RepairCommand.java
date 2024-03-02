package fr.akaazee.factionutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class RepairCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			ItemStack item = player.getInventory().getItemInMainHand();
			Damageable damageable = (Damageable)item.getItemMeta();
			damageable.setDamage(0);
			item.setItemMeta(damageable);
			player.getInventory().setItemInMainHand(item);
			
			player.updateInventory();
			
			return true;
		}
		
		
		
		return false;
	}

}
