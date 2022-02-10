package com.jinke.driverhealth.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/2/9
 */
public class XAxisValueFormatter extends ValueFormatter {

    private List<String> date = new ArrayList<>();

    public XAxisValueFormatter(List<String> date) {
        this.date = date;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return String.valueOf(date.get((int) value));
    }
}
