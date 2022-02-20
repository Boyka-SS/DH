package com.jinke.driverhealth.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jinke.driverhealth.data.network.beans.Contactor;

import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/1/23
 */
@Dao
public interface ContactorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContactor(Contactor contactors);

    @Delete
    void deleteContactor(Contactor contactors);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateContactors(List<Contactor> contactors);

    @Query("SELECT * FROM contactor WHERE id =:id")
    Contactor loadContactorById(int id);

    @Query("SELECT * FROM contactor WHERE  id =:id")
    Contactor loadContactorByIsFisrst(int id);

    @Query("SELECT * FROM contactor")
    List<Contactor> loadAllContactor();


}
