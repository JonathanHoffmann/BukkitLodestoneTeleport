package me.Jonnyfant.LodestoneTeleport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
public static final String CFG_KEY_LAVA = "TeleportIntoLava";
    @Override
    public void onEnable() {
        loadConfig();
        Bukkit.getPluginManager().registerEvents(new LodestoneListener(this), this);
    }

    private void loadConfig() {
        getConfig().addDefault(CFG_KEY_LAVA, false);
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
