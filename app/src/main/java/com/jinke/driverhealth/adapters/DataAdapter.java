package com.jinke.driverhealth.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.data.network.tudingyun.beans.BloodPressure;
import com.jinke.driverhealth.data.network.tudingyun.beans.HeartRate;
import com.jinke.driverhealth.data.network.tudingyun.beans.Temperature;
import com.jinke.driverhealth.utils.CalendarUtil;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/3/1
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {


    private static final String TAG = "DataAdapter";

    private List<BloodPressure.DataDTO.ResultDTO> mBloodPressureResult = new ArrayList<>();
    private List<HeartRate.DataDTO.ResultDTO> mHeartRateResult = new ArrayList<>();
    private List<Temperature.DataDTO.ResultDTO> mTemperatureResult = new ArrayList<>();


    private Context mContext;
    private List<String> mMeasureTime;

    public DataAdapter(Context context) {
        mContext = context;

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
        holder.setData(mBloodPressureResult.get(position), mTemperatureResult.get(position), mHeartRateResult.get(position));

    }

    /**
     * @param bpRes
     * @param hrRes
     * @param holder
     * @param position
     * @throws ParseException 日期解析异常
     */
    private void refactorData(List<BloodPressure.DataDTO.ResultDTO> bpRes, List<HeartRate.DataDTO.ResultDTO> hrRes, ViewHolder holder, int position) throws ParseException {
        mMeasureTime = new ArrayList<>();

        Iterator<BloodPressure.DataDTO.ResultDTO> iterator = bpRes.iterator();
        while (iterator.hasNext()) {
            String created = iterator.next().getCreated();
            String beginTime = CalendarUtil.getCalCalendar(created, 0, true);
            String endTime = CalendarUtil.getCalCalendar(created, 1, true);

            if (!mMeasureTime.contains(beginTime)) {
                mMeasureTime.add(created);
            }

        }


    }

    @Override
    public int getItemCount() {
        //返回要显示的个数
        int hr = mHeartRateResult.size();
        int temp = mTemperatureResult.size();
        return hr <= temp ? hr : temp;
    }

    public void setBloodPressureResult(List<BloodPressure.DataDTO.ResultDTO> bloodPressureResult) {
        if (mBloodPressureResult != null) {
            mBloodPressureResult.clear();
            mBloodPressureResult.addAll(bloodPressureResult);
        }

        //一定要记得加，否则视图不会更新！！！
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

        private TextView mCreateTime;
        private SwipeRecyclerView mSwipeRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCreateTime = itemView.findViewById(R.id.measurement_create_time);

        }

        public void setData(BloodPressure.DataDTO.ResultDTO bloodPressureResultDto, Temperature.DataDTO.ResultDTO temperatureResultDto, HeartRate.DataDTO.ResultDTO heartRateResultDto) {

        }
    }
}
