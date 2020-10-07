package com.pixelmarketo.ularo.userContent;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SctionFitterActivity extends BaseActivity {


    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.purpose)
    Spinner purpose;
    @BindView(R.id.etarea)
    EditText etarea;
    @BindView(R.id.btnsend)
    Button btnsend;
    String id = "", select_type = "", select_angle = "";
    ArrayList<String> type = new ArrayList<String>();
    ArrayList<String> angletype = new ArrayList<String>();
    @BindView(R.id.type)
    Spinner sptype;
    @BindView(R.id.radiogroup1)
    RadioGroup radiogroup1;
    String quick_mode="0";

    @Override
    protected int getContentResId() {
        return R.layout.activity_sction_fitter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("");
        titleTxt.setText("Section Fitter");
        gettype();
        getangletype();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ErrorMessage.E("id" + bundle.getString("id"));
            id = bundle.getString("id");
        }
    }

    @OnClick(R.id.btnsend)
    public void onViewClicked() {
        if (UserAccount.isEmpty(etarea)) {
            int q1 = radiogroup1.getCheckedRadioButtonId();
            RadioButton radioButton1 = (RadioButton) findViewById(q1);
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

    private void gettype() {
        type.add(0, "Door");
        type.add(1, "Window");
        type.add(2, "Partition");
        type.add(3, "False Ceiling");


        //Door,Window,Partition,Square,Rectangular,Round Tubes,False Ceiling
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SctionFitterActivity.this, android.R.layout.simple_spinner_dropdown_item, type);
        purpose.setAdapter(adapter);

        purpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_type = type.get(position).toString();
                ErrorMessage.E("type of glass" + select_type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getangletype() {
        angletype.add(0, "Square");
        angletype.add(1, "Rectangular");
        angletype.add(2, "Round Tubes");


        //Door,Window,Partition,Square,Rectangular,Round Tubes,False Ceiling
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SctionFitterActivity.this, android.R.layout.simple_spinner_dropdown_item, angletype);
        sptype.setAdapter(adapter);

        sptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_angle = angletype.get(position).toString();
                ErrorMessage.E("type of angletype" + select_angle);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void form_submit() {
        if (NetworkUtil.isNetworkAvailable(SctionFitterActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(SctionFitterActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.form(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), id, "", etarea.getText().toString(), "", "", "", "", "", "",
                    "", "", "", "", "", "", "", select_type, "", "", "", "", "", "", "", "", "", select_angle, "","", quick_mode);

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
                                ErrorMessage.T(SctionFitterActivity.this, object.getString("message"));
                                Bundle bundle = new Bundle();
                                bundle.putString("from", "User");
                                ErrorMessage.I_clear(SctionFitterActivity.this, UserHomeActivity.class, bundle);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(SctionFitterActivity.this, object.getString("message"));
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
            ErrorMessage.T(SctionFitterActivity.this, "No Internet");
        }
    }
}
