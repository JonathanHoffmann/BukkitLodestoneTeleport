package me.Jonnyfant.LodestoneTeleport;

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

public class LodestoneListener implements Listener {
    Main plugin;

    public LodestoneListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public boolean onPlayerInteract(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        Entity entity = e.getRightClicked();
        if (!(entity instanceof ItemFrame)) {
            return false;
        }
        ItemStack compass = ((ItemFrame) entity).getItem();
        if (!compass.getType().equals(Material.COMPASS)) {
            return false;
        }
        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        if (!compassMeta.hasLodestone()) {
            return false;
        }
        e.setCancelled(true);

        //init Locations
        Location location = compassMeta.getLodestone();
        Location locationPlus1 = location.clone();
        locationPlus1.setY(locationPlus1.getY() + 1);
        locationPlus1.setX(locationPlus1.getX() + 0.5);
        locationPlus1.setZ(locationPlus1.getZ() + 0.5);
        Location locationPlus2 = locationPlus1.clone();
        locationPlus2.setY(locationPlus2.getY() + 1);
        if (!location.getBlock().getType().equals(Material.LODESTONE)) {
            player.sendMessage(ChatColor.RED + "No lodestone at that location. Was it maybe moved?");
            return false;
        }
        if (!plugin.getConfig().getBoolean(plugin.CFG_KEY_LAVA) &&
                (locationPlus1.getBlock().getType().equals(Material.LAVA) ||
                        locationPlus2.getBlock().getType().equals(Material.LAVA))) {
            player.sendMessage(ChatColor.RED + "The spawning space is obstructed by lava.");
            return false;
        }
        if (!(locationPlus1.getBlock().isPassable() && locationPlus2.getBlock().isPassable())) {
            player.sendMessage(ChatColor.RED + "Lodestone is obstructed");
            return false;
        }

        player.teleport(locationPlus1);
        String message = "Player teleported";

        if (compassMeta.hasDisplayName()) {
            message += " to " + compassMeta.getDisplayName();
        }
        player.sendMessage(ChatColor.YELLOW + message);
        return true;
    }
}
