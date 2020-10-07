package com.pixelmarketo.ularo.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.pixelmarketo.ularo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThankuActivity extends AppCompatActivity {

    @BindView(R.id.btnback)
    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanku);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnback)
    public void onViewClicked() {
        finish();
    }
}
