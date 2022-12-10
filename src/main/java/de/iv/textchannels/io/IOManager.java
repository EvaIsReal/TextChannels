package de.iv.textchannels.io;

import de.iv.iutils.files.ConfigurationFile;
import de.iv.iutils.files.FileManager;
import de.iv.textchannels.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class IOManager {

    private static ArrayList<Config> configs = new ArrayList<>();

    public static void registerConfigs() {
        //add configs to list
        configs.add(new Config("text_channels.yml", Main.get().getDataFolder()));
        configs.add(new Config("settings.yml", Main.get().getDataFolder()));
    }

    public static void init() {
        if(!Main.get().getDataFolder().exists()) {
            Main.get().getDataFolder().mkdir();
        }
        registerConfigs();
    }

    public static void save(String fileName) {
        FileConfiguration cfg;
        for(File file : Arrays.stream(Main.get().getDataFolder().listFiles()).toList()) {
            if(file.getName().equals(fileName)) {
                cfg = YamlConfiguration.loadConfiguration(file);
                try {
                    cfg.save(file);
                    System.out.println("SAVED CONFIG " + file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Config getConfig(String name) {
        return configs.stream().filter(c -> c.getName().equals(name)).toList().get(0);
    }


    public static ArrayList<Config> getConfigs() {
        return configs;
    }

}

