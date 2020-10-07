package com.pixelmarketo.ularo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.utility.ErrorMessage;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class BottomSheetFragment extends BottomSheetDialogFragment {

    @BindView(R.id.btnapply)
    Button btnapply;
    @BindView(R.id.tvtype)
    TextView tvtype;
    @BindView(R.id.tvfilter)
    TextView tvfilter;
    @BindView(R.id.lineartype)
    LinearLayout lineartype;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.linearfilter)
    LinearLayout linearfilter;
    @BindView(R.id.area)
    TextView area;
    private Unbinder unbinder;
    private BottomSheetListener mListener;

    String select_type,select_filter,select_total,select_job;

    @BindView(R.id.story)
    TextView story;
    @BindView(R.id.point)
    TextView point;
    @BindView(R.id.job)
    TextView job;
    @BindView(R.id.hightolow)
    TextView hightolow;
    @BindView(R.id.lowtohigh)
    TextView lowtohigh;
    @BindView(R.id.etcity)
    EditText etcity;
    String city;
    String myValue;
    public BottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        unbinder = ButterKnife.bind(this, view);
         myValue = this.getArguments().getString("id");
        ErrorMessage.E("Fragment>>>"+myValue);
        if ((myValue.equals("30"))||(myValue.equals("27"))||(myValue.equals("25"))||(myValue.equals("24")) || (myValue.equals("28"))){
            story.setVisibility(View.GONE);
            linear.setVisibility(View.GONE);
        }
        else if(myValue.equals("22")){

            lineartype.setVisibility(View.GONE);
            job.setVisibility(View.GONE);
            linear.setVisibility(View.VISIBLE);
        }
        else if (myValue.equals("26")){
            lineartype.setVisibility(View.GONE);
           // story.setVisibility(View.GONE);
            point.setVisibility(View.GONE);
            linear.setVisibility(View.VISIBLE);
        }



       city= etcity.getText().toString();
        return view;
    }
    @OnClick({R.id.area, R.id.story,R.id.hightolow,R.id.lowtohigh,R.id.point,R.id.job})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.area:
                if (myValue.equals("28")){
                    select_type = "Running";
                    area.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);

                }
                else {
                    select_type = "Area";
                    area.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                    story.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.uncheck, 0);
                }
                break;
            case R.id.story:
                select_type = "Story";
                story.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                area.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.uncheck, 0);
                break;
            case R.id.point:
                select_type = "Point";
                point.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
               //area.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.uncheck, 0);
                break;
            case R.id.job:
                select_type = "Job";
                job.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                //area.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.uncheck, 0);
                break;
            case R.id.hightolow:
                select_filter = "high_to_low";
                hightolow.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                lowtohigh.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.uncheck, 0);
                break;
            case R.id.lowtohigh:
                select_filter = "low_to_high";
                lowtohigh.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
                hightolow.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.uncheck, 0);
                break;

        }
    }


    @OnClick(R.id.btnapply)
    public void onViewClicked() {
        mListener.onButtonClicked(select_type, select_filter,etcity.getText().toString());
        dismiss();
    }

    public interface BottomSheetListener {
        void onButtonClicked( String select_type, String select_filter,String city);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}
