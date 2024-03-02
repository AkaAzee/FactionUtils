package fr.akaazee.factionutils.listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class SpawnerListener implements Listener{

	private double dropChance;
    private Random random = new Random();
	
	public SpawnerListener(FileConfiguration config) {
		this.dropChance = 1-config.getConfigurationSection("rate").getDouble("spawner");
		
	}

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
    	Player player = event.getPlayer();
    	Block block = event.getBlock();
        if (block.getType() == Material.SPAWNER && player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
        	if(isPickaxe(player.getInventory().getItemInMainHand())) {
	            event.setDropItems(false); 
	
	            BlockState state = block.getState();
	            if (state instanceof CreatureSpawner) {
	            	CreatureSpawner spawner = (CreatureSpawner)state;
	            	
	                
	                if(random.nextDouble(1.0) > dropChance) {
		            	ItemStack drop = new ItemStack(Material.SPAWNER);
		            	BlockStateMeta bsm = (BlockStateMeta)drop.getItemMeta();
		                bsm.setBlockState(spawner);
		                bsm.setDisplayName("§f§b" + toTitleCase(spawner.getSpawnedType().toString().toLowerCase().replace('_', ' ')) + " Spawner");
		                drop.setItemMeta(bsm);
		                
		                
		            	event.getBlock().getWorld().dropItemNaturally(block.getLocation(), drop);
	                }
	            }
                
            }
        }
    }
    
    public boolean isPickaxe(ItemStack tool) {
    	
    	switch(tool.getType().toString()) {
    	
    		case"DIAMOND_PICKAXE":
    		case"GOLDEN_PICKAXE":
    		case"IRON_PICKAXE":
    		case"NETHERITE_PICKAXE":
    		case"STONE_PICKAXE":
    		case"WOODEN_PICKAXE":
    			return true;
    	
    	}
    	
    	return false;
    }
    
    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                .append(arr[i].substring(1)).append(" ");
        }          
        return sb.toString().trim();
    }
	
    @EventHandler
    public void PlaceSpawnerEvent(BlockPlaceEvent event) {
        ItemStack itemInHand = event.getItemInHand();
        Block block = event.getBlockPlaced();
        if (itemInHand.getType() == Material.SPAWNER) {
            BlockStateMeta blockStateMeta = (BlockStateMeta) itemInHand.getItemMeta();
            CreatureSpawner creatureSpawner = (CreatureSpawner) blockStateMeta.getBlockState();
            CreatureSpawner blockCreatureSpawner = (CreatureSpawner) block.getState();
            blockCreatureSpawner.setSpawnedType(creatureSpawner.getSpawnedType());
            blockCreatureSpawner.update();
        }
    }
}
    
    
    

