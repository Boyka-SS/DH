package com.jinke.driverhealth.data.network.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jinke.driverhealth.data.network.beans.HeartRate;

import java.util.List;



/**
 * 定义 操作heart_rate表的方法
 *
 * @author: fanlihao
 * @date: 2022/1/20
 */
@Dao
public interface HeartRateDao {
    @Insert
    void insertHeartRate(HeartRate... heartRate);

    @Update
    void updateHeartRate(HeartRate... heartRate);

    @Delete
    void deleteHeartRate(HeartRate... heartRate);

    @Query("DELETE FROM HEART_RATE")
    void deleteAllHeartRate();

    @Query("SELECT * FROM HEART_RATE ORDER BY heart_rate_id DESC")
    List<HeartRate> getAllHeartRate();
}
