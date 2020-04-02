package com.android.pehom.thetraining20.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pehom.thetraining20.R;
import com.android.pehom.thetraining20.models.Exercise;

import java.util.List;

public class TrainingDayAdapter extends RecyclerView.Adapter<TrainingDayAdapter.TrainingDayViewHolder> {
    private List<Exercise> data;

    public TrainingDayAdapter(List<Exercise> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TrainingDayViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_day_recyclerview_item, parent,false);
        TrainingDayViewHolder viewHolder = new TrainingDayViewHolder(view);

        int position = viewHolder.getAdapterPosition();

        if (position == RecyclerView.NO_POSITION) position=0;

        {
            final int[] setsDone = {data.get(position).getSetsDone()};

            final int setsNumber = data.get(position).getSetsNumber();
            for (int i=0;i<setsNumber;i++){
                final TextView textView = new TextView(parent.getContext());
                textView.setText(data.get(position).getSet());
                textView.setTextSize((float)20);
                textView.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.colorPrimary));
                if (i< setsDone[0]) {
                    textView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.colorPrimaryDark));
                } else {
                    textView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.colorAccent));
                }
                viewHolder.trainingDayLinearLayout.addView(textView);
          /*  if (i == setsDone[0]) {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView.setTextColor();
                    }
                });
            }*/
            }
        }
      //  final TextView[] setsTextViews = new TextView[setsNumber];
       /* onSetClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setsTextViews[setsDone[0]].setTextColor(ContextCompat.getColor(parent.getContext(), R.color.colorPrimaryDark));
                setsTextViews[setsDone[0]].setClickable(false);
                if (setsDone[0] < setsNumber) {
                    setsDone[0]++;
                    setsTextViews[setsDone[0]].setOnClickListener(onSetClickListener);
                }
            }
        };*/

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingDayViewHolder holder, int position) {
        holder.exerciseTitleTextView.setText(data.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TrainingDayViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseTitleTextView;
        LinearLayout trainingDayLinearLayout;

      //  private View.OnClickListener onSetClickListener;
        public TrainingDayViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseTitleTextView = itemView.findViewById(R.id.exerciseTitleTextView);
            trainingDayLinearLayout = itemView.findViewById(R.id.trainingDayLinearLayout);
        }
    }
}
