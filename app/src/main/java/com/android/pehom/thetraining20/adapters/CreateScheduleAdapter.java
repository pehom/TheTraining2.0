package com.android.pehom.thetraining20.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pehom.thetraining20.R;
import com.android.pehom.thetraining20.models.Exercise;

import java.util.List;

public class CreateScheduleAdapter extends RecyclerView.Adapter<CreateScheduleAdapter.CreateScheduleViewHolder> {
    private List<Exercise> exercises;
    private RecyclerView countRecyclerView;

    public CreateScheduleAdapter(List<Exercise> exercises, RecyclerView countRecyclerView) {
        this.exercises = exercises;
        this.countRecyclerView = countRecyclerView;
    }

    public static class CreateScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseTitleTextView;
        RecyclerView holderCountRecyclerView;

        public CreateScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseTitleTextView = itemView.findViewById(R.id.exerciseTextView);
            holderCountRecyclerView = itemView.findViewById(R.id.countRecyclerView);
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
    public void onBindViewHolder(@NonNull CreateScheduleViewHolder holder, int position) {
        holder.exerciseTitleTextView.setText(""+ exercises.get(position).getTitle());
        holder.holderCountRecyclerView = countRecyclerView;
    }

}
