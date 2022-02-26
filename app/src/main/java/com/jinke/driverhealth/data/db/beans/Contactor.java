package com.jinke.driverhealth.data.db.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 联系人
 *
 * @author: fanlihao
 * @date: 2022/2/23
 */
@Entity(tableName = "contactor")
public class Contactor {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    public int id;

    @ColumnInfo
    public String name;
    @ColumnInfo
    public String phone;
    @ColumnInfo
    public String sys_id;
    @ColumnInfo
    public String lookUpKey;
    @ColumnInfo
    public String rawContactorsId;

    /**
     * 1 是
     * 0 否
     */
    @ColumnInfo
    public int isFirstManToContact;

    public Contactor() {
    }

    @Ignore
    public Contactor(String name, String phone, String sys_id, String lookUpKey, int isFirstManToContact) {
        this.name = name;
        this.phone = phone;
        this.sys_id = sys_id;
        this.lookUpKey = lookUpKey;
        this.isFirstManToContact = isFirstManToContact;
    }


}
