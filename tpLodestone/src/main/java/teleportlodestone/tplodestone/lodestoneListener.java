package teleportlodestone.tplodestone;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class lodestoneListener implements Listener {

    private Main main;
    public lodestoneListener(Main main){ this.main = main;}

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR)){
            if(e.getHand().equals(EquipmentSlot.HAND)){
                if(player.getItemInHand().getType().equals(Material.COMPASS)){
                    ItemStack compass = player.getItemInHand();
                    CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                    if(compassMeta.hasLodestone()){
                        try{
                            Location location = compassMeta.getLodestone();
                            player.teleport(location);
                            player.sendMessage(ChatColor.YELLOW + "Player Teleported");
                        }catch (NullPointerException exception){
                            exception.printStackTrace();
                            player.sendMessage(ChatColor.RED + "Teleportation Failed");
                        }
                    }
                }
            }
        }
    }

}
