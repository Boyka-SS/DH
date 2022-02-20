package com.jinke.driverhealth.repository;

import android.util.Log;

import com.jinke.driverhealth.data.network.beans.Contactor;
import com.jinke.driverhealth.data.db.dao.ContactorDao;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author: fanlihao
 * @date: 2022/1/24
 */
public class ContactorRepository {
    private static final String TAG = "ContactorRepository";
    private ContactorDao mContactorDao;

    public ContactorRepository(ContactorDao contactorDao) {
        mContactorDao = contactorDao;
    }

    public List<Contactor> getAllContactorData() throws ExecutionException, InterruptedException {

        Callable<List<Contactor>> callable = new Callable<List<Contactor>>() {
            @Override
            public List<Contactor> call() throws Exception {
                return mContactorDao.loadAllContactor();
            }
        };

        Future<List<Contactor>> future = Executors.newSingleThreadExecutor().submit(callable);
        Log.d(TAG, future.get().toString());
        return future.get();
    }
}
