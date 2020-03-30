package com.android.pehom.thetraining20.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.pehom.thetraining20.R;

import java.util.List;

public class CountAdapter extends RecyclerView.Adapter<CountAdapter.CountViewHolder> {
    private List<Integer> count;

    public CountAdapter(List<Integer> count) {
        this.count = count;
    }

    @NonNull
    @Override
    public CountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.count_recycler_view_item, parent, false);
        CountViewHolder holder = new CountViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountViewHolder holder, int position) {
        holder.countTextView.setText("" + count.get(position));
    }

    @Override
    public int getItemCount() {
        return count.size();
    }

    public static class CountViewHolder extends RecyclerView.ViewHolder{
        TextView countTextView;

        public CountViewHolder(@NonNull View itemView) {
            super(itemView);
            countTextView = itemView.findViewById(R.id.countRecyclerViewItemTextView);
        }
    }
}
