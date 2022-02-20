package com.jinke.driverhealth;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jinke.driverhealth.data.db.beans.Alcohol;
import com.jinke.driverhealth.data.db.dao.AlcoholDao;
import com.jinke.driverhealth.data.db.dao.ContactorDao;
import com.jinke.driverhealth.data.network.beans.Contactor;
import com.jinke.driverhealth.data.network.beans.HeartRate;
import com.jinke.driverhealth.data.network.beans.Token;
import com.jinke.driverhealth.data.network.dao.HeartRateDao;

/**
 * @author: fanlihao
 * @date: 2022/1/20
 */
@Database(
        entities = {HeartRate.class, Contactor.class, Token.class, Alcohol.class},
        version = 2,
        exportSchema = true

)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;


    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room
                            .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "driverHealth.db")         .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }


    public abstract HeartRateDao getHeartRateDao();

    public abstract ContactorDao getContactorDao();

    public abstract AlcoholDao getAlcoholDao();


}
