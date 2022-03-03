package com.jinke.driverhealth.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.data.network.beans.BloodPressure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/3/3
 */
public class BpCenterAdapter extends RecyclerView.Adapter<BpCenterAdapter.ViewHolder> {

    private List<BloodPressure.DataDTO.ResultDTO> mBpList = new ArrayList<>();


    public BpCenterAdapter(List<BloodPressure.DataDTO.ResultDTO> bpList) {
        mBpList = bpList;
    }

    @NonNull
    @Override
    public BpCenterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.center_data_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BpCenterAdapter.ViewHolder holder, int position) {
        BloodPressure.DataDTO.ResultDTO resultDTO = mBpList.get(position);
        String created = resultDTO.getCreated();
        String blood_rate = resultDTO.getBlood_rate();
        int max_rate = resultDTO.getMax_rate();
        int min_rate = resultDTO.getMin_rate();

        if (max_rate > 140 || max_rate < 90 || min_rate > 90 || min_rate < 60) {
            holder.mHealthStatusTxt.setText("血压异常");
            holder.mHealthStatusTxt.setTextColor(Color.parseColor("#FF0000"));

            holder.mHealthStatus.setText("异常");
            holder.mHealthStatus.setBackgroundColor(Color.parseColor("#FF0000"));

            holder.mIsNormal.setText("控制未达标");
            holder.mIsNormal.setTextColor(Color.parseColor("#FF0000"));
        } else {
            holder.mHealthStatusTxt.setText("数值正常");
            holder.mHealthStatusTxt.setTextColor(Color.parseColor("#000000"));

            holder.mHealthStatus.setText("正常");
            holder.mHealthStatus.setBackgroundColor(Color.parseColor("#A7CC26"));

            holder.mIsNormal.setText("控制达标");
            holder.mIsNormal.setTextColor(Color.parseColor("#000000"));
        }


        holder.mDataCenter.setText("血压：" + blood_rate + " mmHg");
        holder.mTime.setText("测量时间：" + created);
    }

    @Override
    public int getItemCount() {
        return mBpList.size();
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
