package com.pixelmarketo.ularo.userContent.adapter;

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
import com.pixelmarketo.ularo.bidderContent.BidderOnCategortySelecActivity;
import com.pixelmarketo.ularo.model.CategoryModel;
import com.pixelmarketo.ularo.model.CategoryResult;
import com.pixelmarketo.ularo.model.SubCategory;
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
import com.pixelmarketo.ularo.userContent.TablayoutActivity;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.SavedData;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.viewHolder> {

    List<SubCategory> list;
    Context context;


    public SubCategoryAdapter(Context context, List<SubCategory> list) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public SubCategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category, parent, false);
        return new SubCategoryAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.viewHolder holder, int position) {
        ErrorMessage.E("here");
       SubCategory subCategory= list.get(position);
        Glide.with(context).load(subCategory.getCategory_image()).into(holder.imgcategory);
        holder.tvcategory.setText(subCategory.getSub_category());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("data",list.get(position).getSub_category());
                SavedData.saveCheckData(list.get(position).getCategory_id());
                ErrorMessage.I(context, TablayoutActivity.class,bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CardView card;
        ImageView imgcategory;
        TextView tvcategory;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgcategory = itemView.findViewById(R.id.imgcategory);
            tvcategory = itemView.findViewById(R.id.tvcategory);
            card = itemView.findViewById(R.id.card);
        }
    }


   
}
