package com.jinke.driverhealth.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.data.network.tudingyun.beans.Temperature;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/3/3
 */

/**
 * @author: fanlihao
 * @date: 2022/3/3
 */

public class TempCenterAdapter extends RecyclerView.Adapter<TempCenterAdapter.ViewHolder> {

    private List<Temperature.DataDTO.ResultDTO> mTempList = new ArrayList<>();


    public TempCenterAdapter(List<Temperature.DataDTO.ResultDTO> tempList) {
        mTempList = tempList;
    }

    @NonNull
    @Override
    public TempCenterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.center_data_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TempCenterAdapter.ViewHolder holder, int position) {
        Temperature.DataDTO.ResultDTO resultDTO = mTempList.get(position);

        double v = Double.parseDouble(resultDTO.getTemperature());


        holder.mDataCenter.setText("体温：" + v + " ℃");
        holder.mTime.setText("测量时间：" + resultDTO.getCreated());

        if (v >= 35.5 && v <= 37.5) {
            holder.mHealthStatusTxt.setText("数值正常");
            holder.mHealthStatusTxt.setTextColor(Color.parseColor("#000000"));

            holder.mHealthStatus.setText("正常");
            holder.mHealthStatus.setBackgroundColor(Color.parseColor("#A7CC26"));

            holder.mIsNormal.setText("控制达标");
            holder.mIsNormal.setTextColor(Color.parseColor("#000000"));

        } else {
            holder.mHealthStatusTxt.setText("体温异常");
            holder.mHealthStatusTxt.setTextColor(Color.parseColor("#FF0000"));

            holder.mHealthStatus.setText("异常");
            holder.mHealthStatus.setBackgroundColor(Color.parseColor("#FF0000"));

            holder.mIsNormal.setText("控制未达标");
            holder.mIsNormal.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    @Override
    public int getItemCount() {
        return mTempList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mDataCenter, mTime, mHealthStatusTxt, mHealthStatus, mIsNormal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDataCenter = itemView.findViewById(R.id.data_health_center);
            mTime = itemView.findViewById(R.id.measurement_time);
            mHealthStatusTxt = itemView.findViewById(R.id.status_health_txt);
            mHealthStatus = itemView.findViewById(R.id.status_health);
            mIsNormal = itemView.findViewById(R.id.is_normal);
        }
    }
}
