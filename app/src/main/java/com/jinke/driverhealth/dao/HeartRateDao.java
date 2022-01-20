package com.jinke.driverhealth.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jinke.driverhealth.entity.HeartRateEntity;

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
    void insertHeartRate(HeartRateEntity... heartRate);

    @Update
    void updateHeartRate(HeartRateEntity... heartRate);

    @Delete
    void deleteHeartRate(HeartRateEntity... heartRate);

    @Query("DELETE FROM HEART_RATE")
    void deleteAllHeartRate();

    @Query("SELECT * FROM HEART_RATE ORDER BY ID DESC")
    List<HeartRateEntity> getAllHeartRate();
}
