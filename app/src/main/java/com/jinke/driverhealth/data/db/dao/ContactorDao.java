package com.jinke.driverhealth.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jinke.driverhealth.data.db.beans.Contactor;

/**
 * @author: fanlihao
 * @date: 2022/2/23
 */
@Dao
public interface ContactorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContactor(Contactor contactor);

    @Query("SELECT * FROM contactor where isfirstmantocontact = :id ")
    Contactor loadContactorByFirstMan(int id);

    @Query("SELECT * FROM contactor where lookUpKey = :key ")
    Contactor loadContactorByLookUpKey(String key);
}
