package com.pixelmarketo.ularo.bidderContent.adapter;

import android.app.Dialog;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.WebViewActivity;
import com.pixelmarketo.ularo.bidderContent.BiddingActivity;
import com.pixelmarketo.ularo.model.BidListResult;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class BidderSelectionAdapeter extends RecyclerView.Adapter<BidderSelectionAdapeter.viewHolder> {

    Context context;
    List<BidListResult> resullt;
    private ArrayList<BidListResult> arraylist = null;

    String id, from, filter_city;

    public BidderSelectionAdapeter(Context context, List<BidListResult> bids, String id, String from) {
        this.context = context;
        this.resullt = bids;
        this.id = id;
        this.from = from;
        this.arraylist = new ArrayList<BidListResult>();
        this.arraylist.addAll(resullt);
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bidder_bid_select_single_element, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ErrorMessage.E("from" + id);
        if (id.equals("23"))//CONTRACTOR
            {
                if (resullt.get(position).getMap().equalsIgnoreCase("Yes")) {
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.relativestory.setVisibility(View.VISIBLE);
                    holder.maprelative.setVisibility(View.VISIBLE);
                    //   Glide.with(context).load(resullt.get(position).getImage()).into(holder.imgmap);
                    holder.tvimage.setText("map(naksha)");

                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);

                } else {
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.relativestory.setVisibility(View.VISIBLE);
                    holder.relativekitchen.setVisibility(View.VISIBLE);
                    holder.relativeroom.setVisibility(View.VISIBLE);
                    holder.relativebathrooom.setVisibility(View.VISIBLE);
                    holder.relativelatrin.setVisibility(View.VISIBLE);
                    holder.relativehall.setVisibility(View.VISIBLE);

                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.maprelative.setVisibility(View.GONE);
                }
            } else if (id.equals("21"))//carpenter
            {
                holder.relativetype.setVisibility(View.VISIBLE);
            } else if (id.equals("24"))//GLASS
            {
                holder.relativetype.setVisibility(View.VISIBLE);
                holder.relativesquarefeet.setVisibility(View.VISIBLE);
                holder.relativetypeofglass.setVisibility(View.VISIBLE);
            } else if (id.equals("25"))//Painter
            {
                holder.relativetype.setVisibility(View.VISIBLE);
                holder.relativepaintype.setVisibility(View.VISIBLE);
                holder.relativedimension.setVisibility(View.VISIBLE);
                holder.relativeLayer.setVisibility(View.VISIBLE);
                holder.relativesquarefeet.setVisibility(View.VISIBLE);
            } else if (id.equals("26"))//Plumber
            {
                holder.relativetype.setVisibility(View.VISIBLE);
                holder.relativekitchen.setVisibility(View.VISIBLE);
                holder.relativebathrooom.setVisibility(View.VISIBLE);
                holder.relativebasin.setVisibility(View.VISIBLE);
                holder.relativelatrin.setVisibility(View.VISIBLE);
                holder.relativetotaljob.setVisibility(View.VISIBLE);

                holder.total_job.setText(resullt.get(position).getTotal_job_work());
                ErrorMessage.E("12345"+resullt.get(position).getTotal_job_work());
            } else if (id.equals("27"))//POP/PVC WORK
            {
                holder.tvimage.setText("design");

                holder.maprelative.setVisibility(View.VISIBLE);
                holder.relativesquarefeet.setVisibility(View.VISIBLE);
                //  Glide.with(context).load(resullt.get(position).getImage()).into(holder.imgdesign);

            } else if (id.equals("28"))//Railing Fitter
            {
                holder.tvimage.setText("image");

                holder.relativerunningfeet.setVisibility(View.VISIBLE);
                holder.relativetype.setVisibility(View.VISIBLE);
                holder.maprelative.setVisibility(View.VISIBLE);
                //  Glide.with(context).load(resullt.get(position).getImage()).into(holder.imgrailing);

            } else if (id.equals("29"))//Repair & Maintenance
            {
                holder.tvimage.setText("image");
                holder.BidNow.setVisibility(View.GONE);
                holder.relativeservicetype.setVisibility(View.VISIBLE);
                holder.relativedescription.setVisibility(View.VISIBLE);
                holder.maprelative.setVisibility(View.VISIBLE);
                // Glide.with(context).load(resullt.get(position).getImage()).into(holder.imgrepair);

            } else if (id.equals("30"))//SECTION FEETER
            {
                holder.relativesquarefeet.setVisibility(View.VISIBLE);
                holder.relativepurpose.setVisibility(View.VISIBLE);
                holder.relativeangletype.setVisibility(View.VISIBLE);
            } else if (id.equals("20"))//ARCHITECTURE
            {
                holder.relativesquarefeet.setVisibility(View.VISIBLE);
                holder.relativestory.setVisibility(View.VISIBLE);
                holder.relativetype.setVisibility(View.VISIBLE);
            } else if (id.equals("22"))//ELECTRICIAN
            {
                if (resullt.get(position).getFull_other().equalsIgnoreCase("full")) {
                    holder.relativepoint.setVisibility(View.VISIBLE);
                    holder.relativeroom.setVisibility(View.VISIBLE);
                    holder.relativetotalpoint.setVisibility(View.VISIBLE);
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.relativesubcat.setVisibility(View.VISIBLE);
                    holder.relativesubtype.setVisibility(View.VISIBLE);
                    holder.relativesquarefeet.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                } else {
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.relative6A.setVisibility(View.VISIBLE);
                    holder.relative16A.setVisibility(View.VISIBLE);
                    holder.relativemcb.setVisibility(View.VISIBLE);
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.relativesubcat.setVisibility(View.VISIBLE);
                    holder.relativesubtype.setVisibility(View.VISIBLE);
                    holder.relativetotalpoint.setVisibility(View.VISIBLE);
                }
            }
            if (resullt.get(position).getImage().contains("pdf")) {
                Glide.with(context).load(R.drawable.ic_file).into(holder.imgmap);
            } else {
                Glide.with(context).load(resullt.get(position).getImage()).placeholder(R.drawable.ic_attach).into(holder.imgmap);
            }
            holder.StoryManjil.setText(resullt.get(position).getStory_building());
            holder.area.setText(resullt.get(position).getArea_in_square_feet());
            holder.workType.setText(resullt.get(position).getType_of_work());
            holder.glass_type.setText(resullt.get(position).getType_of_glass());
            holder.paint_type.setText(resullt.get(position).getPaint_type());
            holder.layer.setText(resullt.get(position).getLayer());
            holder.dimension.setText(resullt.get(position).getDimension());
            holder.kitchen.setText(resullt.get(position).getNo_of_kitchen());
            holder.bathroom.setText(resullt.get(position).getNo_of_bathroom());
            holder.basin.setText(resullt.get(position).getNo_of_basin());
            holder.latrin.setText(resullt.get(position).getNo_of_latrine());
            holder.service_type.setText(resullt.get(position).getService_type());
            holder.description.setText(resullt.get(position).getDescription());
            holder.purpose.setText(resullt.get(position).getPurpose());
            holder.userName.setText(resullt.get(position).getUser_name());
            holder.dateAndTime.setText(resullt.get(position).getCreated_at());
            holder.point.setText(resullt.get(position).getNo_of_point());
            holder.room.setText(resullt.get(position).getNo_of_room());
            holder.total_point.setText(resullt.get(position).getTotal_point());
            holder.point6a.setText(resullt.get(position).getTotal_point_6a());
            holder.point16a.setText(resullt.get(position).getTotal_point_16a());
            holder.mcb.setText(resullt.get(position).getNo_of_mcb());
            holder.category.setText(resullt.get(position).getSub_cat_name());
            holder.type.setText(resullt.get(position).getFull_other());
            holder.hall.setText(resullt.get(position).getNo_of_hall());
            holder.angletype.setText(resullt.get(position).getAngle_type());
            holder.runningfeet.setText(resullt.get(position).getRunning_feet());

            holder.arrowImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.hideNdShow.getVisibility() == View.VISIBLE) {
                        Glide.with(context).load(R.drawable.ic_down_arrow_1).into(holder.arrowImage);
                        holder.hideNdShow.setVisibility(View.GONE);

                    } else {
                        holder.hideNdShow.setVisibility(View.VISIBLE);
                        Glide.with(context).load(R.drawable.ic_up_arrow).into(holder.arrowImage);
                    }
                }
            });
            holder.BidNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", from);
                    bundle.putString("id", resullt.get(position).getBid_id());
                    ErrorMessage.I(context, BiddingActivity.class, bundle);
                }
            });

            holder.imgmap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("path", resullt.get(position).getImage());
                    ErrorMessage.I(context, WebViewActivity.class, bundle);
                }
            });


    }

    @Override
    public int getItemCount() {
        return resullt.size();
    }

    public void filter(String text) {
        text = text.toLowerCase(Locale.getDefault());
        resullt.clear();
        if (text.length() == 0) {
            resullt.addAll(arraylist);
        } else {
            for (BidListResult wp : arraylist) {
                if (wp.getCity().toLowerCase(Locale.getDefault()).contains(text)) {
                    resullt.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        CircleImageView circleImageView;
        TextView userName, dateAndTime, BidNow, StoryManjil, area, workType, glass_type, paint_type, layer, dimension, kitchen, bathroom, basin, latrin,
        tvtotal,  tvimage, service_type, description, purpose, point, room, total_point, point6a, point16a, mcb, category, type, hall, angletype, runningfeet,
                total_job;
        ImageView arrowImage, imgrepair, imgmap, imgdesign, imgrailing;
        LinearLayout hideNdShow;
        RelativeLayout maprelative, relativestory, relativesquarefeet, relativetype, relativetypeofglass, relativepaintype, relativeLayer, relativedimension,
                relativekitchen, relativebathrooom, relativebasin, relativelatrin, relativedesign, relativeservicetype, relativedescription, relativepurpose,
        relativetotaljob,    relativepoint, relativeroom, relativetotalpoint, relative6A, relative16A, relativemcb, relativesubcat, relativesubtype, relativehall, relativeangletype, repairrelative, relativerunningfeet, relativerailling;
        View view;

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
            maprelative = itemView.findViewById(R.id.maprelative);
            relativestory = itemView.findViewById(R.id.relativestory);
            relativesquarefeet = itemView.findViewById(R.id.relativesquarefeet);
            relativetype = itemView.findViewById(R.id.relativetype);
            relativetypeofglass = itemView.findViewById(R.id.relativetypeofglass);
            glass_type = itemView.findViewById(R.id.glass_type);
            paint_type = itemView.findViewById(R.id.paint_type);
            layer = itemView.findViewById(R.id.layer);
            dimension = itemView.findViewById(R.id.dimension);
            relativepaintype = itemView.findViewById(R.id.relativepaintype);
            relativeLayer = itemView.findViewById(R.id.relativeLayer);
            relativedimension = itemView.findViewById(R.id.relativedimension);
            relativekitchen = itemView.findViewById(R.id.relativekitchen);
            relativebathrooom = itemView.findViewById(R.id.relativebathrooom);
            relativebasin = itemView.findViewById(R.id.relativebasin);
            relativelatrin = itemView.findViewById(R.id.relativelatrin);
            kitchen = itemView.findViewById(R.id.kitchen);
            bathroom = itemView.findViewById(R.id.bathroom);
            basin = itemView.findViewById(R.id.basin);
            latrin = itemView.findViewById(R.id.latrin);
            relativedesign = itemView.findViewById(R.id.relativedesign);
            description = itemView.findViewById(R.id.description);
            service_type = itemView.findViewById(R.id.service_type);
            relativeservicetype = itemView.findViewById(R.id.relativeservicetype);
            relativedescription = itemView.findViewById(R.id.relativedescription);
            purpose = itemView.findViewById(R.id.purpose);
            relativepurpose = itemView.findViewById(R.id.relativepurpose);
            view = itemView.findViewById(R.id.view);
            relativepoint = itemView.findViewById(R.id.relativepoint);
            relativeroom = itemView.findViewById(R.id.relativeroom);
            relativetotalpoint = itemView.findViewById(R.id.relativetotalpoint);
            relative6A = itemView.findViewById(R.id.relative6A);
            relative16A = itemView.findViewById(R.id.relative16A);
            relativemcb = itemView.findViewById(R.id.relativemcb);
            point = itemView.findViewById(R.id.point);
            room = itemView.findViewById(R.id.room);
            total_point = itemView.findViewById(R.id.total_point);
            point6a = itemView.findViewById(R.id.point6a);
            point16a = itemView.findViewById(R.id.point16a);
            mcb = itemView.findViewById(R.id.mcb);
            relativesubcat = itemView.findViewById(R.id.relativesubcat);
            relativesubtype = itemView.findViewById(R.id.relativesubtype);
            category = itemView.findViewById(R.id.category);
            type = itemView.findViewById(R.id.type);
            relativehall = itemView.findViewById(R.id.relativehall);
            hall = itemView.findViewById(R.id.hall);
            angletype = itemView.findViewById(R.id.angletype);
            relativeangletype = itemView.findViewById(R.id.relativeangletype);
            repairrelative = itemView.findViewById(R.id.repairrelative);
            imgrepair = itemView.findViewById(R.id.imgrepair);
            imgmap = itemView.findViewById(R.id.imgmap);
            runningfeet = itemView.findViewById(R.id.runningfeet);
            relativerunningfeet = itemView.findViewById(R.id.relativerunningfeet);
            imgdesign = itemView.findViewById(R.id.imgdesign);
            imgrailing = itemView.findViewById(R.id.imgrailing);
            relativerailling = itemView.findViewById(R.id.relativerailling);
            tvimage = itemView.findViewById(R.id.tvimage);
            tvtotal = itemView.findViewById(R.id.tvtotal);
            total_job = itemView.findViewById(R.id.total_job);
            relativetotaljob = itemView.findViewById(R.id.relativetotaljob);
        }
    }

    public void showpopup(String image) {
        final Dialog nagDialog = new Dialog(context);
        nagDialog.setContentView(R.layout.preview_image);
        nagDialog.setCanceledOnTouchOutside(false);
        nagDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Button btnClose = (Button) nagDialog.findViewById(R.id.btnIvClose);
        ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.iv_preview_image);

        // ivPreview.setImageDrawable();
        Glide.with(context).load(image).into(ivPreview);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                nagDialog.dismiss();
            }
        });
        nagDialog.show();
    }

}
