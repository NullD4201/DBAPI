package com.github.nulld4201.libibkk_dbapi.data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class UserData {
    private final long userIndex;
    private final String userUUID;
    private final long userCash;
    private final Timestamp userJoinTime;
    private final Timestamp userQuitTime;

    public UserData(long userIndex, String userUUID, long userCash, Timestamp userJoinTime, Timestamp userQuitTime) {
        this.userIndex = userIndex;
        this.userUUID = userUUID;
        this.userCash = userCash;
        this.userJoinTime = userJoinTime;
        this.userQuitTime = userQuitTime;
    }

    public long getUserIndex() {
        return userIndex;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public long getUserCash() {
        return userCash;
    }

    public Timestamp getUserJoinTime() {
        return userJoinTime;
    }

    public Timestamp getUserQuitTime() {
        return userQuitTime;
    }

    public String getUserJoinTime_formatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(userJoinTime);
    }

    public String getUserQuitTime_formatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(userQuitTime);
    }
}
