package com.udacity.spacebinge.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.udacity.spacebinge.R;
import com.udacity.spacebinge.utils.DotProgressBar;

public class WebActivity extends AppCompatActivity {

    Intent receiveI;
    String articleUrl;
    WebView news_web_view;
    DotProgressBar dotProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        news_web_view = findViewById(R.id.news_web_view);
        dotProgressBar = findViewById(R.id.dot_progress_bar);

        receiveI = getIntent();

        if (receiveI == null) {
            return;
        }
        articleUrl = receiveI.getStringExtra("newsArticleUrl");

        if (!TextUtils.isEmpty(articleUrl)) {
            news_web_view.setWebViewClient(new MyWebViewClient());
            news_web_view.getSettings().setJavaScriptEnabled(true);
            news_web_view.getSettings().setSupportZoom(true);
            news_web_view.getSettings().setBuiltInZoomControls(true);
            news_web_view.loadUrl(articleUrl);
            news_web_view.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.getSettings().setSupportZoom(true);
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            dotProgressBar.setVisibility(View.GONE);
        }
    }
}
