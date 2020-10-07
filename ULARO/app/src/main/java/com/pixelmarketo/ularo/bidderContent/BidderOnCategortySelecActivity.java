package com.pixelmarketo.ularo.bidderContent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.bidderContent.adapter.BidderSelectionAdapeter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BidderOnCategortySelecActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.bid_categry_search_btn)
    EditText bidCategrySearchBtn;
    @BindView(R.id.bid_selecction_list_recler_view)
    RecyclerView bidSelecctionListReclerView;
    List<String> bids;
    BidderSelectionAdapeter adapeter;

    @Override
    protected int getContentResId() {
        return R.layout.activity_bidder_on_categorty_selec;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle.get("title").toString() != null) {
            setToolbarWithBackButton(bundle.get("title").toString());
        } else
            setToolbarWithBackButton("App Selected");
        ButterKnife.bind(this);

        bids= new ArrayList<>();
        bids.add(0,"This is User");
        bids.add(1,"This is Another");
        bids.add(2,"One More User");
        bids.add(3,"Again Its User");
        bids.add(4,"Oh User");
        bids.add(5,"Sit User");
        bids.add(6,"Stop User");
        bids.add(7,"I Am Bored User");
        bids.add(8,"Its Oky User");
        bids.add(9,"Abhi");
        bids.add(10,"Shivani");


        adapeter = new BidderSelectionAdapeter(BidderOnCategortySelecActivity.this,bids);
        LinearLayoutManager layoutManager = new LinearLayoutManager(BidderOnCategortySelecActivity.this,RecyclerView.VERTICAL,false);
        bidSelecctionListReclerView.setLayoutManager(layoutManager);
        bidSelecctionListReclerView.setAdapter(adapeter);


      /*  bidCategrySearchBtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });*/






    }

    private void filter(String toString) {

        List<String> list = new ArrayList<>();

        for (int i = 0;i==bids.size();i++){
            if (toString.toLowerCase().contains(bids.get(i).toLowerCase()) || toString.toUpperCase().contains(bids.get(i).toUpperCase())){
                list.add(bids.get(i));
            }
        }

        adapeter.filterlist(list);

    }
}
