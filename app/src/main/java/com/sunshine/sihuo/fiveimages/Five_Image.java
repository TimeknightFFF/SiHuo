package com.sunshine.sihuo.fiveimages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.sunshine.sihuo.R;
import com.sunshine.sihuo.beans.Index_Info;
import com.sunshine.sihuo.utils.Parser_find_L;
import com.sunshine.sihuo.utils.SysApplication;

import java.util.List;

/**
 * Created by PLJ on 2015/5/4.
 */
public class Five_Image extends Activity {


    private ImageView lv_item_info_iv1, lv_item_info_back;
    private TextView lv_item_info_name, lv_item_list_price,
            lv_item_list_content, lv_item_info_price, lv_item_info_good, lv_item_info_number;
    private ImageButton lv_item_list_btn2;
    private ImageView lv_item_list_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }


    //显示Gridview的数据

    private void getListInfo() {

        RequestParams params = new RequestParams();
//        Intent intent1 = getIntent();
//        int position = intent1.getIntExtra("Position", 0);
        Intent intent = getIntent();
        final int itemId = intent.getIntExtra("itemId", 0);
        final String imgUrl = intent.getStringExtra("url");
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


                final Index_Info info = listInfo.get(itemId);

                BitmapUtils bmutils = new BitmapUtils(Five_Image.this);

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
                lv_item_list_price.setText(info.getPrice());
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


}
