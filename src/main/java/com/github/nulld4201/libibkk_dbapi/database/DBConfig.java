package com.github.nulld4201.libibkk_dbapi.database;

import com.github.nulld4201.libibkk_dbapi.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class DBConfig{
    private static final File configFile = new File("./plugins/Libibkk_DBAPI", "dbconfig.yml");
    public static final YamlConfiguration configYaml = YamlConfiguration.loadConfiguration(configFile);

    public static boolean isDevMode() {
        return configYaml.getBoolean("Dev_Mode");
    }

    public static String getServerHost() {
        String typeName = "Release";
        if (isDevMode()) typeName = "Dev";
        return configYaml.getString(typeName + ".host");
    }

    public static int getServerPort() {
        String typeName = "Release";
        if (isDevMode()) typeName = "Dev";
        return configYaml.getInt(typeName + ".port");
    }

    public static String getServerDB() {
        String typeName = "Release";
        if (isDevMode()) typeName = "Dev";
        return configYaml.getString(typeName + ".database");
    }

    public static String getServerDBTable() {
        String typeName = "Release";
        if (isDevMode()) typeName = "Dev";
        return configYaml.getString(typeName + ".table");
    }

    public static String getServerUsername() {
        String typeName = "Release";
        if (isDevMode()) typeName = "Dev";
        return configYaml.getString(typeName + ".user");
    }

    public static String getServerPassword() {
        String typeName = "Release";
        if (isDevMode()) typeName = "Dev";
        return configYaml.getString(typeName + ".password");
    }
}
