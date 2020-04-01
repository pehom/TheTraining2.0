package com.android.pehom.thetraining20.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pehom.thetraining20.R;
import com.android.pehom.thetraining20.models.Exercise;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CreateScheduleAdapter extends RecyclerView.Adapter<CreateScheduleAdapter.CreateScheduleViewHolder> {
    private List<Exercise> exercises;
    private OnTitleTouchListener titleTouchListener;
    private OnSetsNumberTouchListener setsNumberTouchListener;
    private OnSetTouchListener setTouchListener;
    private int removedPosition;
    private Exercise removedItem;

    public CreateScheduleAdapter(List<Exercise> exercises, OnTitleTouchListener titleTouchListener, OnSetsNumberTouchListener setsNumberTouchListener, OnSetTouchListener setTouchListener) {
        this.exercises = exercises;
        this.titleTouchListener = titleTouchListener;
        this.setsNumberTouchListener = setsNumberTouchListener;
        this.setTouchListener = setTouchListener;
    }

    public interface OnSetTouchListener {
        void onCountTouch(TextView tv, MotionEvent event, int position);
    }

    public interface OnSetsNumberTouchListener{
        void onSetsNumberTouch(TextView tv, MotionEvent event, int position);
    }

    public interface OnTitleTouchListener{
        void onTitleTouch(RecyclerView.ViewHolder viewHolder, MotionEvent event, int position);
    }

    public class CreateScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseTitleTextView;
        TextView setTextView;
        TextView setsNumberTextView;

        public CreateScheduleViewHolder(@NonNull final View itemView) {
            super(itemView);
            exerciseTitleTextView = itemView.findViewById(R.id.exerciseTextView);
            setTextView = itemView.findViewById(R.id.setTextView);
            setsNumberTextView = itemView.findViewById(R.id.setsNumberTextView);
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
    public void onBindViewHolder(@NonNull final CreateScheduleViewHolder holder, final int position) {

        holder.exerciseTitleTextView.setText(""+ exercises.get(position).getTitle());
        holder.exerciseTitleTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (titleTouchListener != null) {
                    if (position!= RecyclerView.NO_POSITION) {
                        titleTouchListener.onTitleTouch(holder, event, position);
                    }
                }
                return true;
            }
        });
        holder.setTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (setTouchListener != null) {
                    if (position != RecyclerView.NO_POSITION) {
                        setTouchListener.onCountTouch(holder.setTextView, event, position);
                    }
                }
                return true;
            }
        });
        holder.setsNumberTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (setsNumberTouchListener != null) {
                    if (position != RecyclerView.NO_POSITION) {
                        setsNumberTouchListener.onSetsNumberTouch(holder.setsNumberTextView, event, position);
                    }
                }
                return true;
            }
        });
    }

    public void removeItem(final RecyclerView.ViewHolder holder){
        removedPosition = holder.getAdapterPosition();
        removedItem = exercises.get(removedPosition);
        exercises.remove(removedPosition);
        notifyItemRemoved(removedPosition);
        Snackbar.make(holder.itemView, removedItem.getTitle() + " deleted", Snackbar.LENGTH_LONG)
                .setAction(R.string.undo_snackbar, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exercises.add(removedPosition, removedItem);
                        notifyItemInserted(removedPosition);
                        holder.itemView.animate()
                                .x(0)
                                .setDuration(0)
                                .start();
                    }
                }).show();
    }
}
