package com.github.nulld4201.libibkk_dbapi.data;

import com.github.nulld4201.libibkk_dbapi.Main;
import com.github.nulld4201.libibkk_dbapi.database.DBConfig;
import com.github.nulld4201.libibkk_dbapi.database.DBType;
import com.github.nulld4201.libibkk_dbapi.database.DatabaseSetting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.*;

public class HandleData {

    private final DatabaseSetting db;

    public HandleData() {
        this.db = new DatabaseSetting();
    }

    /**
     * UUID로 유저데이터 불러옴.
     * @param type 데이터베이스 종류
     *             (Dev인지, Release인지. 만약 dbconfig 내 Dev_Mode가 true일 경우 값에 관계없이 항상 Dev)
     * @param uuid 유저 UUID
     *             (데이터베이스 내 UUID를 검색하여 UserData return. 검색 결과가 없으면 null return.
     * @return UserData 반환. 이에 대한 부분은 알 필요 없음. 별도 메서드로 각각의 값 반환.
     */
    private UserData getUserByUUID(@Nonnull DBType type, @Nonnull String uuid) {
        try {
            this.db.connect(type);
            Connection conn = this.db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + DBConfig.getServerDBTable(type) + " WHERE UUID=?");

            stmt.setString(1, uuid);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                long index = rs.getLong(1);
                String UUID = rs.getString(2);
                long Cash = rs.getLong(3);
                Timestamp JoinTime = rs.getTimestamp(4);
                Timestamp QuitTime = rs.getTimestamp(5);
                UserData userData = new UserData(index, UUID, Cash, JoinTime, QuitTime);

                rs.close();
                stmt.close();
                this.db.disconnect();
                return userData;
            } else {
                Main.getPlugin(Main.class).getLogger().warning("User not found!");
                rs.close();
                stmt.close();
                this.db.disconnect();
                return null;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(@Nonnull DBType type, @Nonnull String uuid, long cash, @Nullable Timestamp joinTime, @Nullable Timestamp quitTime) {
        if (getUserByUUID(type, uuid) == null) {
            try {
                this.db.connect(type);
                Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + DBConfig.getServerDBTable(type) + "(UUID, Cash, JoinTime, QuitTime) values (?, ?, ?, ?)");

                stmt.setString(1, uuid);
                stmt.setLong(2, cash);
                stmt.setTimestamp(3, joinTime);
                stmt.setTimestamp(4, quitTime);

                stmt.executeUpdate();

                stmt.close();
                conn.close();
                this.db.disconnect();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            Main.getPlugin(Main.class).getLogger().warning("User already exist.");
        }
    }

    public void addCashToUser(@Nonnull DBType type, @Nonnull String uuid, long cashToAdd) {
        UserData userData = getUserByUUID(type, uuid);
        if (userData != null) {
            long currentCash = userData.getUserCash();
            long afterCash = currentCash + cashToAdd;
            try {
                this.db.connect(type);
                Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement("UPDATE " + DBConfig.getServerDBTable(type) + " SET UUID=?, Cash=?, JoinTime=?, QuitTime=? WHERE idx=?");
                stmt.setString(1, userData.getUserUUID());
                stmt.setLong(2, afterCash);
                stmt.setTimestamp(3, userData.getUserJoinTime());
                stmt.setTimestamp(4, userData.getUserQuitTime());
                stmt.setLong(5, userData.getUserIndex());

                stmt.executeUpdate();
                stmt.close();

                this.db.disconnect();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            Main.getPlugin(Main.class).getLogger().warning("User not found!");
        }
    }

    public void setUserJoinTime_Timestamp(@Nonnull DBType type, @Nonnull String uuid, @Nullable Timestamp timestamp) {
        UserData userData = getUserByUUID(type, uuid);
        if (userData != null) {
            try {
                this.db.connect(type);
                Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement("UPDATE " + DBConfig.getServerDBTable(type) + " SET UUID=?, Cash=?, JoinTime=?, QuitTime=? WHERE idx=?");
                stmt.setString(1, userData.getUserUUID());
                stmt.setLong(2, userData.getUserCash());
                stmt.setTimestamp(3, timestamp);
                stmt.setTimestamp(4, userData.getUserQuitTime());
                stmt.setLong(5, userData.getUserIndex());

                stmt.executeUpdate();
                stmt.close();

                this.db.disconnect();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            Main.getPlugin(Main.class).getLogger().warning("User not found!");
        }
    }

    public void setUserJoinTime_FormattedString(@Nonnull DBType type, @Nonnull String uuid, String formatString) {
        setUserJoinTime_Timestamp(type, uuid, Timestamp.valueOf(formatString));
    }

    public void setUserQuitTime_Timestamp(@Nonnull DBType type, @Nonnull String uuid, @Nonnull Timestamp timestamp) {
        UserData userData = getUserByUUID(type, uuid);
        if (userData != null) {
            try {
                this.db.connect(type);
                Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement("UPDATE " + DBConfig.getServerDBTable(type) + " SET UUID=?, Cash=?, JoinTime=?, QuitTime=? WHERE idx=?");
                stmt.setString(1, userData.getUserUUID());
                stmt.setLong(2, userData.getUserCash());
                stmt.setTimestamp(3, userData.getUserJoinTime());
                stmt.setTimestamp(4, timestamp);
                stmt.setLong(5, userData.getUserIndex());

                stmt.executeUpdate();
                stmt.close();

                this.db.disconnect();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            Main.getPlugin(Main.class).getLogger().warning("User not found!");
        }
    }

    public void setUserQuitTime_FormattedString(@Nonnull DBType type, @Nonnull String uuid, String formatString) {
        setUserQuitTime_Timestamp(type, uuid, Timestamp.valueOf(formatString));
    }
}
