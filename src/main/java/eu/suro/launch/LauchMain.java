package eu.suro.launch;

import com.google.common.cache.LoadingCache;
import eu.suro.launch.grpc.Server;
import io.grpc.*;
import jdk.jfr.internal.tool.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import server.ServerGrpc;

public final class LauchMain extends JavaPlugin {

    static Server server;
    static JavaPlugin plugin;
    static ManagedChannel channel;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ChannelCredentials credentials = InsecureChannelCredentials.create();
        channel = Grpc.newChannelBuilder(getConfig().getString("grpc.host")+":"+getConfig().getString("grpc.host"),
                        credentials)
                .build();
        ServerGrpc.ServerStub stub = ServerGrpc.newStub(channel);
        Server server = new Server(stub);
        this.server = server;
        this.plugin = this;
        new BukkitRunnable() {
            @Override
            public void run() {
                if(plugin.isEnabled()){
                    server.UpdateServer(Server.Status.RUNNING);
                }
            }
        }.runTaskTimerAsynchronously(this, 10L, 20*30);
        DeleteServer();
        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    public static void DeleteServer(){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(Bukkit.getOnlinePlayers().size() == 0){
                    Bukkit.shutdown();
                }
            }
        }.runTaskLaterAsynchronously(plugin, 20*30);
    }

    @Override
    public void onDisable() {
       server.DeleteServer();
//       this.server.UpdateServer(Server.Status.STOPPING);
       channel.shutdown();
    }
}
