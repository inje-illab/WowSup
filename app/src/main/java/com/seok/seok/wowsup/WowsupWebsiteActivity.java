package com.seok.seok.wowsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//웹 싸이트 연결할 클래스 엑티비티
public class WowsupWebsiteActivity extends AppCompatActivity {

    //웹뷰 관련
    private WebView mWebView;
    private WebSettings mWebSettings;

    //생성했을때 웹뷰와 싸이트 연결
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wowsup_website);

        mWebView = findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("http://www.heywowsup.com/wordpress/");

    }
}
