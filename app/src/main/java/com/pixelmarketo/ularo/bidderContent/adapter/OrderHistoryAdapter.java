package com.pixelmarketo.ularo.bidderContent.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.WebViewActivity;
import com.pixelmarketo.ularo.bidderContent.BidderHomeActivity;
import com.pixelmarketo.ularo.bidderContent.BidderLoginActivity;
import com.pixelmarketo.ularo.bidderContent.BiddingActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.database.UserProfileModel;
import com.pixelmarketo.ularo.model.BidListResult;
import com.pixelmarketo.ularo.userContent.UserHomeActivity;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.SavedData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.viewHlder> {

    Context context;
    List<BidListResult> result;
    String from, user_id, bidder_id, rating, comment;

    public OrderHistoryAdapter(Context context, List<BidListResult> result, String from) {
        this.context = context;
        this.result = result;
        this.from = from;
    }

    @NonNull
    @Override
    public viewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_single_element, parent, false);

        return new viewHlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHlder holder, int position) {
        ErrorMessage.E("" + result.get(position).getUser_name());
        try {

            holder.tvusername.setText(result.get(position).getUser_name());
            holder.tvdatetime.setText(result.get(position).getCreated_at());
            holder.tvstartingdate.setText(result.get(position).getStart_date());
            holder.tvcompletiondate.setText(result.get(position).getEnd_date());
            holder.totaldays.setText(result.get(position).getTotal_days() + " days");
            holder.bidamount.setText(result.get(position).getBid_amount());
            holder.negotiableamount.setText(result.get(position).getNego_amount());
            holder.area_sqFeet.setText(result.get(position).getArea_in_square_feet());
            holder.work_type.setText(result.get(position).getType_of_work());
            holder.contact.setText(result.get(position).getContact());
            if (result.get(position).getCategory().equalsIgnoreCase("CONTRACTOR")) {
                if (result.get(position).getMap().equalsIgnoreCase("Yes")) {
                    holder.relativetype.setVisibility(View.VISIBLE);
                    holder.relativesquarefeet.setVisibility(View.VISIBLE);
                    holder.relativestory.setVisibility(View.VISIBLE);
                    holder.maprelative.setVisibility(View.VISIBLE);
                    holder.tvimg.setText("map(naksha)");
                    holder.relativestarting.setVisibility(View.VISIBLE);
                    holder.relativecomplete.setVisibility(View.VISIBLE);
                    holder.relativedays.setVisibility(View.VISIBLE);
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
                holder.relativeroom.setVisibility(View.GONE);
                holder.relativetotalpoint.setVisibility(View.GONE);
                holder.relativetypeofglass.setVisibility(View.GONE);
                holder.relativebasin.setVisibility(View.GONE);
                holder.relativepurpose.setVisibility(View.GONE);
                holder.relativeangletype.setVisibility(View.GONE);
                holder.tvstartingdate.setVisibility(View.GONE);
                holder.tvcompletiondate.setVisibility(View.GONE);
                holder.totaldays.setVisibility(View.GONE);

            } else if (result.get(position).getCategory().equalsIgnoreCase("GLASS WORK")) {
                holder.relativetype.setVisibility(View.VISIBLE);
                holder.relativesquarefeet.setVisibility(View.VISIBLE);
                holder.relativetypeofglass.setVisibility(View.VISIBLE);
                holder.relativecomplete.setVisibility(View.VISIBLE);
                holder.relativestarting.setVisibility(View.VISIBLE);
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
                holder.relativeroom.setVisibility(View.GONE);
                holder.relativetotalpoint.setVisibility(View.GONE);
                holder.relativebasin.setVisibility(View.GONE);
                holder.relativepurpose.setVisibility(View.GONE);
                holder.relativeangletype.setVisibility(View.GONE);

            } else if (result.get(position).getCategory().equalsIgnoreCase("PAINTER")) {
                holder.relativetype.setVisibility(View.VISIBLE);
                holder.relativepaintype.setVisibility(View.VISIBLE);
                holder.relativedimension.setVisibility(View.VISIBLE);
                holder.relativeLayer.setVisibility(View.VISIBLE);
                holder.relativesquarefeet.setVisibility(View.VISIBLE);
                holder.relativecomplete.setVisibility(View.VISIBLE);
                holder.relativestarting.setVisibility(View.VISIBLE);
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
                holder.relativeroom.setVisibility(View.GONE);
                holder.relativetotalpoint.setVisibility(View.GONE);
                holder.relativetypeofglass.setVisibility(View.GONE);
                holder.relativebasin.setVisibility(View.GONE);
                holder.relativepurpose.setVisibility(View.GONE);
                holder.relativeangletype.setVisibility(View.GONE);

            } else if (result.get(position).getCategory().equalsIgnoreCase("PLUMBER")) {
                holder.relativetype.setVisibility(View.VISIBLE);
                holder.relativekitchen.setVisibility(View.VISIBLE);
                holder.relativebathrooom.setVisibility(View.VISIBLE);
                holder.relativebasin.setVisibility(View.VISIBLE);
                holder.relativelatrin.setVisibility(View.VISIBLE);
                holder.relativecomplete.setVisibility(View.VISIBLE);
                holder.relativestarting.setVisibility(View.VISIBLE);
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
                holder.relativeroom.setVisibility(View.GONE);
                holder.relativetotalpoint.setVisibility(View.GONE);
                holder.relativetypeofglass.setVisibility(View.GONE);
                holder.relativepurpose.setVisibility(View.GONE);
                holder.relativeangletype.setVisibility(View.GONE);

            } else if (result.get(position).getCategory().equalsIgnoreCase("POP/PVC WORK")) {
                holder.relativesquarefeet.setVisibility(View.VISIBLE);
                holder.relativecomplete.setVisibility(View.VISIBLE);
                holder.relativestarting.setVisibility(View.VISIBLE);
                holder.relativedays.setVisibility(View.VISIBLE);
                holder.maprelative.setVisibility(View.VISIBLE);
                holder.tvimg.setText("design");

                holder.relativestory.setVisibility(View.GONE);
                holder.relativetype.setVisibility(View.GONE);
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
                holder.relativeroom.setVisibility(View.GONE);
                holder.relativetotalpoint.setVisibility(View.GONE);
                holder.relativetypeofglass.setVisibility(View.GONE);
                holder.relativebasin.setVisibility(View.GONE);
                holder.relativepurpose.setVisibility(View.GONE);
                holder.relativeangletype.setVisibility(View.GONE);

            } else if (result.get(position).getCategory().equalsIgnoreCase("RAILING FEETER")) {
                holder.relativesquarefeet.setVisibility(View.VISIBLE);
                holder.relativetype.setVisibility(View.VISIBLE);
                holder.relativerailling.setVisibility(View.VISIBLE);
                holder.maprelative.setVisibility(View.VISIBLE);
                holder.tvimg.setText("image");
                holder.relativecomplete.setVisibility(View.VISIBLE);
                holder.relativestarting.setVisibility(View.VISIBLE);
                holder.relativedays.setVisibility(View.VISIBLE);

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
                holder.relativeroom.setVisibility(View.GONE);
                holder.relativetotalpoint.setVisibility(View.GONE);
                holder.relativetypeofglass.setVisibility(View.GONE);
                holder.relativebasin.setVisibility(View.GONE);
                holder.relativepurpose.setVisibility(View.GONE);
                holder.relativeangletype.setVisibility(View.GONE);

            } else if (result.get(position).getCategory().equalsIgnoreCase("Repair & Maintenance")) {
                holder.relativeservicetype.setVisibility(View.VISIBLE);
                holder.relativedescription.setVisibility(View.VISIBLE);
                holder.repairrelative.setVisibility(View.VISIBLE);
                holder.maprelative.setVisibility(View.VISIBLE);
                holder.tvimg.setText("image");
                holder.relativecomplete.setVisibility(View.VISIBLE);
                holder.relativestarting.setVisibility(View.VISIBLE);
                holder.relativedays.setVisibility(View.VISIBLE);

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
                holder.relativeroom.setVisibility(View.GONE);
                holder.relativetotalpoint.setVisibility(View.GONE);
                holder.relativetypeofglass.setVisibility(View.GONE);
                holder.relativebasin.setVisibility(View.GONE);
                holder.relativepurpose.setVisibility(View.GONE);
                holder.relativeangletype.setVisibility(View.GONE);

            } else if (result.get(position).getCategory().equalsIgnoreCase("SECTION FEETER")) {
                holder.relativesquarefeet.setVisibility(View.VISIBLE);
                holder.relativepurpose.setVisibility(View.VISIBLE);
                holder.relativeangletype.setVisibility(View.VISIBLE);
                holder.relativecomplete.setVisibility(View.VISIBLE);
                holder.relativestarting.setVisibility(View.VISIBLE);
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
                holder.relativeroom.setVisibility(View.GONE);
                holder.relativetotalpoint.setVisibility(View.GONE);
                holder.relativetypeofglass.setVisibility(View.GONE);
                holder.relativebasin.setVisibility(View.GONE);
                holder.work_type.setVisibility(View.GONE);
                holder.relativetype.setVisibility(View.GONE);

            } else if (result.get(position).getCategory().equalsIgnoreCase("ARCHITECT")) {
                holder.relativesquarefeet.setVisibility(View.VISIBLE);
                holder.relativestory.setVisibility(View.VISIBLE);
                holder.relativetype.setVisibility(View.VISIBLE);
                holder.relativecomplete.setVisibility(View.VISIBLE);
                holder.relativestarting.setVisibility(View.VISIBLE);
                holder.relativedays.setVisibility(View.VISIBLE);

                holder.maprelative.setVisibility(View.GONE);
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
                holder.relativeroom.setVisibility(View.GONE);
                holder.relativetotalpoint.setVisibility(View.GONE);
                holder.relativetypeofglass.setVisibility(View.GONE);
                holder.relativebasin.setVisibility(View.GONE);
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
                    holder.relativecomplete.setVisibility(View.VISIBLE);
                    holder.relativestarting.setVisibility(View.VISIBLE);
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
                    holder.relativesquarefeet.setVisibility(View.GONE);
                    holder.relativetypeofglass.setVisibility(View.GONE);
                    holder.relativebasin.setVisibility(View.GONE);
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
                    holder.relativecomplete.setVisibility(View.VISIBLE);
                    holder.relativestarting.setVisibility(View.VISIBLE);
                    holder.relativedays.setVisibility(View.VISIBLE);
                    holder.maprelative.setVisibility(View.GONE);
                    holder.relativestory.setVisibility(View.GONE);
                    holder.relativekitchen.setVisibility(View.GONE);
                    holder.relativeroom.setVisibility(View.GONE);
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
                    holder.relativepurpose.setVisibility(View.GONE);
                    holder.relativeangletype.setVisibility(View.GONE);

                }
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
            holder.imgarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.hide.getVisibility() == View.VISIBLE) {
                        Glide.with(context).load(R.drawable.ic_down_arrow_1).into(holder.imgarrow);
                        holder.hide.setVisibility(View.GONE);
                        holder.hide_bid.setVisibility(View.GONE);
                        holder.view.setVisibility(View.GONE);
                    } else {
                        Glide.with(context).load(R.drawable.ic_up_arrow).into(holder.imgarrow);
                        holder.hide.setVisibility(View.VISIBLE);
                        holder.hide_bid.setVisibility(View.VISIBLE);
                        holder.view.setVisibility(View.VISIBLE);
                    }
                }
            });
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user_id = result.get(position).getUser_id();
                    bidder_id = result.get(position).getBid_id();
                    ErrorMessage.E("bidder_id" + bidder_id);
                    popup(user_id, bidder_id);

                }
            });
            if (result.get(position).getImage().contains("pdf")) {
                Glide.with(context).load(R.drawable.ic_file).into(holder.imgmap);
            } else {
                Glide.with(context).load(result.get(position).getImage()).placeholder(R.drawable.ic_attach).into(holder.imgmap);
            }
            holder.imgmap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("path", result.get(position).getImage());
                    ErrorMessage.I(context, WebViewActivity.class, bundle);

                }
            });


        } catch (Exception e) {
            ErrorMessage.E("" + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class viewHlder extends RecyclerView.ViewHolder {
        CircleImageView display_image;
        TextView tvusername, tvdatetime, story_manjil, area_sqFeet, work_type, tvstartingdate, tvcompletiondate, totaldays, bidamount, negotiableamount, glass_type, paint_type, layer, dimension, kitchen, bathroom, basin, latrin, angletype,
                tvbid, tvimg, service_type, description, purpose, point, room, total_point, point6a, point16a, mcb, category, type, tvcategory, hall, contact;
        LinearLayout hide, hide_bid;
        RelativeLayout arrow;
        ImageView imgarrow, imgmap, imgdesign, imgrepair, imgrailing;
        ;
        View view;
        RelativeLayout maprelative, relativestory, relativesquarefeet, relativetype, relativetypeofglass, relativepaintype, relativeLayer, relativedimension,
                relativekitchen, relativebathrooom, relativebasin, relativelatrin, relativedesign, relativeservicetype, relativedescription, relativepurpose,
                relativepoint, relativeroom, relativetotalpoint, relative6A, relative16A, relativemcb, relativesubcat, relativesubtype, relativearrow, relativehall, relativeangletype, repairrelative, relativerailling, relativestarting, relativecomplete, relativedays, relativebidamount, relativenego;
        Button btn;

        public viewHlder(@NonNull View itemView) {
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
            hide_bid = itemView.findViewById(R.id.hide_bid);
            view = itemView.findViewById(R.id.view);
            maprelative = itemView.findViewById(R.id.maprelative);
            relativearrow = itemView.findViewById(R.id.relativearrow);
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
            contact = itemView.findViewById(R.id.contact);
            imgmap = itemView.findViewById(R.id.imgmap);
            imgdesign = itemView.findViewById(R.id.imgdesign);
            repairrelative = itemView.findViewById(R.id.repairrelative);
            imgrepair = itemView.findViewById(R.id.imgrepair);
            imgrailing = itemView.findViewById(R.id.imgrailing);
            relativerailling = itemView.findViewById(R.id.relativerailling);
            maprelative = itemView.findViewById(R.id.maprelative);
            relativedesign = itemView.findViewById(R.id.relativedesign);
            relativedays = itemView.findViewById(R.id.relativedays);
            relativestarting = itemView.findViewById(R.id.relativestarting);
            relativecomplete = itemView.findViewById(R.id.relativecomplete);
            relativenego = itemView.findViewById(R.id.relativenego);
            relativebidamount = itemView.findViewById(R.id.relativebidamount);
            btn = itemView.findViewById(R.id.btnfeedback);
            tvimg = itemView.findViewById(R.id.tvimg);
            tvbid = itemView.findViewById(R.id.tvbid);
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

    public void popup(String user_id, String bidder_id) {
        ErrorMessage.E("bidder_id" + bidder_id);

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.feedback);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.rating);
        final Button btnsubmit = (Button) dialog.findViewById(R.id.btnsubmit);
        final EditText etcomment = (EditText) dialog.findViewById(R.id.etcomment);
        final TextView tvrate = (TextView) dialog.findViewById(R.id.tvrate);
        rating = String.valueOf(ratingBar.getRating());
        switch (rating) {
            case "1":
                tvrate.setText("Not at all satisfied");
                break;
            case "2":
                tvrate.setText("Slightly satisfied");
                break;
            case "3":
                tvrate.setText("Netural");
                break;
            case "4":
                tvrate.setText("Very satisfied");
                break;
            case "5":
                tvrate.setText("Extremely satisfied");
                break;
        }

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rating = String.valueOf(ratingBar.getRating());
                comment = etcomment.getText().toString();
                switch (rating) {
                    case "1":
                        tvrate.setText("Not at all satisfied");
                        break;
                    case "2":
                        tvrate.setText("Slightly satisfied");
                        break;
                    case "3":
                        tvrate.setText("Netural");
                        break;
                    case "4":
                        tvrate.setText("Very satisfied");
                        break;
                    case "5":
                        tvrate.setText("Extremely satisfied");
                        break;
                }
                //  Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
                ratting(user_id, bidder_id, rating, comment);

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void ratting(String user_id, String bidder_id, String rating, String comment) {
        if (NetworkUtil.isNetworkAvailable(context)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(context);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.rating(user_id, bidder_id, rating, comment);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    ErrorMessage.E("Response" + response.message());
                    ErrorMessage.E("Response" + response.toString());

                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("get" + object.toString());

                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                // ErrorMessage.E("comes in if cond" + object.toString());
                                ErrorMessage.T(context, object.getString("message"));

                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(context, object.getString("message"));


                                materialDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(context, "No Internet");
        }
    }

}
