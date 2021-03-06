package com.github.nulld4201.libibkk_dbapi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Setting.
 * Set Columns as idx, UUID, Cash, JoinTime, QuitTime
 * If you don't set columns like upward, contact plugin developer and request modification.
 */
public class DatabaseSetting {
    private Connection connection;

    public boolean isConnected() {
        return (connection != null);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mariadb://" + DBConfig.getServerHost() + ":" + DBConfig.getServerPort() + "/" + DBConfig.getServerDB(),
                DBConfig.getServerUsername(), DBConfig.getServerPassword());
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
