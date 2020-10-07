package com.pixelmarketo.ularo.userContent.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.model.BidListResult;
import com.pixelmarketo.ularo.userContent.BidsSubmittedDataActivity;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class BidsCategoryAdapter extends RecyclerView.Adapter<BidsCategoryAdapter.viewHolder> {

    Context context;
    List<BidListResult> result;
    private ArrayList<BidListResult> arraylist=null;

    public BidsCategoryAdapter(Context context, List<BidListResult> result) {
        this.context=context;
        this.result=result;
        this.arraylist = new ArrayList<BidListResult>();
        this.arraylist.addAll(result);
    }

    @NonNull
    @Override
    public BidsCategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bids_category, parent, false);
        return new BidsCategoryAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BidsCategoryAdapter.viewHolder holder, int position) {
        holder.tvusername.setText(result.get(position).getCategory());
        holder.tvdatetime.setText(result.get(position).getDate());
        holder.tvmsg.setText(result.get(position).getMessage());
        if (result.get(position).getStatus()==1){
            holder.imgarrow.setVisibility(View.VISIBLE);
        }
        else {
            holder.imgarrow.setVisibility(View.GONE);

        }
        if (result.get(position).getQuick_mode().equalsIgnoreCase("1")){
            holder.imgarrow.setVisibility(View.VISIBLE);
        }
        else {
            holder.imgarrow.setVisibility(View.GONE);

        }
        holder.imgarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("id",result.get(position).getId());
                bundle.putString("category",result.get(position).getCategory());
                bundle.putString("quick_mode",result.get(position).getQuick_mode());
                ErrorMessage.I(context, BidsSubmittedDataActivity.class,bundle);
            }
        });


    }

    @Override
    public int getItemCount() {
        return result.size();
    }
    public void filter(String text) {
        text = text.toLowerCase(Locale.getDefault());
        result.clear();
        if (text.length() == 0) {
            result.addAll(arraylist);
        }
        else
        {
            for (BidListResult wp : arraylist) {
                if (wp.getCategory().toLowerCase(Locale.getDefault()).contains(text)) {
                    result.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvusername,tvdatetime,tvmsg;
        ImageView imgarrow;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvusername=itemView.findViewById(R.id.tvusername);
            tvdatetime=itemView.findViewById(R.id.tvdatetime);
            tvmsg=itemView.findViewById(R.id.tvmsg);
            imgarrow=itemView.findViewById(R.id.imgarrow);

        }
    }


}


