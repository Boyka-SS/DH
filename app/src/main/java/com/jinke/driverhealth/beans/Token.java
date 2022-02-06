package com.jinke.driverhealth.beans;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author: fanlihao
 * @date: 2022/1/16
 */
@Entity(tableName = "token")
public class Token {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @Embedded
    private DataDTO data;
    @Ignore
    private String message;
    @Ignore
    private int status;


    public Token() {

    }

    @Ignore
    public Token( String message, int status) {

        this.message = message;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataDTO {
        @ColumnInfo(name = "expireTime")
        private String expired;
        @ColumnInfo(name = "token")
        private String token;

        public String getExpired() {
            return expired;
        }

        public void setExpired(String expired) {
            this.expired = expired;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    @Override
    public String toString() {
        return "Token{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
