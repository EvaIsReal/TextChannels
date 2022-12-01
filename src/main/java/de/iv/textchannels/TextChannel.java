package de.iv.textchannels;

import org.bukkit.Material;

public class TextChannel {

    private int id;
    private String name, perm, password, prefix, description;
    private Material icon;

    public TextChannel(int id, String name, String perm, String password, String prefix, Material icon, String description) {
        this.id = id;
        this.name = name;
        this.perm = perm;
        this.password = password;
        this.prefix = prefix;
        this.description = description;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public Material getIcon() {
        return icon;
    }

    public boolean hasPassword() {
        return password.toCharArray().length != 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPerm() {
        return perm;
    }

    public String getPassword() {
        return password;
    }

    public String getPrefix() {
        return prefix;
    }
}
