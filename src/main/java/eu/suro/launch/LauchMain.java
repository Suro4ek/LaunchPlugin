package eu.suro.launch;

import com.google.common.cache.LoadingCache;
import jdk.jfr.internal.tool.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class LauchMain extends JavaPlugin {

    static JavaPlugin plugin;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new Events(), this);
    }
}
