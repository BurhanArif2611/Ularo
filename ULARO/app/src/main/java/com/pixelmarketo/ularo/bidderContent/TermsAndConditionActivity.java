package com.pixelmarketo.ularo.bidderContent;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsAndConditionActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected int getContentResId() {
        return R.layout.activity_terms_and_condition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("Terms And Condition");
        ButterKnife.bind(this);

        startWebView("https://www.websitepolicies.com/policies/view/huvfMA8N");

    }

    private void startWebView(String url) {

        webview.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;


            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


            public void onLoadResource(WebView view, String url) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(TermsAndConditionActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();

                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(url);

    }

}
