package com.pixelmarketo.ularo.bidderContent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.activities.SelectionActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.userContent.UserHomeActivity;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.UserAccount;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BiddingActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.etstartingdate)
    EditText etstartingdate;
    @BindView(R.id.etcompletiondate)
    EditText etcompletiondate;
    @BindView(R.id.ettotaldays)
    TextView ettotaldays;
    @BindView(R.id.etbidamount)
    EditText etbidamount;
    @BindView(R.id.etnegotiable)
    EditText etnegotiable;
    @BindView(R.id.btnsend)
    Button btnsend;
    @BindView(R.id.tvbidding)
    TextView tvbidding;
    DatePickerDialog datePickerDialog;

    int Year, Month, Day, Hour, Minute, mHour, mMin;
    Calendar calendar, date1, date2;
    String id;
    @BindView(R.id.linearstarting)
    LinearLayout linearstarting;
    @BindView(R.id.linearending)
    LinearLayout linearending;
    @BindView(R.id.lineardays)
    LinearLayout lineardays;
    @BindView(R.id.linearbidamount)
    LinearLayout linearbidamount;
    @BindView(R.id.linearnego)
    LinearLayout linearnego;
    @BindView(R.id.tvbid)
    TextView tvbid;
    Bundle bundle;
    int year, month, day;
    int year1, month1, day1;
    @BindView(R.id.tverror)
    TextView tverror;
    int amount,negamount;
    AlertDialog.Builder builder;

    @Override
    protected int getContentResId() {
        return R.layout.activity_bidding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("");
        builder = new AlertDialog.Builder(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            ErrorMessage.E("from>>>>>>>>>"+bundle.getString("from"));
            titleTxt.setText("" + bundle.getString("from"));
            tvbidding.setText(bundle.getString("from") + " " + "bidding");
            if (bundle.getString("from").equalsIgnoreCase("Carpenter")) {
                linearstarting.setVisibility(View.GONE);
                linearending.setVisibility(View.GONE);
                lineardays.setVisibility(View.GONE);
                tverror.setVisibility(View.VISIBLE);
                tvbid.setText("Bid amount(in %)");
                etbidamount.setText("");
                etbidamount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String am=etbidamount.getText().toString();
                        ErrorMessage.E("am>>>>"+am);
                        if (!am.equals("")) {
                            amount=Integer.parseInt(am);
                        }
                        ErrorMessage.E("amount>>>>"+amount);
                        if (amount<=100){
                            ErrorMessage.E("done");
                            btnsend.setEnabled(true);
                            btnsend.setBackground(btnsend.getContext().getDrawable(R.drawable.rount_btn));


                        }else {
                            etbidamount.setError("Less than or equal 100%");
                            btnsend.setEnabled(false);
                            btnsend.setBackground(btnsend.getContext().getDrawable(R.drawable.rount_btn_grey));

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

            }
        }

        etnegotiable.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String am=etbidamount.getText().toString();
                String nego_amount=etnegotiable.getText().toString();
                ErrorMessage.E("am>>>>"+am);
                if (!am.equals("")) {
                    amount=Integer.parseInt(am);
                }
                if (!nego_amount.equals("")) {
                    negamount=Integer.parseInt(nego_amount);
                }
                ErrorMessage.E("amount>>>>"+amount);
                if (negamount<=amount){
                    ErrorMessage.E("done");
                    btnsend.setEnabled(true);
                    btnsend.setBackground(btnsend.getContext().getDrawable(R.drawable.rount_btn));


                }else {
                    etnegotiable.setError("Less than or equal bid amount");
                    btnsend.setEnabled(false);
                    btnsend.setBackground(btnsend.getContext().getDrawable(R.drawable.rount_btn_grey));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        calendar = Calendar.getInstance();
        date1 = Calendar.getInstance();
        date2 = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DATE);
        Hour = calendar.get(Calendar.HOUR);
        Minute = calendar.get(Calendar.MINUTE);

        //  getDifference();
    }


    @OnClick({R.id.etstartingdate, R.id.etcompletiondate, R.id.btnsend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.etstartingdate:
                setdate(etstartingdate, "start");
                break;
            case R.id.etcompletiondate:
                setdate(etcompletiondate, "end");
                break;
            case R.id.btnsend:
                if (bundle.getString("from").equalsIgnoreCase("Carpenter")) {
                    if (UserAccount.isEmpty(etbidamount)) {
                        popup();
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else {
                    if (UserAccount.isEmpty(etstartingdate, etcompletiondate, etbidamount)) {
                        popup();
                    } else {
                        UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                }
                break;
        }
    }
    public void popup() {

        final Dialog dialog = new Dialog(BiddingActivity.this);
        dialog.setContentView(R.layout.confirmation);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
        final Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                form_submit();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }
    private void getDifference() {
        try {
            String CurrentDate = etstartingdate.getText().toString();
            String FinalDate = etcompletiondate.getText().toString();
            Date date1;
            Date date2;
            SimpleDateFormat dates = new SimpleDateFormat("yyyy-mm-dd");
            date1 = dates.parse(CurrentDate);
            date2 = dates.parse(FinalDate);


            long difference = Math.abs(date2.getTime() - date1.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);
            String dayDifference = Long.toString(differenceDates);
            int total = Integer.parseInt(dayDifference) + 1;
            ettotaldays.setText(total + " days");
        } catch (Exception exception) {
            ErrorMessage.E(">>>>>" + exception.getMessage());
            Toast.makeText(this, "Unable to find difference", Toast.LENGTH_SHORT).show();
        }

    }

    private void getDifferenceTest() {
        try {
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();

            // Set the values for the calendar fields YEAR, MONTH, and DAY_OF_MONTH.
            calendar1.set(year, month, day);
            calendar2.set(year1, month1, day1);
            long miliSecondForDate1 = calendar1.getTimeInMillis();
            long miliSecondForDate2 = calendar2.getTimeInMillis();

            // Calculate the difference in millisecond between two dates
            long diffInMilis = miliSecondForDate2 - miliSecondForDate1;

            /*
             * Now we have difference between two date in form of millsecond we can
             * easily convert it Minute / Hour / Days by dividing the difference
             * with appropriate value. 1 Second : 1000 milisecond 1 Hour : 60 * 1000
             * millisecond 1 Day : 24 * 60 * 1000 milisecond
             */

            long diffInSecond = diffInMilis / 1000;
            long diffInMinute = diffInMilis / (60 * 1000);
            long diffInHour = diffInMilis / (60 * 60 * 1000);
            long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);

            ErrorMessage.E("Difference in Seconds : " + diffInSecond);
            ErrorMessage.E("Difference in Minute : " + diffInMinute);
            ErrorMessage.E("Difference in Hours : " + diffInHour);
            ErrorMessage.E("Difference in Days : " + diffInDays);
            if (diffInDays < 0) {
                ErrorMessage.T(BiddingActivity.this,"Please select proper dates");
                etcompletiondate.setText("");
                ettotaldays.setText("");
            } else {
                int total = Integer.parseInt(String.valueOf(diffInDays)) + 1;
                ettotaldays.setText(total + " days");
            }
        } catch (Exception exception) {
            ErrorMessage.E(">>>>>" + exception.getMessage());
            Toast.makeText(this, "Unable to find difference", Toast.LENGTH_SHORT).show();
        }

    }

    public void setdate(EditText editText, String from) {
        calendar = Calendar.getInstance();
        //calendar.add(Calendar.DAY_OF_MONTH, 1);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
        calendar.setTimeInMillis(System.currentTimeMillis() - 1000);
        datePickerDialog.setMinDate(calendar);
        if (from.equalsIgnoreCase("end")){

        }

        // Log.e("setMinDate", " == " + calendar.toString());
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int eventYear, int monthOfYear, int dayOfMonth) {
                if (monthOfYear + 1 < 10 && dayOfMonth > 10) {
                    if (from.equalsIgnoreCase("start")) {
                        year = eventYear;
                        month = monthOfYear + 1;
                        day = dayOfMonth;
                    } else {
                        year1 = eventYear;
                        month1 = monthOfYear + 1;
                        day1 = dayOfMonth;
                    }
                    editText.setText((eventYear + "-0" + (monthOfYear + 1) + "-" + dayOfMonth));
                } else if (dayOfMonth < 10 && monthOfYear + 1 >= 10) {
                    if (from.equalsIgnoreCase("start")) {
                        year = eventYear;
                        month = monthOfYear + 1;
                        day = dayOfMonth;
                    } else {
                        year1 = eventYear;
                        month1 = monthOfYear + 1;
                        day1 = dayOfMonth;
                    }
                    editText.setText((eventYear + "-0" + (monthOfYear + 1) + "-" + dayOfMonth));

                } else if (monthOfYear + 1 < 10 && dayOfMonth < 10) {
                    if (from.equalsIgnoreCase("start")) {
                        year = eventYear;
                        month = monthOfYear + 1;
                        day = dayOfMonth;
                    } else {
                        year1 = eventYear;
                        month1 = monthOfYear + 1;
                        day1 = dayOfMonth;
                    }
                    editText.setText((eventYear + "-0" + (monthOfYear + 1) + "-" + dayOfMonth));

                } else {
                    if (from.equalsIgnoreCase("start")) {
                        year = eventYear;
                        month = monthOfYear + 1;
                        day = dayOfMonth;
                    } else {
                        year1 = eventYear;
                        month1 = monthOfYear + 1;
                        day1 = dayOfMonth;
                    }
                    editText.setText((eventYear + "-0" + (monthOfYear + 1) + "-" + dayOfMonth));
                    //getDifference();

                }
                getDifferenceTest();
            }
        });
        datePickerDialog.show(getSupportFragmentManager(), "Date Picker");

    }

    @Override
    public void onDateSet(DatePickerDialog view, int eventYear, int monthOfYear, int dayOfMonth) {

    }

    private void form_submit() {
        if (NetworkUtil.isNetworkAvailable(BiddingActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(BiddingActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.create_bid(id, etbidamount.getText().toString(), etnegotiable.getText().toString(), etcompletiondate.getText().toString(), etstartingdate.getText().toString(), ettotaldays.getText().toString());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());

                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                // ErrorMessage.E("comes in if cond" + object.toString());
                                ErrorMessage.T(BiddingActivity.this, object.getString("message"));
                                Bundle bundle = new Bundle();
                                bundle.putString("from", "Bidder");
                                ErrorMessage.I_clear(BiddingActivity.this, BidderHomeActivity.class, bundle);

                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(BiddingActivity.this, object.getString("message"));
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
            ErrorMessage.T(BiddingActivity.this, "No Internet");
        }
    }

}
