package com.sunshine.sihuo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sunshine.sihuo.beans.Banner;

/**
 * Created by PLJ on 2015/5/2.
 *
 * 头部图片webView
 */
public class Vp_WebView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_webview);
        ;Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String imageUrl = (String) bundle.get("imageUrl");
        final WebView wv1 = (WebView) findViewById(R.id.viewpagerwebview);
        WebSettings settings = wv1.getSettings();
        WebViewClient client = new WebViewClient() {
        };
        wv1.setWebViewClient(client);
        Banner banner=new Banner();
        String url = banner.getUrl();

        wv1.loadUrl(imageUrl);
    }


    }

