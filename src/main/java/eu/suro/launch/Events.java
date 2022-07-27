package eu.suro.launch;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Locale;

public class Events implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if(Bukkit.getOnlinePlayers().size() == 0){
            LauchMain.plugin.getLogger().info("На сервере 0 игроков, завершаю работу");
            LauchMain.DeleteServer();
        }
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(p.getName().toLowerCase(Locale.ROOT).equals(Bukkit.getServerName()) && !p.isOp()){
            p.setOp(true);
            p.sendMessage("§aВы были назначены оператором");
        }
    }
}
