package com.jinke.driverhealth.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.beans.BloodPressure;
import com.jinke.driverhealth.beans.HealthData;
import com.jinke.driverhealth.beans.HeartRate;
import com.jinke.driverhealth.beans.Temperature;

import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/1/20
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {


    private HealthData mHealthData;

    private List<BloodPressure.DataDTO.ResultDTO> mBloodPressureResult;
    private List<HeartRate.DataDTO.ResultDTO> mHeartRateResult;
    private List<Temperature.DataDTO.ResultDTO> mTemperatureResult;

    //血压数据
    public void setBloodPressure(BloodPressure bloodPressure) {
        mHealthData.setBloodPressure(bloodPressure);
    }

    //心率数据
    public void setHeartRate(HeartRate heartRate) {
        mHealthData.setHeartRate(heartRate);
    }

    //体温数据
    public void setTemperature(Temperature temperature) {
        mHealthData.setTemperature(temperature);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.health_data_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //这里设置数据
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        int l1 = mHealthData.getBloodPressure().getData().getTotal();
        int l2 = mHealthData.getTemperature().getData().getTotal();
        return l1 <= l2 ? l1 : l2;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, time, temperature, heartRate, hypertension, hypotension, alcohol;

        ImageView healthAssessment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            healthAssessment = itemView.findViewById(R.id.item_health_assessment);
            date = itemView.findViewById(R.id.item_date);
            time = itemView.findViewById(R.id.item_time);
            temperature = itemView.findViewById(R.id.item_temperature);
            heartRate = itemView.findViewById(R.id.item_heart_rate);
            hypertension = itemView.findViewById(R.id.item_hypertension);
            hypotension = itemView.findViewById(R.id.item_hypotension);
            alcohol = itemView.findViewById(R.id.item_alcohol_concentration);
        }
    }
}
