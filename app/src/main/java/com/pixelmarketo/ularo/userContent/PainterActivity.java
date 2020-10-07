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

public class PainterActivity extends BaseActivity {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.painttype)
    Spinner painttype;
    @BindView(R.id.servicetype)
    Spinner servicetype;
    @BindView(R.id.layretype)
    Spinner layretype;
    @BindView(R.id.dimension)
    Spinner dimension;
    @BindView(R.id.etarea)
    EditText etarea;
    @BindView(R.id.btnsend)
    Button btnsend;
    String id = "", select_type = "", select_type_of_paint = "", select_type_of_layer = "", select_type_of_dimension = "";
    ArrayList<String> type = new ArrayList<String>();
    ArrayList<String> type_of_paint = new ArrayList<String>();
    ArrayList<String> type_of_layer = new ArrayList<String>();
    ArrayList<String> type_of_dimension = new ArrayList<String>();
    @BindView(R.id.radiogroup1)
    RadioGroup radiogroup1;
    String quick_mode="0";

    @Override
    protected int getContentResId() {
        return R.layout.activity_painter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("");
        titleTxt.setText("Painter");
        gettype();
        getPainttype();
        getlayer();
        getdimension();
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

    private void getdimension() {
        type_of_dimension.add(0, "INNER");
        type_of_dimension.add(1, "OUTER");
        type_of_dimension.add(2, "BOTH");
        //INNER, OUTER, BOTH
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PainterActivity.this, android.R.layout.simple_spinner_dropdown_item, type_of_dimension);
        dimension.setAdapter(adapter);

        dimension.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_type_of_dimension = type_of_dimension.get(position).toString();
                ErrorMessage.E("type of glass" + select_type_of_dimension);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getlayer() {
        type_of_layer.add(0, "Single");
        type_of_layer.add(1, "Double");
        type_of_layer.add(2, "Triple");
        //Single,Double,Triple
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PainterActivity.this, android.R.layout.simple_spinner_dropdown_item, type_of_layer);
        layretype.setAdapter(adapter);

        layretype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_type_of_layer = type_of_layer.get(position).toString();
                ErrorMessage.E("type of glass" + select_type_of_layer);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getPainttype() {
        type_of_paint.add(0, "Plastic Paint");
        type_of_paint.add(1, "Oil Paint");
        type_of_paint.add(2, "Putty");
        type_of_paint.add(3, "Texture");
        //Plastic Paint,Oil Paint,Putty,Texture
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PainterActivity.this, android.R.layout.simple_spinner_dropdown_item, type_of_paint);
        painttype.setAdapter(adapter);

        painttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_type_of_paint = type_of_paint.get(position).toString();
                ErrorMessage.E("type of glass" + select_type_of_paint);
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
        if (NetworkUtil.isNetworkAvailable(PainterActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(PainterActivity.this);
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
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PainterActivity.this, android.R.layout.simple_spinner_dropdown_item, type);
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
            ErrorMessage.T(PainterActivity.this, "No Internet");
        }
    }

    private void form_submit() {
        if (NetworkUtil.isNetworkAvailable(PainterActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(PainterActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.form(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), id, "", etarea.getText().toString(), select_type, select_type_of_paint, select_type_of_layer, "", select_type_of_dimension, "",
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",quick_mode);

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
                                ErrorMessage.T(PainterActivity.this, object.getString("message"));
                                Bundle bundle = new Bundle();
                                bundle.putString("from", "User");
                                ErrorMessage.I_clear(PainterActivity.this, UserHomeActivity.class, bundle);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(PainterActivity.this, object.getString("message"));
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
            ErrorMessage.T(PainterActivity.this, "No Internet");
        }
    }
}
