package com.jinke.driverhealth.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jinke.driverhealth.data.db.beans.Question;

import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/3/3
 */
@Dao
public interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuestion(Question question);

    @Query("select * from question")
    LiveData<List<Question>> loadAllQuestion();
}
