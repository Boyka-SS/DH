package com.jinke.driverhealth.data.repository;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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

    public MutableLiveData<List<Contactor>> fetchAllContactors(Context ctx,ContentResolver cr) {
        return fetchAllContactorFromSys(ctx,cr);
    }

    /**
     * 获取系统联系人
     *
     * @param cr
     * @return
     */
    @SuppressLint("Range")
    private MutableLiveData<List<Contactor>> fetchAllContactorFromSys(Context ctx, ContentResolver cr) {
        MutableLiveData<List<Contactor>> liveData = new MutableLiveData<>();
        List<Contactor> contactorList = new ArrayList<>();
        Cursor cursor = null;
        //查询联系人 姓名 手机号
        try {
            cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Contactor contactor = new Contactor();

                    contactor.isFirstManToContact = 0; //默认设置为不是第一联系人
                    //获取联系人姓名
                    String contactorName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    contactor.name = contactorName;
                    Log.d(TAG, "contactor name --> " + contactorName);
                    //获取联系人手机号
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactor.phone = phoneNumber;
                    Log.d(TAG, "contactor phone --> " + phoneNumber);
                    //id
                    String contactorId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    contactor.sys_id = contactorId;
                    Log.d(TAG, "contactor id --> " + contactorId);
                    //lookupkey
                    String lookUpKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                    Log.d(TAG, "contactor lookupid --> " + lookUpKey);
                    contactor.lookUpKey = lookUpKey;
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
        //存入数据
        liveData.observe((LifecycleOwner) ctx, new Observer<List<Contactor>>() {
            @Override
            public void onChanged(List<Contactor> contactors) {
                insertContactors(contactors);
            }
        });

        return liveData;
    }

    /**
     * 存入数据
     *
     * @param contactors
     */
    private void insertContactors(List<Contactor> contactors) {
        Log.d(TAG, "" + contactors);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mContactorDao.insertContactors(contactors);
            }
        }
        ).start();
    }
}
