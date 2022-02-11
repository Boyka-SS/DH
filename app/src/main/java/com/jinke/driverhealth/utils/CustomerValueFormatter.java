package com.jinke.driverhealth.utils;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * 精确表中数据位数
 *
 * @author: fanlihao
 * @date: 2022/2/11
 */
public class CustomerValueFormatter extends ValueFormatter {
    private DecimalFormat mFormat;

    public CustomerValueFormatter() {
        //此处是显示数据的方式，显示整型或者小数后面小数位数自己随意确定
        mFormat = new DecimalFormat("###,###,###,##2");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value);//数据前或者后可根据自己想要显示的方式添加
    }

}
