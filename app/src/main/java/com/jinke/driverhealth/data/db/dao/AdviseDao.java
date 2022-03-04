package com.jinke.driverhealth.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jinke.driverhealth.data.db.beans.Advise;

import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/3/4
 */

@Dao
public interface AdviseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAdvise(Advise advise);

    @Query("select * from advise")
    LiveData<List<Advise>> loadAllAdvise();
}
