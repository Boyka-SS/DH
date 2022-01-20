package com.jinke.driverhealth;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jinke.driverhealth.dao.HeartRateDao;
import com.jinke.driverhealth.entity.HeartRateEntity;

/**
 * @author: fanlihao
 * @date: 2022/1/20
 */
@Database(entities = {HeartRateEntity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    //Singleton
     public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "driver_health_database")
                    .build();
        }
        return instance;
    }

    public abstract HeartRateDao getHeartRateDao();
}
