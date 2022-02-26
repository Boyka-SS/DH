package com.jinke.driverhealth.viewmodels;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jinke.driverhealth.activity.contactor.ContactorActivity;
import com.jinke.driverhealth.data.db.beans.Contactor;
import com.jinke.driverhealth.data.repository.ContactorRepository;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

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


    /**
     * 设置为 第一联系人 将会存储的 本机 DB
     *
     * @param contactor
     */
    public void insertFirstManToContact(Contactor contactor) {
        new ContactorRepository().insertContactors(contactor);
    }

    /**
     * 跳转 编辑 系统联系人 界面
     *
     * @param sys_id
     * @param lookUpKey
     */
    public void modifyContactorInfo(Context context, String sys_id, String lookUpKey) {
        new SweetAlertDialog(context)
                .setTitleText("此操作会修改系统数据")
                .setConfirmText("确认")
                .setConfirmButtonBackgroundColor(Color.parseColor("#1E90FF"))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        Uri data = ContactsContract.Contacts.getLookupUri(Long.parseLong(sys_id), lookUpKey);
                        intent.setDataAndType(data, ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                        intent.putExtra("finishActivityOnSaveCompleted", true);
                        context.startActivity(intent);
                    }
                })
                .setCancelText("取消")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 删除 系统 某一联系人
     *
     * @param lookUpKey
     * @param deleteCallback
     */
    public void deleteContactorByLookUpKey(Context context, String lookUpKey, ContactorActivity.DeleteCallback deleteCallback) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("操作警告！")
                .setContentText("此项操作不可逆")
                .setConfirmText("删除")
                .setCancelText("取消")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        ContentResolver contentResolver = context.getContentResolver();
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookUpKey);
                        contentResolver.delete(uri, null, null);
                        deleteCallback.success();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                })
                .show();

    }
}
