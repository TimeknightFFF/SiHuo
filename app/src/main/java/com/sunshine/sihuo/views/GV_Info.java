package com.sunshine.sihuo.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sunshine.sihuo.MainActivity;
import com.sunshine.sihuo.R;
import com.sunshine.sihuo.alipay.sdk.pay.demo.PayDemoActivity;
import com.sunshine.sihuo.beans.Index_Info;
import com.sunshine.sihuo.chatpackage.ChatActivity;
import com.sunshine.sihuo.utils.Parser_find_L;
import com.sunshine.sihuo.utils.SysApplication;

import java.util.List;

public class GV_Info extends Activity implements View.OnClickListener {

    private ImageView lv_item_info_iv1, lv_item_info_back, lv_item_info_iv;
    private TextView lv_item_info_name, lv_item_list_price,
            lv_item_list_content, lv_item_info_price, lv_item_info_good, lv_item_info_number;
    private ImageButton lv_item_list_btn2;
    private ImageView lv_item_list_image;

    private Button pay_money;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lv__info);
        SysApplication.getInstance().addActivity(this);
        initView();
        getListInfo();


    }

    private void initView() {
        lv_item_info_iv1 = (ImageView) findViewById(R.id.lv_item_info_iv1);
        lv_item_list_image = (ImageView) findViewById(R.id.lv_item_list_image);
        lv_item_info_name = (TextView) findViewById(R.id.lv_item_info_name);
        lv_item_list_price = (TextView) findViewById(R.id.lv_item_info_price);
        lv_item_info_price = (TextView) findViewById(R.id.lv_item_info_pre);
        lv_item_list_content = (TextView) findViewById(R.id.lv_item_list_content);

        lv_item_info_good = (TextView) findViewById(R.id.lv_item_info_good);
        lv_item_info_back = (ImageView) findViewById(R.id.lv_item_info_back);

        lv_item_info_number = (TextView) findViewById(R.id.lv_item_info_number);
        lv_item_info_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pay_money = ((Button) findViewById(R.id.btn_pay_money));
        lv_item_info_iv = ((ImageView) findViewById(R.id.lv_item_info_iv));

        pay_money.setOnClickListener(this);
        lv_item_info_iv.setOnClickListener(this);
    }


    //显示Gridview的数据

    private void getListInfo() {

        RequestParams params = new RequestParams();
//        Intent intent1 = getIntent();
//        int position = intent1.getIntExtra("Position", 0);
        Intent intent = getIntent();
        final int position = intent.getIntExtra("gv_position", 0);
        final String imgUrl = intent.getStringExtra("gv_url");
        // type=2&key=0&top=1&
        params.addBodyParameter("type", "2");
        params.addBodyParameter("key", "0");
        params.addBodyParameter("top", "1");
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.POST, imgUrl, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {


                String url = objectResponseInfo.result;
//
//                Log.i("URL", url);
//                Log.v("--DEBUG--", "-->  " + url);
                // TODO 解析数据
                List<Index_Info> listInfo = Parser_find_L.getListInfo(url);


                final Index_Info info = listInfo.get(position);

                BitmapUtils bmutils = new BitmapUtils(GV_Info.this);

                String iv1Url = "http://static.sihuo.in/" + info.getAvatar();

                if (iv1Url != null) {
                    bmutils.display(lv_item_info_iv1, iv1Url);
                } else {

                    lv_item_info_iv1.setImageResource(R.drawable.icon_default_avatr100);
                }
                lv_item_info_name.setText(info.getuName());

                String iv2Url = "http://static.sihuo.in/" + info.getPhoto();
                if (iv2Url != null) {
                    bmutils.display(lv_item_list_image, iv2Url);
                } else {

                    lv_item_list_image.setImageResource(R.drawable.icon_default_avatr100);
                }
                price = info.getPrice();
                Log.v("getListInfo","给price赋值  -->"+price);
                lv_item_list_price.setText(price);
                lv_item_info_price.setText(info.getOri_price());

                lv_item_list_content.setText(info.getDesc());

                lv_item_info_good.setText(info.getpNum() + "");
                lv_item_info_number.setText(info.getcNum() + "");


                lv_item_list_btn2 = (ImageButton) findViewById(R.id.lv_item_list_btn2);
                lv_item_list_btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                        intent.putExtra(Intent.EXTRA_TEXT, "http://www.sihuo.com/?c=html5&a=goods&v=1.3.4&os=android&goods_id=" + info.getGoods_id());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(Intent.createChooser(intent, "分享"));
                    }
                });


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.lv_item_info_iv:
                chat();
                break;
            case R.id.btn_pay_money:
                Intent intent = new Intent(this, PayDemoActivity.class);
                if (!price.equals("0") && !price.equals("0.0")) {
                    Log.v("onClick", "查看获取到的商品价格  >>" + price);
                    double aDouble = Double.parseDouble(price);
                    intent.putExtra("totalMoney", aDouble);
                    startActivity(intent);
                }
                break;
        }

    }

    private void chat() {
        if (MainActivity.userJID != null) {
            /**
             * 点击按钮，把用户的userJID传送到ChatActivity 就可以聊天了。现在没有完善，
             * 只是完成了，自己和自己聊天，自己和好友聊天
             */
            String userJID = MainActivity.userJID;
            Log.v("IMAGE", userJID);

            // 开启聊天会话界面
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("userJID", userJID);
            startActivity(intent);
        }
    }
}
