package com.jinke.driverhealth.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.data.db.beans.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/3/3
 */
public class HistoryQuestionItemAdapter extends RecyclerView.Adapter<HistoryQuestionItemAdapter.ViewHolder> {

    private List<Question> mQuestionList = new ArrayList<>();

    public HistoryQuestionItemAdapter(List<Question> questionList) {
        mQuestionList = questionList;
    }

    @NonNull
    @Override
    public HistoryQuestionItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_question_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryQuestionItemAdapter.ViewHolder holder, int position) {
        holder.mQuestionContent.setText(mQuestionList.get(position).content);
        holder.mTime.setText(mQuestionList.get(position).time);
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mQuestionContent, mTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mQuestionContent = itemView.findViewById(R.id.question_content);
            mTime = itemView.findViewById(R.id.question_create_time);
        }
    }
}
