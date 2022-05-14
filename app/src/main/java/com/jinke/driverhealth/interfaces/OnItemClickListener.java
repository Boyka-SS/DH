package com.jinke.driverhealth.interfaces;

/**
 * @author: fanlihao
 * @date: 2022/1/22
 */

import android.view.View;

import com.jinke.driverhealth.data.network.tudingyun.beans.BloodPressure;
import com.jinke.driverhealth.data.network.tudingyun.beans.HeartRate;
import com.jinke.driverhealth.data.network.tudingyun.beans.Temperature;

/**
 * RecyclerView item 点击事件
 */
public interface OnItemClickListener {


    /**
     * 长按
     *
     * @param view
     * @param position
     */
    void onItemLongClick(View view, int position);

    /**
     * 点击
     * @param itemView
     * @param pos
     * @param bpResDTO
     * @param tempResDTO
     * @param hrResDTO
     * @param mockAlcoholData
     */
    void onItemClick(View itemView, int pos, BloodPressure.DataDTO.ResultDTO bpResDTO, Temperature.DataDTO.ResultDTO tempResDTO, HeartRate.DataDTO.ResultDTO hrResDTO, String mockAlcoholData,String createDate);
}
