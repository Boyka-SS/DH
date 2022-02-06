package com.jinke.driverhealth.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jinke.driverhealth.beans.Token;

/**
 * @author: fanlihao
 * @date: 2022/2/6
 */

@Dao
public interface  TokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertToken(Token token);

    @Query("SELECT * FROM token")
    public Token loadTokenFromLocal();
}
