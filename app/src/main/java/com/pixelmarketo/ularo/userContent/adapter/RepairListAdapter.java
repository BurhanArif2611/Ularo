package com.pixelmarketo.ularo.userContent.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.model.BidListResult;
import com.pixelmarketo.ularo.model.RepairListResult;
import com.pixelmarketo.ularo.userContent.BidsSubmittedDataActivity;
import com.pixelmarketo.ularo.userContent.RepairMaintenanceListActivity;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class RepairListAdapter extends RecyclerView.Adapter<RepairListAdapter.viewHolder> {

    Context context;
    List<RepairListResult> result;
    private ArrayList<RepairListResult> arraylist=null;

    public RepairListAdapter(Context context, List<RepairListResult> result) {
        this.context=context;
        this.result=result;

    }

    public RepairListAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public RepairListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repair_list, parent, false);
        return new RepairListAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepairListAdapter.viewHolder holder, int position) {
        holder.tvusername.setText(result.get(position).getFname());
        holder.tvmobile.setText(result.get(position).getMobile());
       /* holder.tvmsg.setText(result.get(position).getMessage());
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
        });*/

    }

    @Override
    public int getItemCount() {
        return result.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvusername,tvmobile;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvusername=itemView.findViewById(R.id.tvusername);
            tvmobile=itemView.findViewById(R.id.tvmobile);


        }
    }


}


