package de.iv.textchannels;

import de.iv.ILib;
import de.iv.iutils.items.ItemBuilder;
import de.iv.iutils.menus.MenuManager;
import de.iv.textchannels.commands.CommandManager;
import de.iv.textchannels.io.IOManager;
import de.iv.textchannels.listeners.JoinListener;
import de.iv.textchannels.sql.SQL;
import de.iv.textchannels.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.SQLException;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        IOManager.init();
        ItemBuilder.setup(instance);
        MenuManager.setup(getServer(), instance);
        CommandManager commandManager = new CommandManager(instance);

        getLogger().info("TRY CONNECTING TO DATABASE...");
        try {
            if(SQL.connect()) {
                getLogger().info("CONNECTED TO DATABASE!");
                SQL.init();
                UserManager.init();

                registerListeners(getServer().getPluginManager());
                Bukkit.getConsoleSender().sendMessage(ILib.color(Uni.PREFIX + "Plugin loaded successfully!"));
            } else {
                getLogger().warning("CONNECTION TO DB FAILED!");
                Bukkit.getConsoleSender().sendMessage(ILib.color(Uni.PREFIX + "&cPlugin loaded with error whilst connecting to database!"));
                Bukkit.getConsoleSender().sendMessage(ILib.color(Uni.PREFIX + "&cExpecting errors!"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void registerListeners(PluginManager pm) {
        pm.registerEvents(new MessageRouter(), instance);
        pm.registerEvents(new JoinListener(), instance);
    }

    @Override
    public void onDisable() {
        try {
            SQL.getConnection().createStatement().executeUpdate("DELETE FROM text_channels");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQL.disconnect();
        Bukkit.getConsoleSender().sendMessage(ILib.color(Uni.PREFIX + "Plugin unloaded!"));
    }

    public static Main get() {
        return instance != null ? instance : new Main();
    }
}
