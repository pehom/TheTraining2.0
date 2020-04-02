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
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.chauthai.swipereveallayout.*;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CreateScheduleAdapter extends RecyclerView.Adapter<CreateScheduleAdapter.CreateScheduleViewHolder> {
    private List<Exercise> exercises;
    private OnTitleTouchListener titleTouchListener;
    private OnSetsNumberTouchListener setsNumberTouchListener;
    private OnSetTouchListener setTouchListener;
    private int removedPosition;
    private Exercise removedItem;

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


    public CreateScheduleAdapter(List<Exercise> exercises, OnTitleTouchListener titleTouchListener, OnSetsNumberTouchListener setsNumberTouchListener, OnSetTouchListener setTouchListener) {
        this.exercises = exercises;
        this.titleTouchListener = titleTouchListener;
        this.setsNumberTouchListener = setsNumberTouchListener;
        this.setTouchListener = setTouchListener;
       viewBinderHelper.setOpenOnlyOne(true);
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
        final CreateScheduleViewHolder viewHolder = new CreateScheduleViewHolder(view);
        viewHolder.exerciseTitleTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (titleTouchListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    if (position!= RecyclerView.NO_POSITION) {
                        titleTouchListener.onTitleTouch(viewHolder, event, position);
                    }
                }
                return true;
            }
        });
        viewHolder.setTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (setTouchListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        setTouchListener.onCountTouch(viewHolder.setTextView, event, position);
                    }
                }
                return true;
            }
        });
        viewHolder.setsNumberTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (setsNumberTouchListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        setsNumberTouchListener.onSetsNumberTouch(viewHolder.setsNumberTextView, event, position);
                    }
                }
                return true;
            }
        });

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final CreateScheduleViewHolder holder, final int position) {

        // Save/restore the open/close state.
        // You need to provide a String id which uniquely defines the data object.
        //viewBinderHelper.bind(holder.swipeRevealLayout, dataObject.getId());
        holder.itemView.setVisibility(View.VISIBLE);
        holder.exerciseTitleTextView.setText(""+ exercises.get(position).getTitle());
        holder.setsNumberTextView.setText(""+exercises.get(position).getSetsNumber());
        holder.setTextView.setText(""+exercises.get(position).getSet());

    }

    public void removeItem(final RecyclerView.ViewHolder holder){
        removedPosition = holder.getAdapterPosition();
        removedItem = exercises.get(removedPosition);
        exercises.remove(removedPosition);
        notifyItemRemoved(removedPosition);
        holder.itemView.setVisibility(View.INVISIBLE);
        holder.itemView.animate()
                .x(0)
                .setDuration(0)
                .start();
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
