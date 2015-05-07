package com.sunshine.sihuo;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunshine.sihuo.chatpackage.ChatActivity;
import com.sunshine.sihuo.utils.DataCleanManager;
import com.sunshine.sihuo.utils.SysApplication;


public class MySettingActivity extends FragmentActivity implements View.OnClickListener {

    private ImageButton imageButton;
    private RelativeLayout layout01, layout02, layout03, layout04, layout05;
    private Button btn_out;
    private TextView size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_setting);

        SysApplication.getInstance().addActivity(this);

        // 初始化控件
        init();

        // 跳转到上一个界面
        imageButton.setOnClickListener(this);
        /**
         * 用户反馈，原APP上是和服务器聊天，没有客服（水平有限）就直接跳转到聊天界面。客服的写死userJID
         */
        layout01.setOnClickListener(this);

        // 用户评价的是调用手机上的APPStore 软件
        layout02.setOnClickListener(this);

        // 用户协议
        layout03.setOnClickListener(this);

        // 清空缓存
        layout05.setOnClickListener(this);
        layoutOfClear();

        // 退出
        btn_out.setOnClickListener(this);

    }

    private void init() {
        imageButton = (ImageButton) findViewById(R.id.imgButLeft);

        layout01 = (RelativeLayout) findViewById(R.id.set_of_suggest);
        layout02 = (RelativeLayout) findViewById(R.id.set_of_score);
        layout03 = (RelativeLayout) findViewById(R.id.set_of_protocol);
        layout04 = (RelativeLayout) findViewById(R.id.set_of_new);
        layout05 = (RelativeLayout) findViewById(R.id.set_of_clear);

        btn_out = (Button) findViewById(R.id.set_of_out);
        size = (TextView) findViewById(R.id.set_of_size);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.imgButLeft:
                finish();
                break;
            case R.id.set_of_suggest:
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("userJID", "aaa@10.0.154.2/Smack");
                startActivity(intent);
                break;
            case R.id.set_of_score:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intent1 != null) {
                    startActivity(intent1);
                }else {
                    Toast.makeText(this,"您的手机上没有评分软件",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.set_of_protocol:
                String url = "http://www.sihuo.com/?c=index&a=registerAgreement&v=1.3.4&os=android";
                Intent intent2 = new Intent(this, Vp_WebView.class);
                intent2.putExtra("imageUrl", url);
                startActivity(intent2);
                break;
            case R.id.set_of_new:
                break;
            case R.id.set_of_clear:
                DataCleanManager.clearAllCache(this);
                layoutOfClear();
                break;
            case R.id.set_of_out:
                SysApplication.getInstance().exit();
                break;
        }
    }

    /**
     * 计算缓存大小，并清空
     */
    public void layoutOfClear() {

        try {
            String cacheSize = DataCleanManager.getTotalCacheSize(this);

            size.setText(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
