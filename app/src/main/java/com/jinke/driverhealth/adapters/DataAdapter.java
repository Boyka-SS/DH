package com.jinke.driverhealth.adapters;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.beans.BloodPressure;
import com.jinke.driverhealth.beans.HeartRate;
import com.jinke.driverhealth.beans.Temperature;
import com.jinke.driverhealth.views.ZQImageViewRoundOval;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * @author: fanlihao
 * @date: 2022/1/20
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private static final String TAG = "DataAdapter";
    private List<BloodPressure.DataDTO.ResultDTO> mBloodPressureResult = new ArrayList<>();
    private List<HeartRate.DataDTO.ResultDTO> mHeartRateResult = new ArrayList<>();
    private List<Temperature.DataDTO.ResultDTO> mTemperatureResult = new ArrayList<>();


    private int healthState = 0;//健康状况

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.health_data_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //这里设置数据
        holder.itemView.setTag(position);
        Log.d(TAG, position + "");
        holder.setData(mBloodPressureResult.get(position), mTemperatureResult.get(position), mHeartRateResult.get(position));
    }

    @Override
    public int getItemCount() {
        //返回要显示的个数
        int size = mHeartRateResult.size();
        int size1 = mTemperatureResult.size();
        if (size == 0 || size1 == 0) {
            return 10;
        }


        return size <= size1 ? size : size1;
    }

    public void setBloodPressureResult(List<BloodPressure.DataDTO.ResultDTO> bloodPressureResult) {
        if (mBloodPressureResult != null) {
            mBloodPressureResult.clear();
            mBloodPressureResult.addAll(bloodPressureResult);
        }

        //更新UI
        notifyDataSetChanged();
    }

    public void setHeartRateResult(List<HeartRate.DataDTO.ResultDTO> heartRateResult) {

        if (mHeartRateResult != null) {
            mHeartRateResult.clear();
            mHeartRateResult.addAll(heartRateResult);
        }
        //更新UI
        notifyDataSetChanged();
    }

    public void setTemperatureResult(List<Temperature.DataDTO.ResultDTO> temperatureResult) {
        if (mTemperatureResult != null) {
            mTemperatureResult.clear();
            mTemperatureResult.addAll(temperatureResult);
        }
        //更新UI
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, time, temperature, heartRate, hypertension, hypotension, alcohol;

        ZQImageViewRoundOval healthAssessment;

        //模拟酒精浓度数据
        String[] alcoholArr = new String[]{"1.0", "0.1", "0.0", "1.3", "0.3"};


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void setData(BloodPressure.DataDTO.ResultDTO bloodPressureResultDto, Temperature.DataDTO.ResultDTO temperatureResultDto, HeartRate.DataDTO.ResultDTO heartRateResultDto) {

            initBindView();

            String s = bloodPressureResultDto.getCreated();
            StringTokenizer st = new StringTokenizer(s);

            int i = 0;
            String[] strings = new String[2];
            while (st.hasMoreElements()) {
                strings[i] = (String) st.nextElement();
                i++;
            }


            date.setText(strings[0]);
            time.setText(strings[1]);
            temperature.setText("体温：" + temperatureResultDto.getTemperature() + " ℃");
            heartRate.setText("心率：" + heartRateResultDto.getHeart_rate() + " 次/分");
            hypertension.setText("高血压：" + bloodPressureResultDto.getMax_rate() + " mmHg");
            hypotension.setText("低血压：" + bloodPressureResultDto.getMin_rate() + " mmHg");

            //mock 酒精浓度
            String s1 = alcoholArr[new Random().nextInt(5)];
            alcohol.setText("酒精：" + s1 + " g/L");

            healthState = isHealth(temperatureResultDto.getTemperature(), heartRateResultDto.getHeart_rate(), bloodPressureResultDto.getMax_rate(), bloodPressureResultDto.getMin_rate(), s1);

            switch (healthState) {
                case 1:
                    healthAssessment.setImageResource(R.mipmap.good1);
                    break;
                case 2:
                    healthAssessment.setImageResource(R.mipmap.yiban1);
                    break;
                case 3:
                    healthAssessment.setImageResource(R.mipmap.bad1);
                    break;
                default:
                    break;
            }
        }

        /**
         * 返回 健康 状况
         *
         * @param temperature 体温 36~37
         * @param heartRate   心率 60~100 次/分
         * @param maxRate     收缩压
         * @param minRate     舒张压
         * @param s1          酒精浓度  >= 0.8 g/L（醉酒驾驶）
         * @return 2 一般
         */
        private int isHealth(String temperature, int heartRate, int maxRate, int minRate, String s1) {

            if ((0.0 == Double.parseDouble(s1)) &&
                    (maxRate <= 120) &&
                    (minRate >= 80) &&
                    (heartRate < 100) &&
                    (heartRate > 60) &&
                    (Double.parseDouble(temperature) < 37.0) &&
                    (Double.parseDouble(temperature) > 36.0)) {
                return 1;
            }
            if ((0.8 <= Double.parseDouble(s1)) ||
                    ((maxRate >= 120) && (minRate >= 80)) ||
                    (heartRate > 100) ||
                    (heartRate < 60) ||
                    (Double.parseDouble(temperature) >= 37.0) ||
                    (Double.parseDouble(temperature) <= 36.0)) {//如果 酒精浓度超标 直接 不健康
                return 3;
            }
            return 2;
        }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void initBindView() {
            //日期
            date = itemView.findViewById(R.id.item_date);

            //时间
            time = itemView.findViewById(R.id.item_time);
            //体温
            temperature = itemView.findViewById(R.id.item_temperature);
            //心率
            heartRate = itemView.findViewById(R.id.item_heart_rate);
            //高血压
            hypertension = itemView.findViewById(R.id.item_hypertension);
            //低血压
            hypotension = itemView.findViewById(R.id.item_hypotension);
            alcohol = itemView.findViewById(R.id.item_alcohol_concentration);//mock 数据
            healthAssessment = itemView.findViewById(R.id.item_health_assessment);//健康状况
            //设置图片圆角
            healthAssessment.setType(ZQImageViewRoundOval.TYPE_ROUND);
            healthAssessment.setRoundRadius(45);//圆角大小
        }
    }
}
