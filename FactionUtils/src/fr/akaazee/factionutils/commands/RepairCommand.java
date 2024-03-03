package fr.akaazee.factionutils.commands;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class RepairCommand implements CommandExecutor {

	private HashMap<UUID, Short> utilizations;
	private LocalDate lastcheck;
	private FileConfiguration config;
	
	public RepairCommand(FileConfiguration config) {
		this.utilizations = new HashMap<UUID, Short>();
		this.lastcheck = null;
		this.config = config;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(isNewDay()) {
				this.utilizations = new HashMap<UUID, Short>();
			}
			if(!this.utilizations.containsKey(player.getUniqueId())) {
				
				this.utilizations.put(player.getUniqueId(), (short) config.getConfigurationSection("cooldowns").getInt("repair"));
				this.repair(player);
				player.sendMessage("§cIl vous reste §l" + this.utilizations.get(player.getUniqueId()) + " utilisations§r§c de cette commande aujourd'hui");
				
			}else if(this.utilizations.get(player.getUniqueId()) > 0) {
				this.utilizations.replace(player.getUniqueId(), (short) (this.utilizations.get(player.getUniqueId())-1));
				this.repair(player);
				player.sendMessage("§cIl vous reste §l" + this.utilizations.get(player.getUniqueId()) + " utilisations§r§c de cette commande aujourd'hui");
			}else {
				player.sendMessage("§cVous n'avez plus d'utillisations de cette commande aujourd'hui");
			}
		
			return true;
		}
		
		return false;
	}
	
	
	public void repair(Player player) {
		ItemStack item = player.getInventory().getItemInMainHand();
		Damageable damageable = (Damageable)item.getItemMeta();
		damageable.setDamage(0);
		item.setItemMeta(damageable);
		player.getInventory().setItemInMainHand(item);
		
		player.updateInventory();
	}
	
	public boolean isNewDay() {
		  LocalDate today = LocalDate.now();
		  boolean ret = this.lastcheck == null || today.isAfter(this.lastcheck);
		  this.lastcheck = today;
		  return ret;
		}
}
