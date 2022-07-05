package com.github.nulld4201.libibkk_dbapi;

import com.github.nulld4201.libibkk_dbapi.database.DBType;
import com.github.nulld4201.libibkk_dbapi.database.DatabaseSetting;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public class Main extends JavaPlugin {
    public static File configFile;
//    public static final Plugin plugin = getPlugin(Main.class);
    private DatabaseSetting dbs;

    @Override
    public void onEnable() {
        // Plugin startup logic
        configFile = new File(getPlugin(this.getClass()).getDataFolder(), "dbconfig.yml");

        if (!configFile.exists()) saveResource(configFile.getName(), false);

        this.dbs = new DatabaseSetting();
        try {
            dbs.connect(DBType.DEV);
        } catch (ClassNotFoundException | SQLException e) {
            getLogger().warning("Database not connected.");
            e.printStackTrace();
        }
        if (dbs.isConnected()) getLogger().info("Database connected.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        dbs.disconnect();
    }
}
