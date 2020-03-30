package com.android.pehom.thetraining20.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pehom.thetraining20.R;
import com.android.pehom.thetraining20.models.Exercise;

import java.util.List;

public class CreateScheduleAdapter extends RecyclerView.Adapter<CreateScheduleAdapter.CreateScheduleViewHolder> {
    private List<Exercise> exercises;
    private OnCountTouchListener listener;

    public CreateScheduleAdapter(List<Exercise> exercises, OnCountTouchListener listener) {
        this.exercises = exercises;
        this.listener = listener;
    }

    public interface OnCountTouchListener{
        void onCountTouch(TextView tv, MotionEvent event, int position);
    }

    public class CreateScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseTitleTextView;
        TextView holderCountTextView;

        public CreateScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseTitleTextView = itemView.findViewById(R.id.exerciseTextView);
            holderCountTextView = itemView.findViewById(R.id.countTextView);
            holderCountTextView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onCountTouch(holderCountTextView, event, position);
                        }
                    }
                    return true;

                }
            });
        }
    }

    @NonNull
    @Override
    public CreateScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.create_schedule_recycler_view_item, parent, false);
        CreateScheduleViewHolder viewHolder = new CreateScheduleViewHolder(view);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final CreateScheduleViewHolder holder, int position) {

        holder.exerciseTitleTextView.setText(""+ exercises.get(position).getTitle());

    }

}
