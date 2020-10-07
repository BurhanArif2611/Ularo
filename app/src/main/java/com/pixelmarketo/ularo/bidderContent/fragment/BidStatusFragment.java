package com.pixelmarketo.ularo.bidderContent.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.adapter.BidStatusAdapter;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.model.BidListModel;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BidStatusFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.booking_list_adapter)
    RecyclerView bookingListAdapter;
    BidStatusAdapter adapter;
    int status = 0, position;
    @BindView(R.id.nodata)
    LinearLayout nodata;
    String from;

    public BidStatusFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
       from= bundle.getString("from");
        ErrorMessage.E("from" +  bundle.getString("from"));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bid_status, container, false);
        unbinder = ButterKnife.bind(this, view);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myBroadcastReceiver,
                new IntentFilter("thisIsForMyFragment"));

        form_submit();
        return view;
    }

    private final BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //  Toast.makeText(getActivity(), "Broadcast received!", Toast.LENGTH_SHORT).show();//Do what you want when the broadcast is received...
            position = intent.getIntExtra("extra", 0);
            status = position;
            form_submit();
            ErrorMessage.E("" + position);

        }
    };

    private void form_submit() {
        ErrorMessage.E("status" + status);
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(getActivity());
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call=null;
            if (from.equalsIgnoreCase("user")){
                call = apiService.approve_pending_list_user(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), String.valueOf(status));

            }
            else {
                call = apiService.approve_pending_list(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), String.valueOf(status));
            }
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());

                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        Gson gson = new Gson();
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                bookingListAdapter.setVisibility(View.VISIBLE);
                                nodata.setVisibility(View.GONE);
                                String responseData = object.toString();
                                ErrorMessage.T(getActivity(), object.getString("message"));
                                BidListModel bidListModel = gson.fromJson(responseData, BidListModel.class);
                                adapter = new BidStatusAdapter(getActivity(), bidListModel.getResult(),from,status);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                                bookingListAdapter.setLayoutManager(layoutManager);
                                ErrorMessage.E("" + String.valueOf(position));
                                bookingListAdapter.setAdapter(adapter);
                            } else {
                                bookingListAdapter.setVisibility(View.INVISIBLE);
                                nodata.setVisibility(View.VISIBLE);
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(getActivity(), object.getString("message"));
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
            ErrorMessage.T(getActivity(), "No Internet");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myBroadcastReceiver);
    }
}
