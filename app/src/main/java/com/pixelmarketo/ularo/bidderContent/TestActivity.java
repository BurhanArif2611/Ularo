package com.pixelmarketo.ularo.bidderContent;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.bidderContent.adapter.BidderHomeAdapter;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.database.UserProfileModel;
import com.pixelmarketo.ularo.model.CategoryModel;
import com.pixelmarketo.ularo.userContent.UserHomeActivity;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.SavedData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends BaseActivity {

    @BindView(R.id.radiogroup1)
    RadioGroup radiogroup1;
    @BindView(R.id.radiogroup2)
    RadioGroup radiogroup2;
    @BindView(R.id.radiogroup3)
    RadioGroup radiogroup3;
    @BindView(R.id.radiogroup4)
    RadioGroup radiogroup4;
    @BindView(R.id.btnsubmit)
    Button btnsubmit;
    int q1 = 0, q2 = 0, q3 = 0, q4 = 0;
    ArrayList<String> question_no = new ArrayList<>();
    ArrayList<String> answer_no = new ArrayList<>();
    ArrayList<Integer> mark = new ArrayList<>();
    String id;


    @Override
    protected int getContentResId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        id=bundle.getString("id");

        setToolbarWithBackButton("Test Yourself");
    }

    @OnClick(R.id.btnsubmit)
    public void onViewClicked() {
        q1 = radiogroup1.getCheckedRadioButtonId();
        q2 = radiogroup2.getCheckedRadioButtonId();
        q3 = radiogroup3.getCheckedRadioButtonId();
        q4 = radiogroup4.getCheckedRadioButtonId();

        if ((q1 == -1) || (q2 == -1) || (q3 == -1) || (q4 == -1)) {
            ErrorMessage.T(this, "Please attempt all quesions are complusary");

        } else {
            RadioButton radioButton1 = (RadioButton) findViewById(q1);
            RadioButton radioButton2 = (RadioButton) findViewById(q2);
            RadioButton radioButton3 = (RadioButton) findViewById(q3);
            RadioButton radioButton4 = (RadioButton) findViewById(q4);

            ErrorMessage.E("1===" + radioButton1.getText());
            ErrorMessage.E("2===" + radioButton2.getText());
            ErrorMessage.E("3===" + radioButton3.getText());
            ErrorMessage.E("4===" + radioButton4.getText());


            int ans1 = marking("1", radiogroup1.indexOfChild(findViewById(q1)));
            int ans2 = marking("2", radiogroup2.indexOfChild(findViewById(q2)));
            int ans3 = marking("3", radiogroup3.indexOfChild(findViewById(q3)));
            int ans4 = marking("4", radiogroup4.indexOfChild(findViewById(q4)));

            //add questions in list
            question_no.add("Work Experience (in years)");
            question_no.add(" No. of Projects Completed");
            question_no.add(" Work Force");
            question_no.add("Area of Work (City)");
            ErrorMessage.E("question_no[]  " + question_no);

            //add answers in list
            answer_no.add(String.valueOf(radioButton1.getText()));
            answer_no.add(String.valueOf(radioButton2.getText()));
            answer_no.add(String.valueOf(radioButton3.getText()));
            answer_no.add(String.valueOf(radioButton4.getText()));
            ErrorMessage.E("answer_no[]  " + answer_no);

            //add marks in list
            mark.add(ans1);
            mark.add(ans2);
            mark.add(ans3);
            mark.add(ans4);
            ErrorMessage.E("mark[]  " + mark);

            send();

        }
    }

    public void send() {
        if (NetworkUtil.isNetworkAvailable(TestActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(TestActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.question(id, get_question(), get_answer(), get_mark());
            //UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id()
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
                            ErrorMessage.E("get>>>" + jsonObject.toString());
                            materialDialog.dismiss();
                            if (jsonObject.getString("status").equals("200")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("user_info");

                                String responseData = jsonObject.toString();
                                ErrorMessage.E("responseData" + responseData);
                                UserProfileModel userProfileModel = new UserProfileModel();
                                userProfileModel.setDisplayName(jsonObject1.getString("fname"));
                                userProfileModel.setUser_id(jsonObject1.getString("user_id"));
                                userProfileModel.setUserPhone(jsonObject1.getString("mobile"));
                                userProfileModel.setEmaiiId(jsonObject1.getString("address"));
                                userProfileModel.setProfile_pic(jsonObject1.getString("profile_image"));
                                userProfileModel.setPolice(jsonObject1.getString("police_verification_report"));
                                userProfileModel.setAadhar(jsonObject1.getString("adhar_card"));
                                userProfileModel.setDistrict(jsonObject1.getString("district"));
                                userProfileModel.setCity(jsonObject1.getString("city"));

                                UserProfileHelper.getInstance().delete();
                                userProfileModel.setProvider("bidder");
                                UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                Bundle bundle = new Bundle();
                                bundle.putString("from", "Bidder");
                                ErrorMessage.I_clear(TestActivity.this, BidderHomeActivity.class, bundle);

                            } else {
                                materialDialog.dismiss();
                                ErrorMessage.T(TestActivity.this, jsonObject.getString("message"));
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
            ErrorMessage.T(TestActivity.this, "No Internet");
        }
    }

    public String get_question() {
        String item = "", st = "", st1 = "";
        String[] itemsname = new String[question_no.size()];
        for (int index = 0; index < question_no.size(); index++) {
            itemsname[index] = String.valueOf(question_no.get(index));

            item = TextUtils.join(",", itemsname);
            ErrorMessage.E("item  " + item);

        }
        return item;
    }


    public String get_answer() {
        String item = "", st = "", st1 = "";
        String[] itemsname = new String[answer_no.size()];
        for (int index = 0; index < answer_no.size(); index++) {
            itemsname[index] = String.valueOf(answer_no.get(index));

            item = TextUtils.join(",", itemsname);
            ErrorMessage.E("item  " + item);

        }
        return item;
    }

    public String get_mark() {
        String item = "", st = "", st1 = "";
        String[] itemsname = new String[mark.size()];
        for (int index = 0; index < mark.size(); index++) {
            itemsname[index] = String.valueOf(mark.get(index));

            item = TextUtils.join(",", itemsname);
            ErrorMessage.E("item  " + item);

        }
        return item;
    }

    int marking(String s, int i) {
        int mark = 0;
        if (s.equalsIgnoreCase("4")) {
            switch (i) {
                case 0:

                    mark = 5;
                    break;
                case 1:

                    mark = 5;

                    break;
                case 2:

                    mark = 10;

                    break;

                default:

                    break;
            }

        } else {
            switch (i) {
                case 0:

                    mark = 2;
                    break;
                case 1:

                    mark = 4;

                    break;
                case 2:

                    mark = 6;

                    break;
                case 3:

                    mark = 8;

                    break;
                case 4:

                    mark = 10;

                    break;
                default:

                    break;
            }
        }
        return mark;
    }

    @Override
    public void onBackPressed() {

    }
}
