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
                    if (player.getLevel() >= 3) {
                        try {
                            player.setLevel(player.getLevel() - 3);
                            Location location = compassMeta.getLodestone();
                            player.teleport(location);
                            player.sendMessage(ChatColor.YELLOW + "Player Teleported");
                            e.setCancelled(true);
                        } catch (NullPointerException exception) {
                            exception.printStackTrace();
                            player.sendMessage(ChatColor.RED + "Teleportation Failed");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You need to pay 3 Levels to be teleported");
                    }
                }
            }
        }
    }
}
