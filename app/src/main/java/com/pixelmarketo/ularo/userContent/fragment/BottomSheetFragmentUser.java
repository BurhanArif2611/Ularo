package com.pixelmarketo.ularo.userContent.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class BottomSheetFragmentUser extends BottomSheetDialogFragment {

    @BindView(R.id.btnapply)
    Button btnapply;

    private Unbinder unbinder;
    private BottomSheetListener mListener;

    String select_type,select_filter;

    @BindView(R.id.hightolow)
    TextView hightolow;
    @BindView(R.id.lowtohigh)
    TextView lowtohigh;
    @BindView(R.id.spinner)
    Spinner spinner;

    ArrayList<String> type = new ArrayList<>();

    public BottomSheetFragmentUser() {
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
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        type.add(0,"Bid Amount");
        type.add(1,"Rating");
        type.add(2,"End Date");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_type = type.get(position).toString();
                ErrorMessage.E("ciyt"+select_type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
    @OnClick({R.id.hightolow,R.id.lowtohigh})
    public void onViewClicked(View view) {
        switch (view.getId()) {

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
        mListener.onButtonClicked(select_type, select_filter);
        dismiss();
    }

    public interface BottomSheetListener {
        void onButtonClicked(String select_type, String select_filter);
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
