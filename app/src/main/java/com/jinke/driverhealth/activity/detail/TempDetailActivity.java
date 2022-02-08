package com.jinke.driverhealth.activity.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.beans.Temperature;
import com.jinke.driverhealth.viewmodels.DataViewModel;
import com.jinke.driverhealth.views.TitleLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * 体温详情页面
 */
public class TempDetailActivity extends AppCompatActivity {


    private static final String TAG = "TempActivity";
    //体温折线图

    private LineChartView lineChart;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    private DataViewModel mDataViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        hideActionBar();

        mDataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        String endTime = (String) getIntent().getExtras().get("create_time");

        mDataViewModel.loadTempData("", endTime, "1", "7").observe(this, new Observer<Temperature>() {
            @Override
            public void onChanged(Temperature temperature) {
                lineChart = findViewById(R.id.line_chart_temp);
                List<Temperature.DataDTO.ResultDTO> result = temperature.getData().getResult();

                List<String> date = new ArrayList<>();
                List<String> tempData = new ArrayList<>();

                for (Temperature.DataDTO.ResultDTO item : result) {
                    //01-20 16:13:29
                    date.add(item.getCreated().substring(5, 16));
                    tempData.add(item.getTemperature());
                }
                Collections.reverse(date);
                Collections.reverse(tempData);
                getAxisXLables(date);//获取x轴的标注
                getAxisPoints(tempData);//获取坐标点
                initLineChart();//初始化
            }
        });

    }

    /**
     * 隐藏系统自带 头部导航栏
     */
    private void hideActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        new TitleLayout(this).setTitleText("体温详情").setLeftIcoListening(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(true);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(false);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）,标签是否只能选中
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        line.setFormatter(new SimpleLineChartValueFormatter(2));//设置显示小数点

        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(R.color.design_default_color_primary_variant);  //设置字体颜色
//        axisX.setName("日期");  //表格名称
        axisX.setTextSize(8);//设置字体大小
//        axisX.setMaxLabelChars(6);
        axisX.setMaxLabelChars(7); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setHasLines(true);
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("体温");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        axisY.setMaxLabelChars(6);//max label length, for example 60
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(false);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 1);//最大放大比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 6;
        lineChart.setCurrentViewport(v);
    }

    /**
     * 图表的每个点的表示
     */
    private void getAxisPoints(List<String> result) {
        for (int i = 0; i < result.size(); i++) {
            mPointValues.add(new PointValue(i, Float.parseFloat(result.get(i))));
        }
    }

    /**
     * 设置X轴的显示
     *
     * @param result
     */
    private void getAxisXLables(List<String> result) {
        for (int i = 0; i < result.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(result.get(i)));
        }
    }


}

/**
 * hello chart
 * https://www.cnblogs.com/tfxz/p/12621642.html
 */