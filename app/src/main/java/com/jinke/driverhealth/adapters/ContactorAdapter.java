package com.jinke.driverhealth.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.data.db.beans.Contactor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: fanlihao
 * @date: 2022/2/24
 */
public class ContactorAdapter extends RecyclerView.Adapter<ContactorAdapter.ViewHolder> {

    private List<Contactor> mContactors = new ArrayList<>();


    public ContactorAdapter(List<Contactor> contactors) {
        mContactors = contactors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactor_data_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(mContactors.get(position));
    }

    @Override
    public int getItemCount() {
        return mContactors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mContactorPortrait;
        private TextView mContactorName, mContactorPhone;
        //男女性头像
        int[] portraitsMale = {R.mipmap.african_american_male, R.mipmap.hipster_male, R.mipmap.priest_male, R.mipmap.tourist_male, R.mipmap.student_male};
        int[] portraitsFemale = {R.mipmap.farmer_female, R.mipmap.hipster_female, R.mipmap.person_female, R.mipmap.singer_female, R.mipmap.tourist_male};

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }


        public void setData(Contactor contactor) {
            initBindView();
            mContactorPortrait.setImageResource(portraitsMale[new Random().nextInt(5)]);
            mContactorPhone.setText("电话：" + contactor.getPhone());
            mContactorName.setText("姓名：" + contactor.getName());
        }

        private void initBindView() {
            mContactorPortrait = itemView.findViewById(R.id.contactor_portrait);
            mContactorName = itemView.findViewById(R.id.contactor_name);
            mContactorPhone = itemView.findViewById(R.id.contactor_phone_number);
        }

    }
}
