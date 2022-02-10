package com.jinke.driverhealth.activity.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.beans.BloodPressure;
import com.jinke.driverhealth.utils.XAxisValueFormatter;
import com.jinke.driverhealth.viewmodels.DataViewModel;
import com.jinke.driverhealth.views.TitleLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 血压详情页面
 */
public class BPDetailActivity extends AppCompatActivity {

    private static final String TAG = "TempActivity";
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
        mDataViewModel.loadBPData(token, "", endTime, "1", "7").observe(this, new Observer<BloodPressure>() {
            @Override
            public void onChanged(BloodPressure bloodPressure) {
                mChart = findViewById(R.id.line_chart_bp);
                List<BloodPressure.DataDTO.ResultDTO> result = bloodPressure.getData().getResult();
                initChart(result);
            }
        });

    }

    private void initChart(List<BloodPressure.DataDTO.ResultDTO> result) {

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();


        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(result.size());
        xAxis.setTextSize(12);


        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setLabelCount(7);
        yAxis.setTextSize(12);
        yAxis.setDrawGridLines(false);  //不画y轴网格线



        //prepare data
        List<Entry> maxEntries = new ArrayList<>();//收缩压
        List<Entry> minEntries = new ArrayList<>();//舒张压
        List<String> date = new ArrayList<>();
        int i = 1, j = 1;
        for (BloodPressure.DataDTO.ResultDTO resultDTO : result) {
            maxEntries.add(new Entry(i++, resultDTO.getMax_rate()));
            minEntries.add(new Entry(j++, resultDTO.getMin_rate()));
            date.add(resultDTO.getCreated().substring(5, 10));
        }
        Collections.reverse(maxEntries);
        Collections.sort(maxEntries, new EntryXComparator());
        Collections.reverse(minEntries);
        Collections.sort(minEntries, new EntryXComparator());

        Collections.reverse(date);


        LineDataSet maxDataSet = new LineDataSet(maxEntries, "收缩压");
        renderLine(maxDataSet, "#DC143C");
        LineDataSet minDataSet = new LineDataSet(minEntries, "舒张压");
        renderLine(minDataSet, "#0000FF");


        dataSets.add(maxDataSet);
        dataSets.add(minDataSet);
        LineData lineData = new LineData(dataSets);
        lineData.setValueFormatter(new XAxisValueFormatter(date));

        mChart.setData(lineData);

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



        mChart.getAxisRight().setEnabled(false); //不绘制右侧轴线
        mChart.setScaleYEnabled(false);
        mChart.setScaleXEnabled(false);
        mChart.getDescription().setText("血压分布图");
        mChart.getDescription().setTextSize(12);//与图例字体大小一致
        mChart.setExtraBottomOffset(12f);//设置 legend 和 X轴 之间间距

        mChart.invalidate(); // refresh
        //默认动画
        mChart.animateXY(1000, 1000);
    }


    private void renderLine(LineDataSet dataSet, String color) {
        dataSet.setDrawCircleHole(false);
        dataSet.setLineWidth(2f);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);//设置线数据依赖于左侧y轴
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