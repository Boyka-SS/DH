package com.jinke.driverhealth;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jinke.driverhealth.beans.Contactor;
import com.jinke.driverhealth.beans.HeartRate;
import com.jinke.driverhealth.beans.Token;
import com.jinke.driverhealth.dao.ContactorDao;
import com.jinke.driverhealth.dao.HeartRateDao;

/**
 * @author: fanlihao
 * @date: 2022/1/20
 */
@Database(
        entities = {HeartRate.class, Contactor.class, Token.class},
        version = 1,
        exportSchema = true

)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;


    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room
                            .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "driverHealth.db")
                            .build();
                }
            }
        }
        return instance;
    }


    public abstract HeartRateDao getHeartRateDao();

    public abstract ContactorDao getContactorDao();


}
