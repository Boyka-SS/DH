package com.jinke.driverhealth.data.db.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author: fanlihao
 * @date: 2022/3/4
 */
@Entity(tableName = "user_info")
public class UserInfo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public int id;
    @ColumnInfo
    public String name;
    @ColumnInfo
    public String address;
    @ColumnInfo
    public String idCard;
    @ColumnInfo
    public String sex;
    @ColumnInfo
    public String weight;
    @ColumnInfo
    public String height;

    public UserInfo(String name, String address, String idCard, String sex, String weight, String height) {
        this.name = name;
        this.address = address;
        this.idCard = idCard;
        this.sex = sex;
        this.weight = weight;
        this.height = height;
    }
}
