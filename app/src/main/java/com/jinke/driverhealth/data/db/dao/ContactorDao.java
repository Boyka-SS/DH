package com.jinke.driverhealth.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.jinke.driverhealth.data.db.beans.Contactor;

import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/2/23
 */
@Dao
public interface ContactorDao {
    @Query("SELECT * FROM contactor")
    LiveData<List<Contactor>> loadAllContactor();
}
