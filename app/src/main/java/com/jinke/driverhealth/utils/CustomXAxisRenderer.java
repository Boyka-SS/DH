package com.jinke.driverhealth.utils;

import android.graphics.Canvas;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * 自定义 linechart X 轴 换行
 *
 * @author: fanlihao
 * @date: 2022/2/11
 */
public class CustomXAxisRenderer extends XAxisRenderer {
    public CustomXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }

    @Override
    protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
//        super.drawLabel(c, formattedLabel, x, y, anchor, angleDegrees);
        String[] lines = formattedLabel.split(" ");
        for (int i = 0; i < lines.length; i++) {
            float vOffset = i * mAxisLabelPaint.getTextSize();
            Utils.drawXAxisValue(c, lines[i], x, y + vOffset, mAxisLabelPaint, anchor, angleDegrees);
        }
    }
}
