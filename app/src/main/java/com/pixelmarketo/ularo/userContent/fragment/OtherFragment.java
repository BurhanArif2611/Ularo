package com.pixelmarketo.ularo.userContent.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.model.TypeOfWorkModel;
import com.pixelmarketo.ularo.userContent.UserHomeActivity;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.SavedData;
import com.pixelmarketo.ularo.utility.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtherFragment extends Fragment {


    @BindView(R.id.et6Atotalpoints)
    EditText et6Atotalpoints;
    @BindView(R.id.et_16Atotalpoints)
    EditText et16Atotalpoints;
    @BindView(R.id.etmcb)
    EditText etmcb;
    @BindView(R.id.ettotal)
    EditText ettotal;
    @BindView(R.id.servicetype)
    Spinner servicetype;
    @BindView(R.id.btnsend)
    Button btnsend;
    String id = "", select_type = "";
    ArrayList<String> type = new ArrayList<String>();
    @BindView(R.id.ettotalpoint)
    TextView ettotalpoint;
    @BindView(R.id.radiogroup1)
    RadioGroup radiogroup1;
    String quick_mode="0";
    private Unbinder unbinder;

    public OtherFragment() {
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
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        unbinder = ButterKnife.bind(this, view);
        gettype();
        servicetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_type = type.get(position).toString();
                ErrorMessage.E("type" + select_type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        et6Atotalpoints.setText("");
        et16Atotalpoints.setText("");
        etmcb.setText("");

        et6Atotalpoints.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int totalpoint = Integer.parseInt(et6Atotalpoints.getText().toString()) + Integer.parseInt(et16Atotalpoints.getText().toString()) + Integer.parseInt(etmcb.getText().toString());
                    ettotalpoint.setText("" + totalpoint);
                } catch (Exception e) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et16Atotalpoints.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int totalpoint = Integer.parseInt(et6Atotalpoints.getText().toString()) + Integer.parseInt(et16Atotalpoints.getText().toString()) + Integer.parseInt(etmcb.getText().toString());
                    ettotalpoint.setText("" + totalpoint);
                } catch (Exception e) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etmcb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int totalpoint = Integer.parseInt(et6Atotalpoints.getText().toString()) + Integer.parseInt(et16Atotalpoints.getText().toString()) + Integer.parseInt(etmcb.getText().toString());
                    ettotalpoint.setText("" + totalpoint);
                } catch (Exception e) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }


    @OnClick(R.id.btnsend)
    public void onViewClicked() {
        if (UserAccount.isEmpty(et6Atotalpoints, et16Atotalpoints, etmcb, ettotal)) {
            int q1 = radiogroup1.getCheckedRadioButtonId();
            RadioButton radioButton1 = (RadioButton) getActivity().findViewById(q1);
            ErrorMessage.E("1===" + radioButton1.getText());
            if (radioButton1.getText().equals("yes")){
                quick_mode="1";
                ErrorMessage.E("quick_mode===" + quick_mode);
                form_submit();

            }
            else {
                quick_mode="0";
                form_submit();
            }
        } else {
            UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
            UserAccount.EditTextPointer.requestFocus();
        }
    }

    public void gettype() {
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(getActivity());
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);

            Call<ResponseBody> call = apiService.type_of_work();


            call.enqueue(new Callback<ResponseBody>() {
                @SuppressLint("NewApi")
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        Gson gson = new Gson();
                        try {
                            jsonObject = new JSONObject(response.body().string());
                            ErrorMessage.E("get" + jsonObject.toString());
                            materialDialog.dismiss();
                            if (jsonObject.getString("status").equals("200")) {
                                String responseData = jsonObject.toString();
                                ErrorMessage.E("responseData" + responseData);

                                TypeOfWorkModel typeOfWorkModel = gson.fromJson(responseData, TypeOfWorkModel.class);
                                for (int i = 0; i < typeOfWorkModel.getResult().size(); i++) {
                                    type.add(typeOfWorkModel.getResult().get(i).getType_name());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, type);
                                servicetype.setAdapter(adapter);
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

    private void form_submit() {
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(getActivity());
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.form(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), SavedData.getuserId(), "", ettotal.getText().toString(), select_type, "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", "other", SavedData.getChekcData(), "", ettotalpoint.getText().toString(), et6Atotalpoints.getText().toString(), et16Atotalpoints.getText().toString(), etmcb.getText().toString(), "", "","", quick_mode);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());

                    if (response.isSuccessful()) {
                        try {
                            materialDialog.dismiss();
                            JSONObject object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                // ErrorMessage.E("comes in if cond" + object.toString());
                                ErrorMessage.T(getActivity(), object.getString("message"));
                                Bundle bundle = new Bundle();
                                bundle.putString("from", "User");
                                ErrorMessage.I_clear(getActivity(), UserHomeActivity.class, bundle);
                            } else {
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

}
