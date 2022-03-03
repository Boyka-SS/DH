package com.jinke.driverhealth.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jinke.driverhealth.DHapplication;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.alcohol.AlcoholActivity;
import com.jinke.driverhealth.activity.center.CenterActivity;
import com.jinke.driverhealth.data.db.beans.Alcohol;
import com.jinke.driverhealth.data.db.dao.AlcoholDao;
import com.jinke.driverhealth.data.network.beans.SingleBp;
import com.jinke.driverhealth.data.network.beans.SingleHr;
import com.jinke.driverhealth.data.network.beans.SingleTemp;
import com.jinke.driverhealth.data.network.beans.Temperature;
import com.jinke.driverhealth.utils.CalendarUtil;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.utils.CustomXAxisRenderer;
import com.jinke.driverhealth.utils.CustomerValueFormatter;
import com.jinke.driverhealth.viewmodels.DataViewModel;
import com.jinke.driverhealth.viewmodels.SingleDataViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;

public class HomePageFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HomePageFragment";


    //View
    private LinearLayout mAlcoholLinearLayout;
    private ArcProgress mArcProgress;
    private TextView mAlcoholConcentration,
            mRecentHrAndBpDataCreateTime,
            mRecentTempDataCreateTime,
            mRecentBpMaxData,
            mRecentBpMinData,
            mRecentHrData,
            mBpStatus,
            mTempStatus,
            mHrStatus;
    private LineChart mChart;
    private ScrollView mScrollView;
    private LinearLayout mLoadHistoryData;
    private ImageView mBpStatusPic, mHrStatusPic, mTempStatusPic;
    private RefreshLayout refreshLayout;
    //data
    private SingleDataViewModel mSingleDataViewModel;
    private DataViewModel mDataViewModel;
    ;
    private boolean isBpNormal = true, isHrNormal = true, isTempNormal = true;
    //android reuslt api  用于在activity（fragment)间通信
    private ActivityResultLauncher<Intent> mIntentActivityResultLauncher;
    private AlcoholDao mAlcoholDao = DHapplication.mAppDatabase.getAlcoholDao();
    private String mToken;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //registerForActivityResult() 方法注册结果回调（在 onStart() 之前调用）

        mIntentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //向第二个页面拿回数据
                if (result.getResultCode() == 1) {
                    //获取检测到的酒精数据，并存入room
                    Intent data = result.getData();
                    String alcohol = data.getStringExtra("alcohol");
                    String alcoholCreateTime = data.getStringExtra("alcoholCreateTime");
                    mAlcoholConcentration.setText("获取本日酒精浓度：" + alcohol + " %");
                    storageData(alcohol, alcoholCreateTime, 1);
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_page_fragment, container, false);
        mSingleDataViewModel = new ViewModelProvider(this).get(SingleDataViewModel.class);
        mDataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        initView(view);
        setOnCardClickEvent();
        initData();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
                Toasty.success(getActivity(), "加载成功", Toasty.LENGTH_SHORT).show();
                refreshlayout.finishRefresh(1000);
            }
        });


        return view;
    }

    private void initData() {


        mToken = getActivity().getSharedPreferences("data", MODE_PRIVATE).getString("token", "");
        //bp  data
        mSingleDataViewModel.loadSingleBPData(mToken, Config.IMEI).observe(getActivity(), new Observer<SingleBp>() {
            @Override
            public void onChanged(SingleBp singleBp) {
                if (singleBp != null) {
                    updateBpUi(singleBp);
                }
            }
        });
        //hr data
        mSingleDataViewModel.loadSingleHRData(mToken, Config.IMEI).observe(getActivity(), new Observer<SingleHr>() {
            @Override
            public void onChanged(SingleHr singleHr) {
                if (singleHr != null) {
                    updateHrUi(singleHr);
                }
            }
        });

        //temp data
        mSingleDataViewModel.loadSingleTempData(mToken, Config.IMEI).observe(getActivity(), new Observer<SingleTemp>() {
            @Override
            public void onChanged(SingleTemp singleTemp) {
                if (singleTemp != null) {
                    try {
                        updateTempUi(singleTemp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    /**
     * 获取temp数据更新界面
     *
     * @param singleTemp
     */
    private void updateTempUi(SingleTemp singleTemp) throws ParseException {
        float v = Float.parseFloat(singleTemp.getData().getTemperature());
        String created = singleTemp.getData().getCreated();
        if (v > 35.5 && v < 37.0) {

        } else {
            isTempNormal = false;
            mTempStatus.setTextColor(Color.parseColor("#E9832D"));
        }
        if (!isTempNormal) {
            mTempStatus.setText("体温异常");
            mTempStatusPic.setImageResource(R.mipmap.exception_temp);
        }

        mArcProgress.setProgress(v);
        mRecentTempDataCreateTime.setText("体温时间：" + created);


        //24小时内的体温数据
        String startTime = CalendarUtil.getCalCalendar(created, 0, true);
        String endTime = CalendarUtil.getCalCalendar(created, 1, true);
        mDataViewModel.loadTempData(mToken, startTime, endTime, "1", "20", Config.ASC_DATA).observe(getActivity(), new Observer<Temperature>() {
            @Override
            public void onChanged(Temperature temperature) {
                if (!temperature.getData().getResult().isEmpty()) {
                    initChart(temperature.getData().getResult());
                }
            }
        });
    }


    /**
     * 获取hr数据更新界面
     *
     * @param singleHr
     */
    private void updateHrUi(SingleHr singleHr) {
        int heartRate = singleHr.getData().getHeart_rate();
        if (heartRate >= 60 && heartRate <= 100) {
        } else {
            isHrNormal = false;
            mRecentHrData.setTextColor(Color.parseColor("#E9832D"));
        }
        mRecentHrData.setText(singleHr.getData().getHeart_rate() + "");

        if (!isHrNormal) {
            mHrStatusPic.setImageResource(R.mipmap.exception_hr);
            mHrStatus.setText("心率异常");
            mHrStatus.setTextColor(Color.parseColor("#E9832D"));
        }
    }

    /**
     * 获取bp数据更新界面
     *
     * @param singleBp
     */
    private void updateBpUi(SingleBp singleBp) {
        String created = singleBp.getData().getCreated();
        int maxRate = singleBp.getData().getMax_rate();
        int minRate = singleBp.getData().getMin_rate();
        if (maxRate <= 140 && maxRate >= 90) {
        } else {
            isBpNormal = false;
            mRecentBpMaxData.setTextColor(Color.parseColor("#E9832D"));
        }
        if (minRate <= 90 && minRate >= 60) {
        } else {
            isBpNormal = false;
            mRecentBpMinData.setTextColor(Color.parseColor("#E9832D"));
        }

        mRecentBpMaxData.setText(maxRate + "");
        mRecentBpMinData.setText(minRate + "");

        String formatCalendar = null;
        try {
            formatCalendar = CalendarUtil.getFormatCalendar(created, "yyyy-MM-dd HH:mm:ss");
            mRecentHrAndBpDataCreateTime.setText("最近检测：" + formatCalendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (!isBpNormal) {
            mBpStatusPic.setImageResource(R.mipmap.exception_bp);
            mBpStatus.setText("血压异常");
            mBpStatus.setTextColor(Color.parseColor("#E9832D"));
        }

    }

    /**
     * 存取数据
     *
     * @param data              数据
     * @param alcoholCreateTime 数据生成时间
     * @param i                 数据类型：酒精、心率、血压等
     */
    private void storageData(String data, String alcoholCreateTime, int i) {
        if (i == 1) {
            //存储酒精数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Alcohol alcohol = new Alcohol(alcoholCreateTime, Integer.parseInt(data));
                    mAlcoholDao.insertAlcohol(alcohol);
                }
            }).start();
        }
    }


    private void initView(View view) {
        mAlcoholLinearLayout = view.findViewById(R.id.alcohol_data_get);
        mAlcoholConcentration = view.findViewById(R.id.alcohol_concentration_txt);

        mRecentHrAndBpDataCreateTime = view.findViewById(R.id.home_page_recent_data_create_time);
        mLoadHistoryData = view.findViewById(R.id.home_page_history_data);
        mLoadHistoryData.setOnClickListener(this);

        mRecentBpMaxData = view.findViewById(R.id.home_page_recent_bp_max_data);
        mRecentBpMinData = view.findViewById(R.id.home_page_recent_bp_min_data);
        mRecentHrData = view.findViewById(R.id.home_page_recent_hr_data);


        mBpStatusPic = view.findViewById(R.id.home_page_bp_status_pic);
        mHrStatusPic = view.findViewById(R.id.home_page_hr_status_pic);


        mBpStatus = view.findViewById(R.id.home_page_bp_status);
        mHrStatus = view.findViewById(R.id.home_page_hr_status);

        mArcProgress = view.findViewById(R.id.arc_progress);
        mRecentTempDataCreateTime = view.findViewById(R.id.home_page_temp_create_time);


        mTempStatus = view.findViewById(R.id.home_page_recent_temp_status);
        mTempStatusPic = view.findViewById(R.id.home_page_recent_temp_status_pic);

        mChart = view.findViewById(R.id.home_page_line_chart_recent_temp);

        refreshLayout = view.findViewById(R.id.refreshLayout);

        mScrollView = view.findViewById(R.id.scroll_view);
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (refreshLayout != null) {
//                    refreshLayout.setEnableRefresh(mScrollView.getScaleY() == 0);
                    refreshLayout.setEnableLoadMore(mScrollView.getScaleY() == 0);
                }
            }
        });
    }

    /**
     * 卡片点击事件
     */
    private void setOnCardClickEvent() {
        mAlcoholLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTestAlcoholPage();
            }
        });
    }

    /**
     * 跳转到 获取酒精浓度页面
     */
    private void navigateToTestAlcoholPage() {
        mIntentActivityResultLauncher.launch(new Intent(getActivity(), AlcoholActivity.class));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_page_history_data:
                startActivity(new Intent(getActivity(), CenterActivity.class));
                break;

            default:
                break;
        }
    }

    private void initChart(List<Temperature.DataDTO.ResultDTO> result) {
        //prepare data
        List<Entry> tempEntries = new ArrayList<>();//心率
        List<String> date = new ArrayList<>();//日期


        for (int i = 0; i < result.size(); i++) {
            tempEntries.add(new Entry(i, new Double(result.get(i).getTemperature()).floatValue()));
            date.add(result.get(i).getCreated().substring(5, 16));
        }

        date.add("");//解决X轴不匹配问题

        //设置x轴
        XAxis xAxis = mChart.getXAxis();

        xAxis.setDrawGridLines(true);//设置X轴上每个竖线是否显示
        xAxis.setDrawLabels(true);//设置是否绘制X轴上的对应值(标签)
        xAxis.setGranularity(1f); //设置x轴间距
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(date.size());
        xAxis.setLabelRotationAngle(-10); //标签倾斜
        xAxis.setDrawGridLines(false);//是否显示X轴上的网格线
        xAxis.setTextSize(10);
        xAxis.setAvoidFirstLastClipping(false);//是否避免图表或屏幕的边缘的第一个和最后一个轴中的标签条目被裁剪
        //修改X轴内容成字符日期
        xAxis.setValueFormatter(new IndexAxisValueFormatter(date));

        //设置y轴
        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setLabelCount(5);
        yAxis.setTextSize(12);
        yAxis.setDrawGridLines(false); //是否显示Y轴上的网格线
        yAxis.setAxisMaximum(40.0f);
        yAxis.setAxisMinimum(35.0f);
        yAxis.setDrawLabels(true);//绘制y轴标签

        LineDataSet tempDataSet = new LineDataSet(tempEntries, "体温");
        renderLine(tempDataSet, "#DC143C");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(tempDataSet);

        LineData lineData = new LineData(dataSets);
        lineData.setValueFormatter(new CustomerValueFormatter());
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


        mChart.getAxisRight().setEnabled(false); //不绘制右侧轴线
        mChart.setScaleYEnabled(false);
        mChart.setScaleXEnabled(false);

        //设置是否可以触摸
        mChart.setTouchEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);

        //X 轴 换行显示
        mChart.setXAxisRenderer(new CustomXAxisRenderer(mChart.getViewPortHandler(), mChart.getXAxis(), mChart.getTransformer(YAxis.AxisDependency.LEFT)));
        mChart.setExtraRightOffset(30f);
        mChart.getDescription().setText("");
        mChart.getDescription().setTextSize(12);//与图例字体大小一致
        // 设置LineChar间距
        mChart.setExtraBottomOffset(12f);//设置 legend 和 X轴 之间间距
        mChart.invalidate(); // refresh
        //设置从X轴出来的动画时间
        mChart.animateXY(1000, 1000);
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
        dataSet.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.color_line_chart));//设置数据的文本颜色，如果不绘制线的数据 这句代码也不用设置了
        dataSet.setValueTextSize(10f);//如果不绘制线的数据 这句代码也不用设置了
        dataSet.setCircleRadius(4f);//设置每个折线点的大小
        dataSet.setCircleColor(Color.parseColor(color));

    }
}