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
    private int id;
    @ColumnInfo(name = "姓名")
    private String name;
    @ColumnInfo(name = "手机号码")
    private String phone;
    @ColumnInfo(name = "邮箱")
    private String email;
    @ColumnInfo(name = "是否是第一联系人")
    private int isFirstManToContact;


    @Ignore
    public Contactor() {
    }

    public Contactor(String name, String phone, String email, int isFirstManToContact) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.isFirstManToContact = isFirstManToContact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Ignore
    public Contactor(String name, String phone, int isFirstManToContact) {
        this.name = name;
        this.phone = phone;
        this.isFirstManToContact = isFirstManToContact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIsFirstManToContact() {
        return isFirstManToContact;
    }

    public void setIsFirstManToContact(int isFirstManToContact) {
        this.isFirstManToContact = isFirstManToContact;
    }

    @Override
    public String toString() {
        return "Contactor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", isFirstManToContact=" + isFirstManToContact +
                '}';
    }
}
