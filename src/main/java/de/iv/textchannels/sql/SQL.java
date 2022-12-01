package de.iv.textchannels.sql;

import de.iv.textchannels.Main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQL {

    private static Connection con;

    public static boolean connect() throws IOException {
        con = null;
        File db = new File(Main.get().getDataFolder() + "/data/database.db");
        if(!db.exists()) {
            db.getParentFile().mkdirs();
            db.createNewFile();
        }
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + db.getPath());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean disconnect() {
        if(con == null) return false;
        try {
            con.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void init() {
        try {
            PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS users (uuid VARCHAR(255), sel_tc INTEGER)");
            PreparedStatement ps2 = con.prepareStatement("CREATE TABLE IF NOT EXISTS text_channels " +
                    "(id INTEGER, name VARCHAR(127), password VARCHAR(127), perm VARCHAR(63), prefix VARCHAR(63), icon VARCHAR(127), description TEXT)");
            ps.executeUpdate();
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return con;
    }
}
