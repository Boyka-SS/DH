package com.jinke.driverhealth.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.AppDatabase;
import com.jinke.driverhealth.DHapplication;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.data.network.beans.Contactor;
import com.jinke.driverhealth.data.db.dao.ContactorDao;
import com.jinke.driverhealth.repository.ContactorRepository;
import com.jinke.driverhealth.views.TitleLayout;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 添加联系人界面
 */
public class ContacterActivity extends AppCompatActivity {

    private static final String TAG = "ContacterActivity";

    private EditText contacterName;
    private EditText contacterSurName;
    private EditText contacterPhone;
    private EditText contacterMail;
    private CheckBox mCheckBox;
    private Button mResetButton, mSave;

    protected AppDatabase mAppDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacter);
        //隐藏系统自带 头部导航栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        new TitleLayout(this).setTitleText("添加联系人").setLeftIcoListening(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //获取database实例
        mAppDatabase = ((DHapplication) getApplication()).getAppDatabase();
        initView();

    }

    private void initView() {
        contacterSurName = this.findViewById(R.id.contacter_surname);
        contacterName = this.findViewById(R.id.contacter_name);
        //电话 和 邮箱 id exchange
        contacterPhone = this.findViewById(R.id.contacter_mail);
        contacterMail = this.findViewById(R.id.contacter_phone);
        mCheckBox = this.findViewById(R.id.checkBox);

        mSave = this.findViewById(R.id.finish);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveContactorInfo();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        mResetButton = this.findViewById(R.id.reset);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "reset");
                resetContactorInfo();
            }
        });


    }


    /**
     * 重置用户联系人信息
     */
    private void resetContactorInfo() {
        Log.d(TAG, "reset ");
        contacterName.setText("");
        contacterSurName.setText("");
        contacterPhone.setText("");
        contacterMail.setText("");
        mCheckBox.setChecked(false);
    }

    /**
     * room 保存 联系人 信息
     */
    private void saveContactorInfo() throws ExecutionException, InterruptedException {

        String name = contacterSurName.getText().toString() + contacterName.getText().toString();
//        String phone = isTelPhoneNumber(contacterPhone.getText().toString())? contacterPhone.getText().toString() : "手机号格式不正确";
        String phone = contacterPhone.getText().toString();
//        String mail = isMail(contacterMail.getText().toString()) ? contacterMail.getText().toString() : "邮箱格式不正确";
        String mail = contacterMail.getText().toString();

        Contactor contactor = new Contactor(name,
                phone,
                mail,
                mCheckBox.isChecked() ? 1 : 0,
                "");

        ContactorDao contactorDao = mAppDatabase.getContactorDao();

        List<Contactor> allContactorData = new ContactorRepository(contactorDao).getAllContactorData();

        Iterator<Contactor> iterator = allContactorData.iterator();

        //保证第一联系人唯一,如果新增联系人被设为第一联系人，则其他联系人要设为false,并且更新 DB
        if (mCheckBox.isChecked()) {
            while (iterator.hasNext()) {
                iterator.next().setFirstContactor(0);
            }
            contactorDao.updateContactors(allContactorData);
        }

        new AlertDialog.Builder(ContacterActivity.this)
                .setMessage("是否保存")
                .setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "cancel");
                    }
                })
                .setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        contactorDao.insertContactor(contactor);
                        resetContactorInfo();
                    }
                })
                .show();

    }


    /**
     * 手机号号段校验，
     * 第1位：1；
     * 第2位：{3、4、5、6、7、8、9}任意数字；
     * 第3—11位：0—9任意数字
     *
     * @param value
     * @return
     */
    public static boolean isTelPhoneNumber(String value) {
        if (value != null && value.length() == 11) {
            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 邮箱有效性验证
     *
     * @param value
     * @return
     */
    public static boolean isMail(String value) {
        if (value != "") {
            Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 点击软键盘外面的区域关闭软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}