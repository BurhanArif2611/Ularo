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

import com.bumptech.glide.Glide;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.BidderHomeActivity;
import com.pixelmarketo.ularo.bidderContent.BidderLoginActivity;
import com.pixelmarketo.ularo.bidderContent.BidderOnCategortySelecActivity;
import com.pixelmarketo.ularo.model.CategoryResult;
import com.pixelmarketo.ularo.userContent.ArchitectureActivity;
import com.pixelmarketo.ularo.userContent.CarpenterActivity;
import com.pixelmarketo.ularo.userContent.ContractorActivity;
import com.pixelmarketo.ularo.userContent.ElectricianActivity;
import com.pixelmarketo.ularo.userContent.GlassWorkActivity;
import com.pixelmarketo.ularo.userContent.PVCWorkActivity;
import com.pixelmarketo.ularo.userContent.PainterActivity;
import com.pixelmarketo.ularo.userContent.PlumberActivity;
import com.pixelmarketo.ularo.userContent.RailingFilterActivity;
import com.pixelmarketo.ularo.userContent.RepairAndMaintainanceActivity;
import com.pixelmarketo.ularo.userContent.SctionFitterActivity;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.SavedData;

import java.util.ArrayList;
import java.util.List;

public class BidderHomeAdapter extends RecyclerView.Adapter<BidderHomeAdapter.viewHolder> {

    List<CategoryResult> list;
    String result,payment_status,packag;
    ArrayList<Integer> images;
    Context context;
    Boolean bidder;
    Bundle bundle;

