package com.pixelmarketo.ularo.userContent;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.bidderContent.BidderOnCategortySelecActivity;
import com.pixelmarketo.ularo.bidderContent.adapter.BidderSelectionAdapeter;
import com.pixelmarketo.ularo.model.CategoryModel;
import com.pixelmarketo.ularo.model.CategoryResult;
import com.pixelmarketo.ularo.userContent.adapter.SubCategoryAdapter;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ElectricianActivity extends BaseActivity {

    @BindView(R.id.title_txt)
    TextView titleTxt;

    String id, data;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    SubCategoryAdapter adapter;
    CategoryResult categoryResult;
    @Override
    protected int getContentResId() {
        return R.layout.activity_electrician;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("");
        titleTxt.setText("Electrician");

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        categoryResult  =(CategoryResult) bundle.getSerializable("data");
        ErrorMessage.E("data" + data);
        ErrorMessage.E("data" + id);
        inti();

    }

    private void inti() {
        try {
            if (categoryResult.getResult().size() > 0) {
                    adapter = new SubCategoryAdapter(ElectricianActivity.this, categoryResult.getResult());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ElectricianActivity.this, RecyclerView.VERTICAL, false);
                    recycler.setLayoutManager(layoutManager);
                    recycler.setAdapter(adapter);
                }

        } catch (Exception e) {
            ErrorMessage.E("dfs"+e.getMessage());
            e.printStackTrace();
        }
    }
}
    /*@OnClick({R.id.cardopen, R.id.cardunder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cardopen:
                Bundle bundle=new Bundle();
                bundle.putString("data","open");
                ErrorMessage.I(this,TablayoutActivity.class,bundle);
                break;
            case R.id.cardunder:
                Bundle bundle1=new Bundle();
                bundle1.putString("data","under ground");
                ErrorMessage.I(this,TablayoutActivity.class,bundle1);
                break;
        }
    }*/

