package com.jinke.driverhealth.data.db.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 酒精实体类
 *
 * @author: fanlihao
 * @date: 2022/2/20
 */
@Entity(tableName = "alcohol")
public class Alcohol {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "生成时间")
    public String createdTime;
    @ColumnInfo(name = "酒精浓度")
    public int alcohol_concentration;

    public Alcohol(String createdTime, int alcohol_concentration) {
        this.createdTime = createdTime;
        this.alcohol_concentration = alcohol_concentration;
    }
}