    public BidderHomeAdapter(Context bidderHomeActivity, List<CategoryResult> list, String result, Boolean bidder) {
        this.list = list;
        this.context = bidderHomeActivity;
        this.bidder = bidder;
        this.result = result;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bidding_single_element, parent, false);
        bundle=new Bundle();
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
      //  ErrorMessage.E("data"+result);
        Glide.with(context).load(list.get(position).getCategory_image()).into(holder.imageView);
        holder.textView.setText(list.get(position).getCategory_name());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bidder) {
                    ErrorMessage.E("true::"+bidder);
                    bidderItemClicked(position);
                } else {
                    ErrorMessage.E("false::"+bidder);
                    userItemClicked(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
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

    public void bidderItemClicked(int position) {

            if (list.get(position).getCategory_id().equals("20")){
                bundle.putString("id",list.get(position).getCategory_id());
                bundle.putString("name",list.get(position).getCategory_name());
                ErrorMessage.I(context, BidderOnCategortySelecActivity.class,bundle); 
            }
            else if (list.get(position).getCategory_id().equals("21")){
                bundle.putString("id",list.get(position).getCategory_id());
                bundle.putString("name",list.get(position).getCategory_name());
                ErrorMessage.I(context, BidderOnCategortySelecActivity.class,bundle);
            }
            else if (list.get(position).getCategory_id().equals("22")){
                bundle.putString("id",list.get(position).getCategory_id());
                bundle.putString("name",list.get(position).getCategory_name());
                ErrorMessage.I(context, BidderOnCategortySelecActivity.class,bundle);
            }
            else if (list.get(position).getCategory_id().equals("23")){
                bundle.putString("id",list.get(position).getCategory_id());
                bundle.putString("name",list.get(position).getCategory_name());
                ErrorMessage.I(context, BidderOnCategortySelecActivity.class,bundle);
            }
            else if (list.get(position).getCategory_id().equals("24")){
                bundle.putString("id",list.get(position).getCategory_id());
                bundle.putString("name",list.get(position).getCategory_name());
                ErrorMessage.I(context, BidderOnCategortySelecActivity.class,bundle);
            }
            else if (list.get(position).getCategory_id().equals("25")){
                bundle.putString("id",list.get(position).getCategory_id());
                bundle.putString("name",list.get(position).getCategory_name());
                ErrorMessage.I(context, BidderOnCategortySelecActivity.class,bundle);
            }
            else if (list.get(position).getCategory_id().equals("26")){
                bundle.putString("id",list.get(position).getCategory_id());
                bundle.putString("name",list.get(position).getCategory_name());
                ErrorMessage.I(context, BidderOnCategortySelecActivity.class,bundle);
            }
            else if (list.get(position).getCategory_id().equals("27")){
                bundle.putString("id",list.get(position).getCategory_id());
                bundle.putString("name",list.get(position).getCategory_name());
                ErrorMessage.I(context, BidderOnCategortySelecActivity.class,bundle);
            }
            else if (list.get(position).getCategory_id().equals("28")){
                bundle.putString("id",list.get(position).getCategory_id());
                bundle.putString("name",list.get(position).getCategory_name());
                ErrorMessage.I(context, BidderOnCategortySelecActivity.class,bundle);
            }
            else if (list.get(position).getCategory_id().equals("29")){
                bundle.putString("id",list.get(position).getCategory_id());
                bundle.putString("name",list.get(position).getCategory_name());
                ErrorMessage.I(context, BidderOnCategortySelecActivity.class,bundle);
            }
            else if (list.get(position).getCategory_id().equals("30")){
                bundle.putString("id",list.get(position).getCategory_id());
                bundle.putString("name",list.get(position).getCategory_name());
                ErrorMessage.I(context, BidderOnCategortySelecActivity.class,bundle);
            }
    }

    public void userItemClicked(int position) {

        ErrorMessage.E("userItemClicked ::"+position);
        if (list.get(position).getCategory_id().equals("20")){
            bundle.putString("id",list.get(position).getCategory_id());
            ErrorMessage.I(context, ArchitectureActivity.class,bundle);
        }
        else if (list.get(position).getCategory_id().equals("21")){
            bundle.putString("id",list.get(position).getCategory_id());
            ErrorMessage.I(context, CarpenterActivity.class,bundle);
        }
        else if (list.get(position).getCategory_id().equals("22")){
            SavedData.saveuserId(list.get(position).getCategory_id());
            bundle.putString("id",list.get(position).getCategory_id());
            bundle.putSerializable("data", list.get(position));
            ErrorMessage.I(context, ElectricianActivity.class,bundle);
        }
        else if (list.get(position).getCategory_id().equals("23")){
            bundle.putString("id",list.get(position).getCategory_id());
            ErrorMessage.I(context, ContractorActivity.class,bundle);
        }
        else if (list.get(position).getCategory_id().equals("24")){
            bundle.putString("id",list.get(position).getCategory_id());
            ErrorMessage.I(context, GlassWorkActivity.class,bundle);
        }
        else if (list.get(position).getCategory_id().equals("25")){
            bundle.putString("id",list.get(position).getCategory_id());
            ErrorMessage.I(context, PainterActivity.class,bundle);
        }
        else if (list.get(position).getCategory_id().equals("26")){
            bundle.putString("id",list.get(position).getCategory_id());
            ErrorMessage.I(context, PlumberActivity.class,bundle);
        }
        else if (list.get(position).getCategory_id().equals("27")){
            bundle.putString("id",list.get(position).getCategory_id());
            ErrorMessage.I(context, PVCWorkActivity.class,bundle);
        }
        else if (list.get(position).getCategory_id().equals("28")){
            bundle.putString("id",list.get(position).getCategory_id());
            ErrorMessage.I(context, RailingFilterActivity.class,bundle);
        }
        else if (list.get(position).getCategory_id().equals("29")){
            bundle.putString("id",list.get(position).getCategory_id());
            ErrorMessage.I(context, RepairAndMaintainanceActivity.class,bundle);
        }
        else if (list.get(position).getCategory_id().equals("30")) {
            bundle.putString("id", list.get(position).getCategory_id());
            ErrorMessage.I(context, SctionFitterActivity.class, bundle);
        }
    }
}
