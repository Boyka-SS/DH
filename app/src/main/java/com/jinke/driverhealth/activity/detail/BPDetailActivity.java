package com.jinke.driverhealth.activity.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.beans.BloodPressure;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.viewmodels.DataViewModel;
import com.jinke.driverhealth.views.TitleLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 血压详情页面
 */
public class BPDetailActivity extends AppCompatActivity {

    private static final String TAG = "BPDetailActivity";
    //体温折线图
    private DataViewModel mDataViewModel;
    private LineChart mChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);

        hideActionBar();


        mDataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        String endTime = (String) getIntent().getExtras().get("create_time");
        String token = getSharedPreferences("data", MODE_PRIVATE).getString("token", "");
        mDataViewModel.loadBPData(token, Config.START_TIME, endTime, "1", "7").observe(this, new Observer<BloodPressure>() {
            @Override
            public void onChanged(BloodPressure bloodPressure) {
                mChart = findViewById(R.id.line_chart_bp);
                List<BloodPressure.DataDTO.ResultDTO> result = bloodPressure.getData().getResult();
                try {
                    initChart(result);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initChart(List<BloodPressure.DataDTO.ResultDTO> result) throws ParseException {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        //prepare data
        List<Entry> maxEntries = new ArrayList<>();//收缩压
        List<Entry> minEntries = new ArrayList<>();//舒张压
        List<String> date = new ArrayList<>();//舒张压
        int i = 1, j = 1;

        for (BloodPressure.DataDTO.ResultDTO resultDTO : result) {
            maxEntries.add(new Entry(i++, new Double(resultDTO.getMax_rate()).floatValue()));
            minEntries.add(new Entry(j++, new Double(resultDTO.getMin_rate()).floatValue()));
            date.add(resultDTO.getCreated().substring(5, 10));
        }
        Collections.reverse(maxEntries);
        Collections.reverse(minEntries);
        date.add("");//解决X轴不匹配问题
        Collections.reverse(date);

        Log.d(TAG, "date --> " + date);
        Collections.sort(maxEntries, new EntryXComparator());
        Collections.sort(minEntries, new EntryXComparator());


        //设置x轴
        XAxis xAxis = mChart.getXAxis();

        xAxis.setDrawGridLines(true);//设置X轴上每个竖线是否显示
        xAxis.setDrawLabels(true);//设置是否绘制X轴上的对应值(标签)
        xAxis.setGranularity(1f); //设置x轴间距
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(date.size());
        xAxis.setLabelRotationAngle(-30); //标签倾斜
        xAxis.setDrawGridLines(true);//是否显示X轴上的网格线
        xAxis.setTextSize(10);
        xAxis.setAvoidFirstLastClipping(true);//是否避免图表或屏幕的边缘的第一个和最后一个轴中的标签条目被裁剪
        //修改X轴内容成字符日期
        xAxis.setValueFormatter(new IndexAxisValueFormatter(date));

        //设置y轴
        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setLabelCount(7);
        yAxis.setTextSize(12);
        yAxis.setDrawGridLines(false); //是否显示Y轴上的网格线
        yAxis.setAxisMaximum(160f);
        yAxis.setAxisMinimum(50f);

        yAxis.setDrawLabels(true);//绘制y轴标签

        LineDataSet maxDataSet = new LineDataSet(maxEntries, "收缩压");
        renderLine(maxDataSet, "#DC143C");
        LineDataSet minDataSet = new LineDataSet(minEntries, "舒张压");
        renderLine(minDataSet, "#0000FF");
        dataSets.add(maxDataSet);
        dataSets.add(minDataSet);
        LineData lineData = new LineData(dataSets);

        if (result.isEmpty()) {
            mChart.clear();
        } else {
            // set data
            mChart.setData(lineData);
        }


        //设置图例
        Legend legend = mChart.getLegend();
        legend.setFormSize(12f);//设置当前这条线的图例的大小
        legend.setForm(Legend.LegendForm.LINE); // 线
        legend.setFormSize(14f); // 图形大小
        legend.setFormLineWidth(9f);  // 图形线宽
//        legend.setXOffset();
        legend.setXEntrySpace(13f);//设置 各个legend之间的距离
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setWordWrapEnabled(true);
        legend.setTextSize(12);

        //添加界定线
        LimitLine maxLimit = renderLimitLine(140f, "140 mmHg", "#DC143C");
        LimitLine minLimit = renderLimitLine(90f, "90 mmHg", "#0000FF");
        yAxis.addLimitLine(maxLimit);
        yAxis.addLimitLine(minLimit);

        mChart.getAxisRight().setEnabled(false); //不绘制右侧轴线
        mChart.setScaleYEnabled(false);
        mChart.setScaleXEnabled(false);

        //设置是否可以触摸
        mChart.setTouchEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);


        mChart.getDescription().setText("血压分布图");
        mChart.getDescription().setTextSize(12);//与图例字体大小一致
        // 设置LineChar间距
        mChart.setExtraBottomOffset(12f);//设置 legend 和 X轴 之间间距

        mChart.invalidate(); // refresh
        //设置从X轴出来的动画时间
        mChart.animateXY(1000, 1000);
    }

    /**
     * 添加 界线
     *
     * @param value 界定值
     * @param name  界定线名字
     * @param color 界定线颜色
     */
    private LimitLine renderLimitLine(float value, String name, String color) {
        LimitLine limitLine = new LimitLine(value, name);
        limitLine.enableDashedLine(15f, 10f, 0);//设置为虚线
        limitLine.setLineColor(Color.parseColor(color));
        limitLine.setLineWidth(2f);
        limitLine.setTextColor(ContextCompat.getColor(this, R.color.color_limit_line_chart));
        limitLine.setTextSize(12f);
        return limitLine;
    }

    /**
     * 为chart每一条折线设置样式
     *
     * @param dataSet 数据
     * @param color   折线颜色
     */

    private void renderLine(LineDataSet dataSet, String color) {
        dataSet.setDrawCircleHole(false);
        dataSet.setLineWidth(2f);
//        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);//设置线数据依赖于左侧y轴
        dataSet.setColor(Color.parseColor(color));
        dataSet.setDrawFilled(true);//是否画数据覆盖的阴影层
        dataSet.setDrawValues(true);//是否绘制线的数据
        dataSet.setValueTextColor(ContextCompat.getColor(this, R.color.color_line_chart));//设置数据的文本颜色，如果不绘制线的数据 这句代码也不用设置了
        dataSet.setValueTextSize(10f);//如果不绘制线的数据 这句代码也不用设置了
        dataSet.setCircleRadius(4f);//设置每个折线点的大小
        dataSet.setCircleColor(Color.parseColor(color));

    }

    /**
     * 隐藏系统自带 头部导航栏
     */
    private void hideActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        new TitleLayout(this).setTitleText("血压详情").setLeftIcoListening(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}

/**
 * 使用MpAndroidChart步骤（linechart为例）：
 * https://weeklycoding.com/mpandroidchart-documentation/getting-started/
 * MPAndroidChart的详细使用——Legend图例的详细设置：
 * https://blog.csdn.net/Honiler/article/details/80074019
 */