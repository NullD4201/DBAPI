package com.github.nulld4201.libibkk_dbapi;

import com.github.nulld4201.libibkk_dbapi.database.DBType;
import com.github.nulld4201.libibkk_dbapi.database.DatabaseSetting;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Main extends JavaPlugin {
//    public static final Plugin plugin = getPlugin(Main.class);
    private DatabaseSetting dbs;

    @Override
    public void onEnable() {
        // Plugin startup logic
        File configFile = new File("./plugins/Libibkk_DBAPI", "dbconfig.yml");
        YamlConfiguration configYml = YamlConfiguration.loadConfiguration(configFile);

        if (!configFile.exists()) {
            try {
                configYml.save(configFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.dbs = new DatabaseSetting();
        try {
            dbs.connect();
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
