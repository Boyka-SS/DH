package com.jinke.driverhealth.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jinke.driverhealth.data.db.beans.Alcohol;

import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/2/20
 */
@Dao
public interface AlcoholDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAlcohol(Alcohol alcohol);

    @Query("SELECT * from alcohol")
     LiveData<List<Alcohol>> loadAllAlcohols();
}
