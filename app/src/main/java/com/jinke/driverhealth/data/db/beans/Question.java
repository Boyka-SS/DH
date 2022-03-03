package com.jinke.driverhealth.data.db.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author: fanlihao
 * @date: 2022/3/3
 */
@Entity(tableName = "question")
public class Question {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String time;
    @ColumnInfo
    public String content;

    public Question(String time, String content) {
        this.time = time;
        this.content = content;
    }
}
