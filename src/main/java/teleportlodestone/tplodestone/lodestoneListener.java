package teleportlodestone.tplodestone;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class lodestoneListener implements Listener {

    private Main main;

    public lodestoneListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        Entity entity = e.getRightClicked();
        if (entity instanceof ItemFrame) {
            ItemStack item = ((ItemFrame) entity).getItem();
            if (item.getType().equals(Material.COMPASS)) {

                CompassMeta compassMeta = (CompassMeta) item.getItemMeta();
                if (compassMeta.hasLodestone()) {
                    Location location = compassMeta.getLodestone();
                    Location locationPlus1 = location;
                    locationPlus1.setY(locationPlus1.getY() + 1);
                    Location locationPlus2 = location;
                    locationPlus2.setY(locationPlus2.getY() + 2);
                    try {
                        if (locationPlus1.getBlock().isPassable() && locationPlus2.getBlock().isPassable()) {
                            player.teleport(locationPlus1);
                            String message = "Player teleported";

                            if(compassMeta.hasDisplayName())
                            {
                                message+= " to " + compassMeta.getDisplayName();
                            }

                            player.sendMessage(ChatColor.YELLOW + message);
                            e.setCancelled(true);
                        } else {
                            player.sendMessage(ChatColor.RED + "Lodestone is obstructed");
                        }
                    } catch (NullPointerException exception) {
                        exception.printStackTrace();
                        player.sendMessage(ChatColor.RED + "Teleportation Failed");
                    }
                }
            }
        }
    }
}
