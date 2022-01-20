package com.jinke.driverhealth.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author: fanlihao
 * @date: 2022/1/20
 */
@Entity(tableName = "heart_rate")
public class HeartRateEntity {

    public HeartRateEntity(String created, String heartRate) {
        this.created = created;
        this.heartRate = heartRate;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "生成时间")
    private String created;

    @ColumnInfo(name = "心率")
    private String heartRate;

    @ColumnInfo(name = "设备号")
    private String imeiSn;


    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }



    public String getImeiSn() {
        return imeiSn;
    }

    public void setImeiSn(String imeiSn) {
        this.imeiSn = imeiSn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }


}
