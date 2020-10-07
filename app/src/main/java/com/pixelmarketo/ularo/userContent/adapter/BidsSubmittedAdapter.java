package com.pixelmarketo.ularo.userContent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.model.BidListResult;
import com.pixelmarketo.ularo.userContent.BidsSubmittedCategoryActivity;
import com.pixelmarketo.ularo.userContent.BidsSubmittedDataActivity;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class BidsSubmittedAdapter extends RecyclerView.Adapter<BidsSubmittedAdapter.viewHolder> {

    Context context;
    List<BidListResult> result;
    double amount;
    double percent, total, exxcess_amount;
    private ArrayList<BidListResult> arraylist = null;

    public BidsSubmittedAdapter(Context context, List<BidListResult> result) {
        this.context = context;
        this.result = result;
        this.arraylist = new ArrayList<BidListResult>();
        this.arraylist.addAll(result);
    }

    @NonNull
    @Override
    public BidsSubmittedAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bids_submitted, parent, false);
        return new BidsSubmittedAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BidsSubmittedAdapter.viewHolder holder, int position) {
        holder. tvmsg.setSelected(true);

        holder.tvusername.setText(result.get(position).getUser_name());
        holder.tvstartingdate.setText(result.get(position).getStart_date());
        holder.tvcompletiondate.setText(result.get(position).getEnd_date());
        holder.bidamount.setText(result.get(position).getBid_amount());
        holder.negotiableamount.setText(result.get(position).getNego_amount());
        holder.tvdatetime.setText(result.get(position).getCreated_at());
        //  int value= Integer.parseInt(result.get(position).getRating());
        holder.rb.setRating((float) result.get(position).getRating());
        holder.totaldays.setText(result.get(position).getTotal_days() + " days");
        holder.tvcategory.setText(result.get(position).getCategory());
        if(result.get(position).getCategory().equalsIgnoreCase("CARPENTER")){
            holder.relativestart.setVisibility(View.GONE);
            holder.relativeend.setVisibility(View.GONE);
            holder.relativetotal.setVisibility(View.GONE);
            holder.tvbid.setText("Bid Amount(In %)");
        }
        try {
            holder.arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.hide_bid.getVisibility() == View.VISIBLE) {
                        Glide.with(context).load(R.drawable.ic_down_arrow_1).into(holder.imgarrow);
                        holder.hide_bid.setVisibility(View.GONE);
                    } else {
                        Glide.with(context).load(R.drawable.ic_up_arrow).into(holder.imgarrow);
                        holder.hide_bid.setVisibility(View.VISIBLE);
                    }
                }
            });
            int price = Integer.parseInt(result.get(position).getBid_amount()) - Integer.parseInt(result.get(position).getNego_amount());
            String total = String.valueOf((price * 3) / 100);

            holder.btnapprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double total_amount = getcharge(Integer.parseInt(result.get(position).getNego_amount()), result.get(position).getCategory());
                    ErrorMessage.E("" + total_amount);
                   // ((BidsSubmittedDataActivity) context).confirm_payment("1234",result.get(position).getBid_id());
                    ((BidsSubmittedDataActivity) context).startPayment(("" + (new DecimalFormat("#").format(total_amount)) + "00"), result.get(position).getImage(), result.get(position).getBid_id(), (new DecimalFormat("#").format(total_amount)));
                }
            });
        } catch (Exception e) {
        }
    }

    private double getcharge(int bid_amount, String category) {
        try {
            double per = 0.5 / 100;
            ErrorMessage.E("per" + per);
            if (category.contains("ARCHITECT")) {
                amount = 150;
            } else if (category.contains("CARPENTER")) {
                if (bid_amount <= 30000) {
                    amount = 400;
                } else {
                    exxcess_amount = bid_amount - 30000;
                    percent = (exxcess_amount) * per;
                    total = 400 + percent;
                    amount = total;
                }
            } else if (category.contains("ELECTRICIAN")) {
                if (bid_amount <= 15000) {
                    amount = 150;
                } else {
                    exxcess_amount = bid_amount - 15000;
                    percent = exxcess_amount * per;
                    total = 150 + percent;
                    amount = total;
                }
            } else if (category.contains("CONTRACTOR")) {
                if (bid_amount <= 500000) {
                    amount = 1000;
                } else {
                    exxcess_amount = bid_amount - 500000;
                    percent = exxcess_amount * per;
                    total = 1000 + percent;
                    amount = total;
                }
            } else if (category.contains("GLASS WORK")) {
                if (bid_amount <= 5000) {
                    amount = 100;
                } else {
                    exxcess_amount = bid_amount - 5000;
                    percent = exxcess_amount * per;
                    total = 100 + percent;
                    amount = total;
                }
            } else if (category.contains("PAINTER")) {
                if (bid_amount <= 50000) {
                    amount = 150;
                } else {
                    exxcess_amount = bid_amount - 50000;
                    percent = (exxcess_amount) * per;
                    total = 150 + percent;
                    amount = total;
                }

            } else if (category.contains("PLUMBER")) {
                if (bid_amount <= 5000) {
                    amount = 100;
                } else {
                    exxcess_amount = bid_amount - 5000;
                    percent = (exxcess_amount) * per;
                    total = 100 + percent;
                    amount = total;
                }
            } else if (category.contains("POP/PVC WORK")) {
                if (bid_amount <= 50000) {
                    amount = 200;
                } else {
                    exxcess_amount = bid_amount - 50000;
                    percent = (exxcess_amount) * per;
                    total = 200 + percent;
                    amount = total;
                }
            } else if (category.contains("RAILING FEETTER")) {
                if (bid_amount <= 10000) {
                    amount = 150;
                } else {
                    exxcess_amount = bid_amount - 10000;
                    percent = (exxcess_amount) * per;
                    total = 150 + percent;
                    amount = total;
                }
            } else if (category.contains("REPAIR AND MAINTENANCE")) {
                amount = 50;
            } else if (category.contains("SECTION FEETER")) {
                if (bid_amount <= 50000) {
                    amount = 100;
                } else {
                    exxcess_amount = bid_amount - 50000;
                    percent = (exxcess_amount) * per;
                    total = 100 + percent;
                    amount = total;
                }
            }
        } catch (Exception e) {
        }
        return amount;
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
        } else {
            for (BidListResult wp : arraylist) {
                if (wp.getCategory().toLowerCase(Locale.getDefault()).contains(text)) {
                    result.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView display_image;
        TextView tvusername, tvdatetime, tvstartingdate, tvcompletiondate, totaldays, bidamount, negotiableamount, tvcategory,tvmsg,tvbid;
        LinearLayout hide_bid;
        ImageView imgarrow;
        Button btnapprove;
        RelativeLayout arrow,relativetotal,relativeend,relativestart;
        RatingBar rb;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            display_image = itemView.findViewById(R.id.display_image);
            tvusername = itemView.findViewById(R.id.tvusername);
            tvdatetime = itemView.findViewById(R.id.tvdatetime);
            tvstartingdate = itemView.findViewById(R.id.tvstartingdate);
            tvcompletiondate = itemView.findViewById(R.id.tvcompletiondate);
            totaldays = itemView.findViewById(R.id.totaldays);
            bidamount = itemView.findViewById(R.id.bidamount);
            negotiableamount = itemView.findViewById(R.id.negotiableamount);
            hide_bid = itemView.findViewById(R.id.hide_bid);
            imgarrow = itemView.findViewById(R.id.imgarrow);
            btnapprove = itemView.findViewById(R.id.btnapprove);
            arrow = itemView.findViewById(R.id.arrow);
            tvcategory = itemView.findViewById(R.id.tvcategory);
            rb = (RatingBar) itemView.findViewById(R.id.ratingBar);
            tvmsg = (TextView) itemView.findViewById(R.id.tvmsg);
            tvbid = (TextView) itemView.findViewById(R.id.tvbid);
            relativetotal = (RelativeLayout) itemView.findViewById(R.id.relativetotal);
            relativeend = (RelativeLayout) itemView.findViewById(R.id.relativeend);
            relativestart = (RelativeLayout) itemView.findViewById(R.id.relativestart);

        }
    }


}

