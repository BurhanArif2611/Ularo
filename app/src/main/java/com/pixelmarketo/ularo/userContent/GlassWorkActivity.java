package com.pixelmarketo.ularo.userContent;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
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

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.model.TypeOfWorkModel;
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

public class GlassWorkActivity extends BaseActivity {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.ettypeofglass)
    Spinner ettypeofglass;
    @BindView(R.id.etarea)
    EditText etarea;
    @BindView(R.id.servicetype)
    Spinner servicetype;
    @BindView(R.id.btnsend)
    Button btnsend;
    String id = "", select_type = "", select_type_of_glass = "";
    ArrayList<String> type = new ArrayList<String>();
    ArrayList<String> type_of_glass = new ArrayList<String>();
    @BindView(R.id.btnno)
    RadioButton btnno;
    @BindView(R.id.radiogroup1)
    RadioGroup radiogroup1;
    String quick_mode="0";

    @Override
    protected int getContentResId() {
        return R.layout.activity_glass_work;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarWithBackButton("");
        titleTxt.setText("GlassWork");
        gettype();
        get_type_glass();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ErrorMessage.E("id" + bundle.getString("id"));
            id = bundle.getString("id");
        }
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
    }

    private void get_type_glass() {
        type_of_glass.add(0, "NORMAL");
        type_of_glass.add(1, "TOUGHENED");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GlassWorkActivity.this, android.R.layout.simple_spinner_dropdown_item, type_of_glass);
        ettypeofglass.setAdapter(adapter);

        ettypeofglass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_type_of_glass = type_of_glass.get(position).toString();
                ErrorMessage.E("type of glass" + select_type_of_glass);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btnsend)
    public void onViewClicked() {
        if (UserAccount.isEmpty(etarea)) {
            int q1 = radiogroup1.getCheckedRadioButtonId();
            RadioButton radioButton1 = (RadioButton) findViewById(q1);
            ErrorMessage.E("1===" + radioButton1.getText());
            if (radioButton1.getText().equals("yes")) {
                quick_mode = "1";
                ErrorMessage.E("quick_mode===" + quick_mode);
                form_submit();

            } else {
                quick_mode = "0";
                form_submit();
            }
        } else {
            UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
            UserAccount.EditTextPointer.requestFocus();
        }

    }

    public void gettype() {
        if (NetworkUtil.isNetworkAvailable(GlassWorkActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(GlassWorkActivity.this);
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
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(GlassWorkActivity.this, android.R.layout.simple_spinner_dropdown_item, type);
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
            ErrorMessage.T(GlassWorkActivity.this, "No Internet");
        }
    }

    private void form_submit() {
        if (NetworkUtil.isNetworkAvailable(GlassWorkActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(GlassWorkActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.form(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), id, "", etarea.getText().toString(), select_type, "", "", "", "", "",
                    select_type_of_glass, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","", quick_mode);

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
                                ErrorMessage.T(GlassWorkActivity.this, object.getString("message"));
                                Bundle bundle = new Bundle();
                                bundle.putString("from", "User");
                                ErrorMessage.I_clear(GlassWorkActivity.this, UserHomeActivity.class, bundle);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(GlassWorkActivity.this, object.getString("message"));
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
            ErrorMessage.T(GlassWorkActivity.this, "No Internet");
        }
    }
}
