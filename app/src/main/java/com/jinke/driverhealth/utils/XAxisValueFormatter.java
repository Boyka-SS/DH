package com.jinke.driverhealth.utils;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * 格式化 折线图 x轴的标注
 *
 * @author: fanlihao
 * @date: 2022/2/9
 */
public class XAxisValueFormatter extends ValueFormatter {
    private static final String TAG = "XAxisValueFormatter";

    @Override
    public String getFormattedValue(float value) {
        Log.d(TAG, "XAxisValueFormatter getFormattedValue --> " + value);
        return super.getFormattedValue(value);
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        Log.d(TAG, "XAxisValueFormatter getAxisLabel --> " + value);
        return super.getAxisLabel(value, axis);
    }
    /* @Override
    public String getPointLabel(Entry entry) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date((long) entry.getX()));
        Log.d(TAG, "XAxisValueFormatter getXValue--> " + entry.getX());
        Log.d(TAG, "XAxisValueFormatter date --> " + date);
        return date.substring(5,10);
    }*/
}
