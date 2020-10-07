package com.pixelmarketo.ularo.bidderContent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.BidderProfileActivity;
import com.pixelmarketo.ularo.bidderContent.BidderRegistrationActivity;
import com.pixelmarketo.ularo.model.CategoryResult;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BidderServiceAdapter extends RecyclerView.Adapter<BidderServiceAdapter.viewHolder> {

    Context context;
    List<CategoryResult> bids;
    String from;

    public BidderServiceAdapter(Context context, List<CategoryResult> bids, String from) {
        this.context = context;
        this.bids = bids;
        this.from = from;
    }

    @NonNull
    @Override
    public BidderServiceAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bidder_service, parent, false);
        return new BidderServiceAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BidderServiceAdapter.viewHolder holder, int position) {
        holder.tvname.setText(bids.get(position).getCategory_name());
        Glide.with(context).load(bids.get(position).getCategory_image()).into(holder.imgcategory);
        holder.imgcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equalsIgnoreCase("bidderprofile")) {
                    if (holder.verified_img.getVisibility() == View.VISIBLE) {
                        ((BidderProfileActivity) context).remove_category_id(bids.get(position).getCategory_id());
                        holder.verified_img.setVisibility(View.GONE);

                    } else {
                        holder.verified_img.setVisibility(View.VISIBLE);
                        ((BidderProfileActivity) context).get_category_id(bids.get(position).getCategory_id());
                    }
                } else {
                    if (holder.verified_img.getVisibility() == View.VISIBLE) {
                        ((BidderRegistrationActivity) context).remove_category_id(bids.get(position).getCategory_id());
                        holder.verified_img.setVisibility(View.GONE);

                    } else {
                        holder.verified_img.setVisibility(View.VISIBLE);
                        ((BidderRegistrationActivity) context).get_category_id(bids.get(position).getCategory_id());
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return bids.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvname;
        ImageView imgcategory;
        ImageView verified_img;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tvname);
            imgcategory = itemView.findViewById(R.id.imgcategory);
            verified_img = itemView.findViewById(R.id.verified_img);

        }
    }
}
