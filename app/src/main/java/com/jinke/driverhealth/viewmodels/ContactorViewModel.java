package com.jinke.driverhealth.viewmodels;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jinke.driverhealth.data.db.beans.Contactor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/2/23
 */
public class ContactorViewModel extends ViewModel {
    private static final String TAG = "ContactorViewModel";
    private MutableLiveData<List<Contactor>> mContactorLiveData;

    public MutableLiveData<List<Contactor>> getContactorLiveData() {
        return mContactorLiveData;
    }


    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public MutableLiveData<List<Contactor>> loadAllContactors(ContentResolver cr) {
        mContactorLiveData = new MutableLiveData<>();
        List<Contactor> contactorList = new ArrayList<>();
        Cursor cursor = null;
        //查询联系人 姓名 手机号
        try {
            cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Contactor contactor = new Contactor();
                    //默认设置为不是第一联系人
                    contactor.setIsFirstManToContact(0);
                    //获取联系人姓名
                    String contactorName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //获取联系人手机号
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactor.setName(contactorName);
                    contactor.setPhone(phoneNumber);
                    contactorList.add(contactor);
                }
            }

            mContactorLiveData.postValue(contactorList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return mContactorLiveData;
    }
}
