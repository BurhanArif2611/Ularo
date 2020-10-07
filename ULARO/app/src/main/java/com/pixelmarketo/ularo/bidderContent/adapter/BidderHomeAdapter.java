package com.pixelmarketo.ularo.bidderContent.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.BidderHomeActivity;
import com.pixelmarketo.ularo.bidderContent.BidderOnCategortySelecActivity;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

public class BidderHomeAdapter extends RecyclerView.Adapter<BidderHomeAdapter.viewHolder> {

    List<String> list;
    ArrayList<Integer> images;
    Context context;

    public BidderHomeAdapter(Context bidderHomeActivity, List<String> list, ArrayList<Integer> images) {
        this.list = list;
        this.images = images;
        this.context = bidderHomeActivity;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bidding_single_element,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.imageView.setBackgroundResource(images.get(position));
        holder.textView.setText(list.get(position));

        Bundle bundle = new Bundle();
        bundle.putString("title",list.get(position));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorMessage.I(context, BidderOnCategortySelecActivity.class,bundle);
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.bid_title);
            cardView = itemView.findViewById(R.id.card);
        }
    }
}
