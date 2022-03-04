package com.jinke.driverhealth.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jinke.driverhealth.data.db.beans.UserInfo;

import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/3/4
 */
@Dao
public interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserInfo(UserInfo userInfo);

    @Query("select * from user_info")
    LiveData<List<UserInfo>> loadAllUserInfo();
}
