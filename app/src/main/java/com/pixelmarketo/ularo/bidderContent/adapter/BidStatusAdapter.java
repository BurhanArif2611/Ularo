package com.pixelmarketo.ularo.bidderContent.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.WebViewActivity;
import com.pixelmarketo.ularo.model.BidListResult;
import com.pixelmarketo.ularo.userContent.UserHomeActivity;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BidStatusAdapter extends RecyclerView.Adapter<BidStatusAdapter.viewHolder> {

    Context context;
    List<BidListResult> result;
    String from;
    int status;

    public BidStatusAdapter(Context context, List<BidListResult> result, String from, int status) {
        this.context = context;
        this.result = result;
        this.from = from;
        this.status = status;
    }

    @NonNull
    @Override
    public BidStatusAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bid_status, parent, false);

        return new BidStatusAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BidStatusAdapter.viewHolder holder, int position) {
        try {
            if (from.equals("user")) {
                ErrorMessage.E("usre..."+result.get(position).getAngle_type());
                holder.relativestarting.setVisibility(View.GONE);
                holder.relativecomplete.setVisibility(View.GONE);
                holder.relativedays.setVisibility(View.GONE);
                holder.relativebidamount.setVisibility(View.GONE);
                holder.relativenego.setVisibility(View.GONE);
                holder.view.setVisibility(View.GONE);

                holder.tvusername.setText(result.get(position).getUser_name());
                holder.tvdatetime.setText(result.get(position).getCreated_at());
                holder.tvstartingdate.setText(result.get(position).getStart_date());
                holder.tvcompletiondate.setText(result.get(position).getEnd_date());
                holder.totaldays.setText(result.get(position).getTotal_days() + " days");
                holder.bidamount.setText(result.get(position).getBid_amount());
                holder.negotiableamount.setText(result.get(position).getNego_amount());
                holder.area_sqFeet.setText(result.get(position).getArea_in_square_feet());
                holder.work_type.setText(result.get(position).getType_of_work());
                if (result.get(position).getId().equalsIgnoreCase("Contractor")) {
                    if (result.get(position).getMap().equalsIgnoreCase("Yes")) {
                        holder.relativetype.setVisibility(View.VISIBLE);
                        holder.relativesquarefeet.setVisibility(View.VISIBLE);
                        holder.relativestory.setVisibility(View.VISIBLE);
                        holder.maprelative.setVisibility(View.VISIBLE);
                        holder.tvimg.setText("map(naksha)");

                       // Glide.with(context).load(result.get(position).getImage()).into(holder.imgmap);


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
                        holder.relativedesign.setVisibility(View.GONE);
                        holder.repairrelative.setVisibility(View.GONE);
                        holder.relativeservicetype.setVisibility(View.GONE);
                        holder.relativepurpose.setVisibility(View.GONE);
                        holder.relativeangletype.setVisibility(View.GONE);
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
                        holder.relativedesign.setVisibility(View.GONE);
                        holder.repairrelative.setVisibility(View.GONE);
                        holder.relativeservicetype.setVisibility(View.GONE);
                        holder.relativepurpose.setVisibility(View.GONE);
                        holder.relativeangletype.setVisibility(View.GONE);
                    }
                } else if (result.get(position).getCategory().equalsIgnoreCase("Carpenter")) {
                    holder.relativetype.setVisibility(View.VISIBLE);

                    holder.maprelative.setVisibility(View.GONE);
                    holder.relativesquarefeet.setVisibility(View.GONE);
                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativesquarefeet.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);
                    holder.relativestarting.setVisibility(View.GONE);
                    holder.relativecomplete.setVisibility(View.GONE);
                    holder.relativedays.setVisibility(View.GONE);

                } else if (result.get(position).getCategory().equalsIgnoreCase("Glass Work")) {
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.relativetypeofglass.setVisibility(View.VISIBLE);

                    holder.maprelative.setVisibility(View.GONE);
                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);
                } else if (result.get(position).getCategory().equalsIgnoreCase("Painter")) {
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.relativepaintype.setVisibility(View.VISIBLE);
                    holder.relativedimension.setVisibility(View.VISIBLE);
                    holder.relativeLayer.setVisibility(View.VISIBLE);
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);


                    holder.maprelative.setVisibility(View.GONE);
                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);

                } else if (result.get(position).getCategory().equalsIgnoreCase("Plumber")) {
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.relativekitchen.setVisibility(View.VISIBLE);
                    holder.relativebathrooom.setVisibility(View.VISIBLE);
                    holder.relativebasin.setVisibility(View.VISIBLE);
                    holder.relativelatrin.setVisibility(View.VISIBLE);


                    holder.maprelative.setVisibility(View.GONE);
                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativesquarefeet.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);
                } else if (result.get(position).getCategory().equalsIgnoreCase("POP/PVC WORK")) {
                    holder.maprelative.setVisibility(View.VISIBLE);
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.tvimg.setText("design");

                    //  Glide.with(context).load(result.get(position).getImage()).into(holder.imgdesign);



                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativetype.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);

                } else if (result.get(position).getCategory().equalsIgnoreCase("Railing Feeter")) {
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.maprelative.setVisibility(View.VISIBLE);
                    holder.tvimg.setText("image");

                    //  Glide.with(context).load(result.get(position).getImage()).into(holder.imgrailing);


                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);

                } else if (result.get(position).getCategory().equalsIgnoreCase("REPAIR AND MAINTENANCE")) {
                    holder.relativeservicetype.setVisibility(View.VISIBLE);
                    holder.relativedescription.setVisibility(View.VISIBLE);
                    holder.maprelative.setVisibility(View.VISIBLE);
                   // Glide.with(context).load(result.get(position).getImage()).into(holder.imgrepair);
                    holder.tvimg.setText("image");


                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativesquarefeet.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.relativetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);

                } else if (result.get(position).getCategory().equalsIgnoreCase("SECTION FEETER")) {
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.relativepurpose.setVisibility(View.VISIBLE);
                    holder.relativeangletype.setVisibility(View.VISIBLE);


                    holder.maprelative.setVisibility(View.GONE);
                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativetype.setVisibility(View.GONE);


                } else if (result.get(position).getCategory().equalsIgnoreCase("ARCHITECT")) {
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.relativestory.setVisibility(View.VISIBLE);
                    holder.relativetype.setVisibility(View.VISIBLE);


                    holder.maprelative.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.relativedescription.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);

                } else if (result.get(position).getCategory().equalsIgnoreCase("ELECTRICIAN")) {
                    if (result.get(position).getFull_other().equalsIgnoreCase("full")) {
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

                        holder.maprelative.setVisibility(View.GONE);
                        holder.relativestory.setVisibility(View.GONE);
                        holder.relativekitchen.setVisibility(View.GONE);
                        holder.relativebathrooom.setVisibility(View.GONE);
                        holder.relativelatrin.setVisibility(View.GONE);
                        holder.relativehall.setVisibility(View.GONE);
                        holder.relativepaintype.setVisibility(View.GONE);
                        holder.relativedimension.setVisibility(View.GONE);
                        holder.relativeLayer.setVisibility(View.GONE);
                        holder.relativesquarefeet.setVisibility(View.GONE);
                        holder.relativetypeofglass.setVisibility(View.GONE);
                        holder.relativebasin.setVisibility(View.GONE);
                        holder.relativerailling.setVisibility(View.GONE);
                        holder.relativedesign.setVisibility(View.GONE);
                        holder.repairrelative.setVisibility(View.GONE);
                        holder.relativeservicetype.setVisibility(View.GONE);
                        holder.relativepurpose.setVisibility(View.GONE);
                        holder.relativeangletype.setVisibility(View.GONE);

                    } else {
                        holder.relativesquarefeet.setVisibility(View.VISIBLE);
                        holder.relative6A.setVisibility(View.VISIBLE);
                        holder.relative16A.setVisibility(View.VISIBLE);
                        holder.relativemcb.setVisibility(View.VISIBLE);
                        holder.relativetype.setVisibility(View.VISIBLE);
                        holder.relativesubcat.setVisibility(View.VISIBLE);
                        holder.relativesubtype.setVisibility(View.VISIBLE);
                        holder.relativestarting.setVisibility(View.VISIBLE);
                        holder.relativecomplete.setVisibility(View.VISIBLE);
                        holder.relativedays.setVisibility(View.VISIBLE);


                        holder.maprelative.setVisibility(View.GONE);
                        holder.relativestory.setVisibility(View.GONE);
                        holder.relativekitchen.setVisibility(View.GONE);
                        holder.relativebathrooom.setVisibility(View.GONE);
                        holder.relativelatrin.setVisibility(View.GONE);
                        holder.relativehall.setVisibility(View.GONE);
                        holder.relativepaintype.setVisibility(View.GONE);
                        holder.relativedimension.setVisibility(View.GONE);
                        holder.relativeLayer.setVisibility(View.GONE);
                        holder.relativepoint.setVisibility(View.GONE);
                        holder.relativeroom.setVisibility(View.GONE);
                        holder.relativetotalpoint.setVisibility(View.GONE);
                        holder.relativetypeofglass.setVisibility(View.GONE);
                        holder.relativebasin.setVisibility(View.GONE);
                        holder.relativerailling.setVisibility(View.GONE);
                        holder.relativedesign.setVisibility(View.GONE);
                        holder.repairrelative.setVisibility(View.GONE);
                        holder.relativeservicetype.setVisibility(View.GONE);
                        holder.relativepurpose.setVisibility(View.GONE);
                        holder.relativeangletype.setVisibility(View.GONE);

                    }
                }
            } else {
                if (status==1){
                    holder.tvmobile.setVisibility(View.VISIBLE);
                }

                holder.tvmobile.setText(result.get(position).getContact());

                holder.tvusername.setText(result.get(position).getUser_name());
                holder.tvdatetime.setText(result.get(position).getCreated_at());
                holder.tvstartingdate.setText(result.get(position).getStart_date());
                holder.tvcompletiondate.setText(result.get(position).getEnd_date());
                holder.totaldays.setText(result.get(position).getTotal_days() + " days");
                holder.bidamount.setText(result.get(position).getBid_amount());
                holder.negotiableamount.setText(result.get(position).getNego_amount());
                holder.area_sqFeet.setText(result.get(position).getArea_in_square_feet());
                holder.work_type.setText(result.get(position).getType_of_work());
                if (result.get(position).getCategory().equalsIgnoreCase("CONTRACTOR")) {
                    if (result.get(position).getMap().equalsIgnoreCase("Yes")) {
                        holder.relativetype.setVisibility(View.VISIBLE);
                        holder.relativesquarefeet.setVisibility(View.VISIBLE);
                        holder.relativestory.setVisibility(View.VISIBLE);
                        holder.maprelative.setVisibility(View.VISIBLE);
                       // Glide.with(context).load(result.get(position).getImage()).into(holder.imgmap);
                        holder.relativestarting.setVisibility(View.VISIBLE);
                        holder.relativecomplete.setVisibility(View.VISIBLE);
                        holder.relativedays.setVisibility(View.VISIBLE);
                        holder.tvimg.setText("map(naksha)");


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
                        holder.relativedesign.setVisibility(View.GONE);
                        holder.repairrelative.setVisibility(View.GONE);
                        holder.relativeservicetype.setVisibility(View.GONE);
                        holder.relativepurpose.setVisibility(View.GONE);
                        holder.relativeangletype.setVisibility(View.GONE);
                    } else {
                        holder.relativetype.setVisibility(View.VISIBLE);
                        holder.relativesquarefeet.setVisibility(View.VISIBLE);
                        holder.relativestory.setVisibility(View.VISIBLE);
                        holder.relativekitchen.setVisibility(View.VISIBLE);
                        holder.relativeroom.setVisibility(View.VISIBLE);
                        holder.relativebathrooom.setVisibility(View.VISIBLE);
                        holder.relativelatrin.setVisibility(View.VISIBLE);
                        holder.relativehall.setVisibility(View.VISIBLE);
                        holder.relativestarting.setVisibility(View.VISIBLE);
                        holder.relativecomplete.setVisibility(View.VISIBLE);
                        holder.relativedays.setVisibility(View.VISIBLE);

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
                        holder.relativedesign.setVisibility(View.GONE);
                        holder.repairrelative.setVisibility(View.GONE);
                        holder.relativeservicetype.setVisibility(View.GONE);
                        holder.relativepurpose.setVisibility(View.GONE);
                        holder.relativeangletype.setVisibility(View.GONE);
                    }
                } else if (result.get(position).getCategory().equalsIgnoreCase("CARPENTER")) {
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.tvbid.setText("bid amount(in %)");

                    holder.maprelative.setVisibility(View.GONE);
                    holder.relativesquarefeet.setVisibility(View.GONE);
                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativesquarefeet.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);
                    holder.relativestarting.setVisibility(View.GONE);
                    holder.relativecomplete.setVisibility(View.GONE);
                    holder.relativedays.setVisibility(View.GONE);

                } else if (result.get(position).getCategory().equalsIgnoreCase("GLASS WORK")) {
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.relativetypeofglass.setVisibility(View.VISIBLE);
                    holder.relativestarting.setVisibility(View.VISIBLE);
                    holder.relativecomplete.setVisibility(View.VISIBLE);
                    holder.relativedays.setVisibility(View.VISIBLE);

                    holder.maprelative.setVisibility(View.GONE);
                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);
                } else if (result.get(position).getCategory().equalsIgnoreCase("PAINTER")) {
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.relativepaintype.setVisibility(View.VISIBLE);
                    holder.relativedimension.setVisibility(View.VISIBLE);
                    holder.relativeLayer.setVisibility(View.VISIBLE);
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.relativestarting.setVisibility(View.VISIBLE);
                    holder.relativecomplete.setVisibility(View.VISIBLE);
                    holder.relativedays.setVisibility(View.VISIBLE);


                    holder.maprelative.setVisibility(View.GONE);
                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);

                } else if (result.get(position).getCategory().equalsIgnoreCase("PLUMBER")) {
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.relativekitchen.setVisibility(View.VISIBLE);
                    holder.relativebathrooom.setVisibility(View.VISIBLE);
                    holder.relativebasin.setVisibility(View.VISIBLE);
                    holder.relativelatrin.setVisibility(View.VISIBLE);
                    holder.relativestarting.setVisibility(View.VISIBLE);
                    holder.relativecomplete.setVisibility(View.VISIBLE);
                    holder.relativedays.setVisibility(View.VISIBLE);

                    holder.maprelative.setVisibility(View.GONE);
                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativesquarefeet.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);
                } else if (result.get(position).getCategory().equalsIgnoreCase("POP/PVC WORK")) {
                    holder.maprelative.setVisibility(View.VISIBLE);
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                  //  Glide.with(context).load(result.get(position).getImage()).into(holder.imgdesign);
                    holder.relativestarting.setVisibility(View.VISIBLE);
                    holder.relativecomplete.setVisibility(View.VISIBLE);
                    holder.relativedays.setVisibility(View.VISIBLE);
                    holder.tvimg.setText("design");


                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativetype.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);

                } else if (result.get(position).getCategory().equalsIgnoreCase("RAILING FEETER")) {
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.maprelative.setVisibility(View.VISIBLE);
                 //   Glide.with(context).load(result.get(position).getImage()).into(holder.imgrailing);
                    holder.relativestarting.setVisibility(View.VISIBLE);
                    holder.relativecomplete.setVisibility(View.VISIBLE);
                    holder.relativedays.setVisibility(View.VISIBLE);
                    holder.tvimg.setText("image");


                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);

                } else if (result.get(position).getCategory().equalsIgnoreCase("REPAIR AND MAINTENANCE")) {
                    holder.relativeservicetype.setVisibility(View.VISIBLE);
                    holder.relativedescription.setVisibility(View.VISIBLE);
                    holder.maprelative.setVisibility(View.VISIBLE);
                  //  Glide.with(context).load(result.get(position).getImage()).into(holder.imgrepair);
                    holder.relativestarting.setVisibility(View.VISIBLE);
                    holder.relativecomplete.setVisibility(View.VISIBLE);
                    holder.relativedays.setVisibility(View.VISIBLE);
                    holder.tvimg.setText("image");


                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativesquarefeet.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.relativetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);

                } else if (result.get(position).getCategory().equalsIgnoreCase("SECTION FEETER")) {
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.relativepurpose.setVisibility(View.VISIBLE);
                    holder.relativeangletype.setVisibility(View.VISIBLE);
                    holder.relativestarting.setVisibility(View.VISIBLE);
                    holder.relativecomplete.setVisibility(View.VISIBLE);
                    holder.relativedays.setVisibility(View.VISIBLE);

                    holder.maprelative.setVisibility(View.GONE);
                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativetype.setVisibility(View.GONE);


                } else if (result.get(position).getCategory().equalsIgnoreCase("ARCHITECT")) {
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.relativestory.setVisibility(View.VISIBLE);
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.relativestarting.setVisibility(View.VISIBLE);
                    holder.relativecomplete.setVisibility(View.VISIBLE);
                    holder.relativedays.setVisibility(View.VISIBLE);

                    holder.maprelative.setVisibility(View.GONE);
                    holder.relativedesign.setVisibility(View.GONE);
                    holder.repairrelative.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
                    holder.relativebathrooom.setVisibility(View.GONE);
                    holder.relativelatrin.setVisibility(View.GONE);
                    holder.relativehall.setVisibility(View.GONE);
                    holder.relative6A.setVisibility(View.GONE);
                    holder.relative16A.setVisibility(View.GONE);
                    holder.relativemcb.setVisibility(View.GONE);
                    holder.relativesubcat.setVisibility(View.GONE);
                    holder.relativesubtype.setVisibility(View.GONE);
                    holder.relativepaintype.setVisibility(View.GONE);
                    holder.relativedimension.setVisibility(View.GONE);
                    holder.relativeLayer.setVisibility(View.GONE);
                    holder.relativepoint.setVisibility(View.GONE);
                    holder.relativetotalpoint.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
                    holder.relativerailling.setVisibility(View.GONE);
                    holder.relativedescription.setVisibility(View.GONE);
                    holder.relativeservicetype.setVisibility(View.GONE);
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);

                } else if (result.get(position).getCategory().equalsIgnoreCase("ELECTRICIAN")) {
                    if (result.get(position).getFull_other().equalsIgnoreCase("full")) {
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
                        holder.tvstartingdate.setVisibility(View.VISIBLE);
                        holder.tvcompletiondate.setVisibility(View.VISIBLE);
                        holder.totaldays.setVisibility(View.VISIBLE);

                        holder.maprelative.setVisibility(View.GONE);
                        holder.relativestory.setVisibility(View.GONE);
                        holder.relativekitchen.setVisibility(View.GONE);
                        holder.relativebathrooom.setVisibility(View.GONE);
                        holder.relativelatrin.setVisibility(View.GONE);
                        holder.relativehall.setVisibility(View.GONE);
                        holder.relativepaintype.setVisibility(View.GONE);
                        holder.relativedimension.setVisibility(View.GONE);
                        holder.relativeLayer.setVisibility(View.GONE);
                        holder.relativesquarefeet.setVisibility(View.GONE);
                        holder.relativetypeofglass.setVisibility(View.GONE);
                        holder.relativebasin.setVisibility(View.GONE);
                        holder.relativerailling.setVisibility(View.GONE);
                        holder.relativedesign.setVisibility(View.GONE);
                        holder.repairrelative.setVisibility(View.GONE);
                        holder.relativeservicetype.setVisibility(View.GONE);
                        holder.relativepurpose.setVisibility(View.GONE);
                        holder.relativeangletype.setVisibility(View.GONE);

                    } else {
                        holder.relativesquarefeet.setVisibility(View.VISIBLE);
                        holder.relative6A.setVisibility(View.VISIBLE);
                        holder.relative16A.setVisibility(View.VISIBLE);
                        holder.relativemcb.setVisibility(View.VISIBLE);
                        holder.relativetype.setVisibility(View.VISIBLE);
                        holder.relativesubcat.setVisibility(View.VISIBLE);
                        holder.relativesubtype.setVisibility(View.VISIBLE);
                        holder.relativestarting.setVisibility(View.VISIBLE);
                        holder.relativecomplete.setVisibility(View.VISIBLE);
                        holder.relativedays.setVisibility(View.VISIBLE);


                        holder.maprelative.setVisibility(View.GONE);
                        holder.relativestory.setVisibility(View.GONE);
                        holder.relativekitchen.setVisibility(View.GONE);
                        holder.relativebathrooom.setVisibility(View.GONE);
                        holder.relativelatrin.setVisibility(View.GONE);
                        holder.relativehall.setVisibility(View.GONE);
                        holder.relativepaintype.setVisibility(View.GONE);
                        holder.relativedimension.setVisibility(View.GONE);
                        holder.relativeLayer.setVisibility(View.GONE);
                        holder.relativepoint.setVisibility(View.GONE);
                        holder.relativeroom.setVisibility(View.GONE);
                        holder.relativetotalpoint.setVisibility(View.GONE);
                        holder.relativetypeofglass.setVisibility(View.GONE);
                        holder.relativebasin.setVisibility(View.GONE);
                        holder.relativerailling.setVisibility(View.GONE);
                        holder.relativedesign.setVisibility(View.GONE);
                        holder.repairrelative.setVisibility(View.GONE);
                        holder.relativeservicetype.setVisibility(View.GONE);
                        holder.relativepurpose.setVisibility(View.GONE);
                        holder.relativeangletype.setVisibility(View.GONE);

                    }
                }
            }
            if (result.get(position).getImage().contains("pdf")) {
                Glide.with(context).load(R.drawable.ic_file).into(holder.imgmap);
            } else {
                Glide.with(context).load(result.get(position).getImage()).placeholder(R.drawable.ic_attach).into(holder.imgmap);
            }
            holder.story_manjil.setText(result.get(position).getStory_building());
            holder.area_sqFeet.setText(result.get(position).getArea_in_square_feet());
            holder.work_type.setText(result.get(position).getType_of_work());
            holder.glass_type.setText(result.get(position).getType_of_glass());
            holder.paint_type.setText(result.get(position).getPaint_type());
            holder.layer.setText(result.get(position).getLayer());
            holder.dimension.setText(result.get(position).getDimension());
            holder.kitchen.setText(result.get(position).getNo_of_kitchen());
            holder.bathroom.setText(result.get(position).getNo_of_bathroom());
            holder.basin.setText(result.get(position).getNo_of_basin());
            holder.latrin.setText(result.get(position).getNo_of_latrine());
            holder.service_type.setText(result.get(position).getService_type());
            holder.description.setText(result.get(position).getDescription());
            holder.purpose.setText(result.get(position).getPurpose());
            holder.tvusername.setText(result.get(position).getUser_name());
            holder.tvdatetime.setText(result.get(position).getCreated_at());
            holder.point.setText(result.get(position).getNo_of_point());
            holder.room.setText(result.get(position).getNo_of_room());
            holder.total_point.setText(result.get(position).getTotal_point());
            holder.point6a.setText(result.get(position).getTotal_point_6a());
            holder.point16a.setText(result.get(position).getTotal_point_16a());
            holder.mcb.setText(result.get(position).getNo_of_mcb());
            holder.category.setText(result.get(position).getSub_cat_name());
            holder.type.setText(result.get(position).getFull_other());
            holder.tvcategory.setText(result.get(position).getCategory());
            holder.hall.setText(result.get(position).getNo_of_hall());
            holder.angletype.setText(result.get(position).getAngle_type());


            Glide.with(context).load(result.get(position).getPro_img()).placeholder(R.drawable.ic_user).into(holder.display_image);
            // showImage(result.get(position).getImage());

            holder.arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.hide.getVisibility() == View.VISIBLE) {
                        Glide.with(context).load(R.drawable.ic_down_arrow_1).into(holder.imgarrow);
                        holder.hide.setVisibility(View.GONE);
                        holder.hide_bid.setVisibility(View.GONE);
                        holder.view.setVisibility(View.GONE);
                        if (from.equals("user")) {
                            holder.view.setVisibility(View.GONE);

                        }
                    } else {
                        Glide.with(context).load(R.drawable.ic_up_arrow).into(holder.imgarrow);
                        holder.hide.setVisibility(View.VISIBLE);
                        holder.hide_bid.setVisibility(View.VISIBLE);
                        holder.view.setVisibility(View.VISIBLE);
                        if (from.equals("user")) {
                            holder.view.setVisibility(View.GONE);

                        }
                    }
                }
            });

            holder.imgmap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("path", result.get(position).getImage());
                    ErrorMessage.I(context, WebViewActivity.class, bundle);

                }
            });

        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return result.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView display_image;
        TextView tvusername, tvdatetime, story_manjil, area_sqFeet, work_type, tvstartingdate, tvcompletiondate, totaldays, bidamount, negotiableamount, glass_type, paint_type, layer, dimension, kitchen, bathroom, basin, latrin, angletype,
        tvimg,tvmobile, tvbid,service_type, description, purpose, point, room, total_point, point6a, point16a, mcb, category, type, tvcategory, hall;
        LinearLayout hide, hide_bid;
        RelativeLayout arrow;
        ImageView imgarrow, imgmap, imgdesign, imgrepair, imgrailing;
        View view;
        RelativeLayout maprelative, relativestory, relativesquarefeet, relativetype, relativetypeofglass, relativepaintype, relativeLayer, relativedimension,
                relativekitchen, relativebathrooom, relativebasin, relativelatrin, relativedesign, relativeservicetype, relativedescription, relativepurpose,
                relativepoint, relativeroom, relativetotalpoint, relative6A, relative16A, relativemcb, relativesubcat, relativesubtype, repairrelative, relativehall, relativeangletype, relativerailling,
                relativestarting, relativecomplete, relativedays, relativebidamount, relativenego;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            display_image = itemView.findViewById(R.id.display_image);
            tvusername = itemView.findViewById(R.id.tvusername);
            tvdatetime = itemView.findViewById(R.id.tvdatetime);
            story_manjil = itemView.findViewById(R.id.story_manjil);
            area_sqFeet = itemView.findViewById(R.id.area_sqFeet);
            work_type = itemView.findViewById(R.id.work_type);
            tvstartingdate = itemView.findViewById(R.id.tvstartingdate);
            tvcompletiondate = itemView.findViewById(R.id.tvcompletiondate);
            totaldays = itemView.findViewById(R.id.totaldays);
            bidamount = itemView.findViewById(R.id.bidamount);
            negotiableamount = itemView.findViewById(R.id.negotiableamount);
            hide = itemView.findViewById(R.id.hide);
            arrow = itemView.findViewById(R.id.arrow);
            imgarrow = itemView.findViewById(R.id.imgarrow);
            imgmap = itemView.findViewById(R.id.imgmap);
            imgdesign = itemView.findViewById(R.id.imgdesign);
            hide_bid = itemView.findViewById(R.id.hide_bid);
            view = itemView.findViewById(R.id.view);
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
            tvcategory = itemView.findViewById(R.id.tvcategory);
            relativehall = itemView.findViewById(R.id.relativehall);
            hall = itemView.findViewById(R.id.hall);
            angletype = itemView.findViewById(R.id.angletype);
            relativeangletype = itemView.findViewById(R.id.relativeangletype);
            repairrelative = itemView.findViewById(R.id.repairrelative);
            imgrepair = itemView.findViewById(R.id.imgrepair);
            imgrailing = itemView.findViewById(R.id.imgrailing);
            relativerailling = itemView.findViewById(R.id.relativerailling);
            relativedays = itemView.findViewById(R.id.relativedays);
            relativestarting = itemView.findViewById(R.id.relativestarting);
            relativecomplete = itemView.findViewById(R.id.relativecomplete);
            relativenego = itemView.findViewById(R.id.relativenego);
            relativebidamount = itemView.findViewById(R.id.relativebidamount);
            tvbid = itemView.findViewById(R.id.tvbid);
            tvimg = itemView.findViewById(R.id.tvimg);
            tvmobile = itemView.findViewById(R.id.tvmobile);
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

