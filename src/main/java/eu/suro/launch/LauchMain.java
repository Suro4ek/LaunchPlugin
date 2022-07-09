package eu.suro.launch;

import eu.suro.launch.grpc.Server;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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
        channel = ManagedChannelBuilder
                .forAddress("localhost",9000)
                .usePlaintext()
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
    }

    @Override
    public void onDisable() {
       this.server.UpdateServer(Server.Status.STOPPING);
       channel.shutdown();
    }
}
