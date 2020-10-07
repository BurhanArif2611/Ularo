package com.pixelmarketo.ularo.bidderContent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelmarketo.ularo.R;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.viewHlder> {

    Context context;

    public OrderHistoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public viewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_single_element,parent,false);

        return new viewHlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHlder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class viewHlder extends RecyclerView.ViewHolder{

        public viewHlder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
