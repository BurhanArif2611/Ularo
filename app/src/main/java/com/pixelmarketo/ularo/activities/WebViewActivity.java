package com.pixelmarketo.ularo.activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {


    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.full_screen_img)
    PhotoView fullScreenImg;
    String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        path= bundle.getString("path");
        ErrorMessage.E(""+ bundle.getString("path"));
        if (path.contains("pdf")){
            webView.setVisibility(View.VISIBLE);
            fullScreenImg.setVisibility(View.GONE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://docs.google.com/viewer?url=" + bundle.getString("path"));
        }
        else {
            fullScreenImg.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);

            Glide.with(this).load(bundle.getString("path")).placeholder(R.drawable.ic_attach).into(fullScreenImg);
        }

    }
}
