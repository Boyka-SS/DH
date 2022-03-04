package com.jinke.driverhealth;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jinke.driverhealth.data.db.beans.Advise;
import com.jinke.driverhealth.data.db.beans.Alcohol;
import com.jinke.driverhealth.data.db.beans.Contactor;
import com.jinke.driverhealth.data.db.beans.Question;
import com.jinke.driverhealth.data.db.beans.UserInfo;
import com.jinke.driverhealth.data.db.dao.AdviseDao;
import com.jinke.driverhealth.data.db.dao.AlcoholDao;
import com.jinke.driverhealth.data.db.dao.ContactorDao;
import com.jinke.driverhealth.data.db.dao.QuestionDao;
import com.jinke.driverhealth.data.db.dao.UserInfoDao;

/**
 * @author: fanlihao
 * @date: 2022/1/20
 */
@Database(
        entities = {Alcohol.class, Contactor.class, Question.class, Advise.class, UserInfo.class},
        version =3,
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
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }


    public abstract AlcoholDao getAlcoholDao();

    public abstract ContactorDao getContactorDao();

    public abstract QuestionDao getQuestionDao();


    public abstract AdviseDao getAdviseDao();

    public abstract UserInfoDao getUserInfoDao();

}
