package com.jinke.driverhealth.viewmodels;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jinke.driverhealth.data.db.beans.Contactor;
import com.jinke.driverhealth.data.repository.ContactorRepository;

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
    public LiveData<List<Contactor>> loadAllContactors(Context ctx, ContentResolver cr) {
        return new ContactorRepository().fetchAllContactorFromSys(ctx, cr);
    }
}
