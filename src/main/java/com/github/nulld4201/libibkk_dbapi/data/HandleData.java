package com.github.nulld4201.libibkk_dbapi.data;

import com.github.nulld4201.libibkk_dbapi.database.DBConfig;
import com.github.nulld4201.libibkk_dbapi.database.DatabaseSetting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HandleData {

    private final DatabaseSetting db;

    public HandleData() {
        this.db = new DatabaseSetting();
    }

    /**
     * UUID로 유저데이터 불러옴.
     * @param uuid 유저 UUID
     *             (데이터베이스 내 UUID를 검색하여 UserData return. 검색 결과가 없으면 null return.
     * @return UserData 반환. 이에 대한 부분은 알 필요 없음. 별도 메서드로 각각의 값 반환.
     */
    private UserData getUserByUUID(@Nonnull String uuid) {
        try {
            this.db.connect();
            Connection conn = this.db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + DBConfig.getServerDBTable() + " WHERE UUID=?");

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

    public List<String> getAllUsers() {
        List<String> list = new ArrayList<>();
        try {
            this.db.connect();
            Connection conn = this.db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT ALL UUID FROM " + DBConfig.getServerDBTable() + " WHERE TRUE");

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String UUID = rs.getString("UUID");
                list.add(UUID);
                return list;
            } else {
                rs.close();
                stmt.close();
                this.db.disconnect();
                return new ArrayList<>();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void addUser(@Nonnull String uuid, long cash, @Nullable Timestamp joinTime, @Nullable Timestamp quitTime) {
        if (getUserByUUID(uuid) == null) {
            try {
                this.db.connect();
                Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + DBConfig.getServerDBTable() + "(UUID, Cash, JoinTime, QuitTime) values (?, ?, ?, ?)");

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
        }
    }

    public void addCashToUser(@Nonnull String uuid, long cashToAdd) {
        UserData userData = getUserByUUID(uuid);
        if (userData != null) {
            long currentCash = userData.getUserCash();
            long afterCash = currentCash + cashToAdd;
            try {
                this.db.connect();
                Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement("UPDATE " + DBConfig.getServerDBTable() + " SET UUID=?, Cash=?, JoinTime=?, QuitTime=? WHERE idx=?");
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
        }
    }

    public void setUserJoinTime_Timestamp(@Nonnull String uuid, @Nullable Timestamp timestamp) {
        UserData userData = getUserByUUID(uuid);
        if (userData != null) {
            try {
                this.db.connect();
                Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement("UPDATE " + DBConfig.getServerDBTable() + " SET UUID=?, Cash=?, JoinTime=?, QuitTime=? WHERE idx=?");
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
        }
    }

    public void setUserQuitTime_Timestamp(@Nonnull String uuid, @Nonnull Timestamp timestamp) {
        UserData userData = getUserByUUID(uuid);
        if (userData != null) {
            try {
                this.db.connect();
                Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement("UPDATE " + DBConfig.getServerDBTable() + " SET UUID=?, Cash=?, JoinTime=?, QuitTime=? WHERE idx=?");
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
        }
    }
}
