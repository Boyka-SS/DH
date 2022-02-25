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
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "姓名")
    public String name;
    @ColumnInfo(name = "手机号码")
    public String phone;
    @ColumnInfo(name = "联系人系统ID")
    public String sys_id;
    @ColumnInfo(name = "联系人系统LookUpKey")
    public String lookUpKey;

    /**
     * 1 是
     * 0 否
     */
    @ColumnInfo(name = "是否是第一联系人")
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
