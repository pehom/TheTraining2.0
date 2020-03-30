package com.android.pehom.thetraining20.adapters;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pehom.thetraining20.R;

import java.sql.Array;
import java.util.ArrayList;

public class PullupsCountAdapter extends RecyclerView.Adapter<PullupsCountAdapter.PullupsCountViewHolder> {
    private ArrayList<Integer> pullupsCount;

    public PullupsCountAdapter(ArrayList<Integer> pullupsCount){
        this.pullupsCount = pullupsCount;
    }

    public static class PullupsCountViewHolder extends RecyclerView.ViewHolder {
        public TextView pullupsCountTextView1;
//        public TextView pullupsCountTextView2;
//        public TextView pullupsCountTextView3;

        public PullupsCountViewHolder(@NonNull View itemView) {
            super(itemView);
            pullupsCountTextView1 = itemView.findViewById(R.id.pullupsCountRecyclerViewItemTextView1);
//            pullupsCountTextView2 = itemView.findViewById(R.id.pullupsCountRecyclerViewItemTextView2);
//            pullupsCountTextView3 = itemView.findViewById(R.id.pullupsCountRecyclerViewItemTextView3);

        }
    }

    @NonNull
    @Override
    public PullupsCountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pullups_count_recycler_view_item, parent, false);
        PullupsCountViewHolder viewHolder = new PullupsCountViewHolder(view);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return pullupsCount.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PullupsCountViewHolder holder, int position) {
        holder.pullupsCountTextView1.setText(""+ pullupsCount.get(position));
//        if (position > 0 && position < pullupsCount.size()-1) {
//            holder.pullupsCountTextView1.setText(""+pullupsCount.get(position-1));
//            holder.pullupsCountTextView2.setText(""+pullupsCount.get(position));
//            holder.pullupsCountTextView3.setText(""+pullupsCount.get(position+1));
//        } else if (position == 0) {
//            holder.pullupsCountTextView1.setText(""+pullupsCount.get(0));
//            holder.pullupsCountTextView2.setText(""+pullupsCount.get(1));
//            holder.pullupsCountTextView3.setText(""+pullupsCount.get(2));
//        } else if (position == pullupsCount.size()-1 ) {
//            holder.pullupsCountTextView1.setText(""+pullupsCount.get(position-2));
//            holder.pullupsCountTextView2.setText(""+pullupsCount.get(position-1));
//            holder.pullupsCountTextView3.setText(""+pullupsCount.get(position));
//        }
    }




}
