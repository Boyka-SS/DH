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
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.beans.HeartRate;
import com.jinke.driverhealth.utils.CalendarUtil;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.utils.CustomXAxisRenderer;
import com.jinke.driverhealth.viewmodels.DataViewModel;
import com.jinke.driverhealth.views.TitleLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 心率详情页面
 */
public class HRDetailActivity extends AppCompatActivity {

    private static final String TAG = "HRDetailActivity";
    private LineChart mChart;
    private DataViewModel mDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

        hideActionBar();

        mDataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        String createTime = (String) getIntent().getExtras().get("create_time");
        //请求数据 截止日期 天数+1

        String endTime = null, startTime = null;
        try {
            endTime = CalendarUtil.getFormatCalendar(createTime, 1, true);
            startTime = CalendarUtil.getFormatCalendar(endTime, 8, false);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String token = getSharedPreferences("data", MODE_PRIVATE).getString("token", "");

        //升序数据
        mDataViewModel.loadHRData(token, startTime, endTime, "1", "7", Config.ASC_DATA).observe(this, new Observer<HeartRate>() {
            @Override
            public void onChanged(HeartRate heartRate) {
                mChart = findViewById(R.id.line_chart_hr);
                List<HeartRate.DataDTO.ResultDTO> result = heartRate.getData().getResult();
                initChart(result);

            }
        });

    }

    private void initChart(List<HeartRate.DataDTO.ResultDTO> result) {

        //prepare data
        List<Entry> hrEntries = new ArrayList<>();//心率
        List<String> date = new ArrayList<>();//日期


        for (int i = 0; i < result.size(); i++) {
            hrEntries.add(new Entry(i, new Double(result.get(i).getHeart_rate()).floatValue()));
            date.add(result.get(i).getCreated().substring(5, 16));
        }

        date.add("");//解决X轴不匹配问题
//        Collections.sort(hrEntries, new EntryXComparator());
        Log.d(TAG, "date --> " + date);
        Log.d(TAG, "hrEntries --> " + hrEntries);


        //设置x轴
        XAxis xAxis = mChart.getXAxis();

        xAxis.setDrawGridLines(true);//设置X轴上每个竖线是否显示
        xAxis.setDrawLabels(true);//设置是否绘制X轴上的对应值(标签)
        xAxis.setGranularity(1f); //设置x轴间距
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(date.size());
        xAxis.setLabelRotationAngle(-10); //标签倾斜
        xAxis.setDrawGridLines(true);//是否显示X轴上的网格线
        xAxis.setTextSize(10);
        xAxis.setAvoidFirstLastClipping(false);//是否避免图表或屏幕的边缘的第一个和最后一个轴中的标签条目被裁剪
        //修改X轴内容成字符日期
        xAxis.setValueFormatter(new IndexAxisValueFormatter(date));

        //设置y轴
        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setLabelCount(7);
        yAxis.setTextSize(12);
        yAxis.setDrawGridLines(false); //是否显示Y轴上的网格线
        yAxis.setAxisMaximum(120f);
        yAxis.setAxisMinimum(50f);
        yAxis.setDrawLabels(true);//绘制y轴标签

        LineDataSet hrDataSet = new LineDataSet(hrEntries, "心率");
        renderLine(hrDataSet, "#DC143C");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(hrDataSet);

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
        legend.setXEntrySpace(13f);//设置 各个legend之间的距离
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setWordWrapEnabled(true);
        legend.setTextSize(12);

        //添加界定线
        LimitLine maxLimit = renderLimitLine(100f, "100 次/分", "#DC143C");
        LimitLine minLimit = renderLimitLine(60f, "60 次/分", "#0000FF");
        yAxis.addLimitLine(maxLimit);
        yAxis.addLimitLine(minLimit);

        mChart.getAxisRight().setEnabled(false); //不绘制右侧轴线
        mChart.setScaleYEnabled(false);
        mChart.setScaleXEnabled(false);

        //设置是否可以触摸
        mChart.setTouchEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);

        //X 轴 换行显示
        mChart.setXAxisRenderer(new CustomXAxisRenderer(mChart.getViewPortHandler(), mChart.getXAxis(), mChart.getTransformer(YAxis.AxisDependency.LEFT)));
        mChart.setExtraRightOffset(30f);

        mChart.getDescription().setText("心率分布图");
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
        new TitleLayout(this).setTitleText("心率详情").setLeftIcoListening(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}