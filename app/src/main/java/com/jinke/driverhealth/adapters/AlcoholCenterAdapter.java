package com.jinke.driverhealth.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.data.db.beans.Alcohol;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/3/3
 */

public class AlcoholCenterAdapter extends RecyclerView.Adapter<AlcoholCenterAdapter.ViewHolder> {

    private List<Alcohol> mAlcoholList = new ArrayList<>();


    public AlcoholCenterAdapter(List<Alcohol> alcoholList) {
        mAlcoholList = alcoholList;
    }

    @NonNull
    @Override
    public AlcoholCenterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.center_data_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlcoholCenterAdapter.ViewHolder holder, int position) {
        Alcohol resultDTO = mAlcoholList.get(position);
        String created = resultDTO.createdTime;
        int alcohol_concentration = resultDTO.alcohol_concentration;

        if (alcohol_concentration > 20) {
            holder.mHealthStatusTxt.setText("酒精异常");
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


        holder.mDataCenter.setText("酒精：" + alcohol_concentration + " %");
        holder.mTime.setText("测量时间：" + created);
    }

    @Override
    public int getItemCount() {
        return mAlcoholList.size();
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