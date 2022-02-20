package com.jinke.driverhealth.data.network.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author: fanlihao
 * @date: 2022/1/23
 */
@Entity(tableName = "contactor")
public class Contactor {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "姓名")
    private String name;
    @ColumnInfo(name = "电话")
    private String phone;
    @ColumnInfo(name = "邮箱")
    private String mail;
    @ColumnInfo(name = "是否第一联系人")
    private int isFirstContactor;
    @ColumnInfo(name = "头像路径")
    private String imageUrl;

    public Contactor(String name, String phone, String mail, int isFirstContactor, String imageUrl) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.isFirstContactor = isFirstContactor;
        this.imageUrl = imageUrl;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int isFirstContactor() {
        return isFirstContactor;
    }

    public void setFirstContactor(int firstContactor) {
        isFirstContactor = firstContactor;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Contactor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", isFirstContactor=" + isFirstContactor +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
