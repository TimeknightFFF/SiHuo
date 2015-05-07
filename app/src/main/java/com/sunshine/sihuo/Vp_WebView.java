package com.sunshine.sihuo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sunshine.sihuo.beans.Banner;
import com.sunshine.sihuo.utils.SysApplication;

/**
 * Created by PLJ on 2015/5/2.
 * <p/>
 * 头部图片webView
 */
public class Vp_WebView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_webview);

        SysApplication.getInstance().addActivity(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String imageUrl = (String) bundle.get("imageUrl");
        final WebView wv1 = (WebView) findViewById(R.id.viewpagerwebview);

        wv1.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv1.loadUrl(imageUrl);
//        WebSettings settings = wv1.getSettings();
//        WebViewClient client = new WebViewClient() {
//        };
//        wv1.setWebViewClient(client);
//        Banner banner=new Banner();
//        String url = banner.getUrl();
        wv1.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        wv1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

    }


}
