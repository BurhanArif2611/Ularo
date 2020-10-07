package com.pixelmarketo.ularo.bidderContent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelmarketo.ularo.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BidderSelectionAdapeter extends RecyclerView.Adapter<BidderSelectionAdapeter.viewHolder> {

    Context context;
    List<String> usernames;

    public BidderSelectionAdapeter(Context context, List<String> bids) {
        this.context = context;
        this.usernames = bids;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bidder_bid_select_single_element, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.hideNdShow.getVisibility() == View.VISIBLE) {
                    holder.hideNdShow.setVisibility(View.GONE);
                } else
                    holder.hideNdShow.setVisibility(View.VISIBLE);
            }
        });


        holder.userName.setText(usernames.get(position));

    }

    @Override
    public int getItemCount() {
        return usernames.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        CircleImageView circleImageView;
        TextView userName, dateAndTime, BidNow, StoryManjil, area, workType;
        ImageView arrowImage;
        LinearLayout hideNdShow;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.selc_card);
            circleImageView = itemView.findViewById(R.id.display_image);
            userName = itemView.findViewById(R.id.selec_user_name);
            dateAndTime = itemView.findViewById(R.id.date_and_time_of_post);
            BidNow = itemView.findViewById(R.id.bid_now);
            arrowImage = itemView.findViewById(R.id.arrow_im);
            StoryManjil = itemView.findViewById(R.id.story_manjil);
            area = itemView.findViewById(R.id.area_sqFeet);
            workType = itemView.findViewById(R.id.work_type);
            hideNdShow = itemView.findViewById(R.id.hide_bid);
        }

    }

    public void filterlist(List<String> list) {

        this.usernames = list;
        notifyDataSetChanged();
    }

}
