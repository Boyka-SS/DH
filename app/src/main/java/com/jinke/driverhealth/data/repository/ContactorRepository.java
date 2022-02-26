package com.jinke.driverhealth.data.repository;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.lifecycle.MutableLiveData;

import com.jinke.driverhealth.DHapplication;
import com.jinke.driverhealth.data.db.beans.Contactor;
import com.jinke.driverhealth.data.db.dao.ContactorDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/2/25
 */
public class ContactorRepository {
    private static final String TAG = "ContactorRepository";

    private ContactorDao mContactorDao = DHapplication.mAppDatabase.getContactorDao();


    public ContactorRepository() {

    }


    /**
     * 从系统通讯录中获取联系人
     *
     * @param cr
     * @return
     */
    @SuppressLint("Range")
    public MutableLiveData<List<Contactor>> fetchAllContactorFromSys(Context ctx, ContentResolver cr) {
        MutableLiveData<List<Contactor>> liveData = new MutableLiveData<>();
        List<Contactor> contactorList = new ArrayList<>();
        Cursor cursor = null;
        //查询联系人 姓名 手机号
        try {
            // 查询contacts表的所有记录
            cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            // 如果记录不为空
            if (cursor.getCount() > 0) {
                // 游标初始指向查询结果的第一条记录的上方，执行moveToNext函数会判断
                // 下一条记录是否存在，如果存在，指向下一条记录。否则，返回false。
                while (cursor.moveToNext()) {
                    String rawContactId = "";
                    Contactor contactor = new Contactor();
                    contactor.isFirstManToContact = 0; //默认设置为不是第一联系人
                    //获取联系人姓名
                    String contactorName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    contactor.name = contactorName;
                    //获取联系人手机号
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactor.phone = phoneNumber;
                    //id,从Contacts表当中取得ContactId
                    String contactorId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    contactor.sys_id = contactorId;
                    //lookupkey
                    String lookUpKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                    contactor.lookUpKey = lookUpKey;

                    // 获取RawContacts表的游标
                    Cursor rawContactCur = cr.query(ContactsContract.RawContacts.CONTENT_URI, null,
                            ContactsContract.RawContacts._ID + "=?", new String[]{contactorId}, null);
                    // 该查询结果一般只返回一条记录，所以我们直接让游标指向第一条记录
                    if (rawContactCur.moveToFirst()) {
                        // 读取第一条记录的RawContacts._ID列的值
                        rawContactId = rawContactCur.getString(rawContactCur
                                .getColumnIndex(ContactsContract.RawContacts._ID));
                        contactor.rawContactorsId = rawContactId;
                    }
                    // 关闭游标
                    rawContactCur.close();

                    contactorList.add(contactor);
                }
            }
            liveData.postValue(contactorList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return liveData;
    }

    /**
     * 存入数据
     *
     * @param contactor
     */
    public void insertContactors(Contactor contactor) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mContactorDao.insertContactor(contactor);
            }
        }
        ).start();
    }
}
//系统联系人 ：
// https://blog.csdn.net/cbk861110/article/details/50930828