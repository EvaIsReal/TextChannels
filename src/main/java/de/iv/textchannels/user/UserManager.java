package de.iv.textchannels.user;

import de.iv.ILib;
import de.iv.iutils.files.FileManager;
import de.iv.textchannels.Main;
import de.iv.textchannels.TextChannel;
import de.iv.textchannels.io.IOManager;
import de.iv.textchannels.sql.SQL;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;

public class UserManager {

    private static ArrayList<TextChannel> textChannels = new ArrayList<>();
    private static FileConfiguration cfg = IOManager.getConfig("settings.yml").getCfg();

    public static void init() {
        textChannels.add(new TextChannel(0, "Global", "", "", cfg.getString("global_tc_prefix"), Material.GRASS_BLOCK, "The default Text-Channel for everyone."));
        textChannels.add(new TextChannel(1, "test", "sys.test", "", "&7[&cT&7]", Material.COMMAND_BLOCK, "This is for testing..."));

        //get custom TCs from separate file
        compileTextChannels();

        for (TextChannel textChannel : textChannels) {
            try {

                PreparedStatement ps = SQL.getConnection().prepareStatement("INSERT INTO text_channels (id,name,password,perm,prefix,icon,description) " +
                        "VALUES (?,?,?,?,?,?,?)");

                ps.setInt(1, textChannel.getId());
                ps.setString(2, textChannel.getName());
                ps.setString(3, textChannel.getPassword());
                ps.setString(4, textChannel.getPerm());
                ps.setString(5, textChannel.getPrefix());
                ps.setString(6, textChannel.getIcon().toString().toUpperCase());
                ps.setString(7, textChannel.getDescription());
                ps.executeUpdate();

                Main.get().getLogger().log(Level.FINE, "CREATED TC_" + textChannel.getName().toUpperCase());

                /*if (SQL.getConnection().createStatement().executeQuery("SELECT name FROM text_channels WHERE id =" + textChannel.getId()).next()) {
                    PreparedStatement ps = SQL.getConnection().prepareStatement("UPDATE text_channels SET id = ?, name = ?, password = ?, perm = ?, prefix = ?, icon = ?, description = ?");
                    ps.setInt(1, textChannel.getId());
                    ps.setString(2, textChannel.getName());
                    ps.setString(3, textChannel.getPassword());
                    ps.setString(4, textChannel.getPerm());
                    ps.setString(5, textChannel.getPrefix());
                    ps.setString(6, textChannel.getIcon().toString().toUpperCase());
                    ps.setString(7, textChannel.getDescription());
                    ps.executeUpdate();
                } else {
                    PreparedStatement ps = SQL.getConnection().prepareStatement("INSERT INTO text_channels (id,name,password,perm,prefix,icon,description) VALUES (?,?,?,?,?,?,?)");
                    ps.setInt(1, textChannel.getId());
                    ps.setString(2, textChannel.getName());
                    ps.setString(3, textChannel.getPassword());
                    ps.setString(4, textChannel.getPerm());
                    ps.setString(5, textChannel.getPrefix());
                    ps.setString(6, textChannel.getIcon().toString().toUpperCase());
                    ps.setString(7, textChannel.getDescription());
                    ps.executeUpdate();
                }*/
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public static void createUserData(String uuid) {
        //CREATE NEW USER ENTRY WITH INITIAL VALUES
        try {
            SQL.getConnection().createStatement().executeUpdate("INSERT INTO users (uuid,sel_tc) VALUES ('" + uuid + "', '" + 0 + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean exists(String uuid) {
        try {
            return SQL.getConnection().createStatement().executeQuery("SELECT * FROM users WHERE uuid = '"+uuid+"'").next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static TextChannel getSelectedTextChannel(String uuid) {
        try {
            PreparedStatement ps = SQL.getConnection().prepareStatement("SELECT sel_tc FROM users WHERE uuid=?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return textChannels.get(rs.getInt("sel_tc"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void selectTextChannel(String uuid, int textChannel) {
        //UPDATE COLUMN 'sel_tc' WHERE UUID = uuid
        try {
            PreparedStatement ps = SQL.getConnection().prepareStatement("UPDATE users SET sel_tc=? WHERE uuid=?");
            ps.setInt(1, textChannel);
            ps.setString(2, uuid);
            ps.executeUpdate();
            System.out.println("selected text channel " + textChannel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void compileTextChannels() {
        FileConfiguration cfg = IOManager.getConfig("text_channels.yml").getCfg();
        for (String e : getYamlTextChannels()) {
            if(cfg.getConfigurationSection("Text-Channels." + e) != null) {
                String key = "Text-Channels." + e;

                try {
                    TextChannel tc = new TextChannel(cfg.getInt(key + ".id"), cfg.getString(key + ".name"), cfg.getString(key + ".perm"), cfg.getString(key + ".password"),
                            cfg.getString(key + ".prefix"), Material.valueOf(Objects.requireNonNull(cfg.getString(key + ".icon")).toUpperCase()),
                            cfg.getString(key + ".description"));
                    textChannels.add(tc);
                } catch (NumberFormatException ex) {
                    Main.get().getLogger().log(Level.SEVERE, "There was an error compiling the text_channels.yml structure.");
                    ex.printStackTrace();
                }
            }
        }
    }

    private static List<String> getYamlTextChannels() {
        return IOManager.getConfig("text_channels.yml").getCfg().getConfigurationSection("Text-Channels").getKeys(false).stream().toList();
    }


    public static void deleteUserData(String uuid) {
        //DELETE ROW FROM DB
        try {
            SQL.getConnection().createStatement().executeUpdate("DELETE FROM users WHERE uuid = '"+uuid+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<TextChannel> getTextChannels() {
        return textChannels;
    }
}
