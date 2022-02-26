package com.jinke.driverhealth.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.jinke.driverhealth.data.db.beans.Contactor;

/**
 * @author: fanlihao
 * @date: 2022/2/23
 */
@Dao
public interface ContactorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContactor(Contactor contactor);
}
