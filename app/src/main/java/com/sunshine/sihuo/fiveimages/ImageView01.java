package com.sunshine.sihuo.fiveimages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sunshine.sihuo.R;
import com.sunshine.sihuo.adapters.Show_GridView_Adapter;
import com.sunshine.sihuo.beans.Index_Info;
import com.sunshine.sihuo.urls.Kind_Url;
import com.sunshine.sihuo.utils.Parser_find_Kind;
import com.sunshine.sihuo.utils.SysApplication;

import java.util.List;

public class ImageView01 extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private PullToRefreshGridView gv_five_imgeview;
    private HttpUtils utils = new HttpUtils();
    private List<Index_Info> listInfo;
    private TextView imge_tv;
    String[] str = {"精致生活", "妈咪宝贝", "最可信", "白菜价", "全新特惠"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view01);
        gv_five_imgeview = ((PullToRefreshGridView) findViewById(R.id.gv_five_imgeview));

        SysApplication.getInstance().addActivity(this);
        imge_tv = (TextView) findViewById(R.id.imge_tv);
        Intent intent = getIntent();
        int position = intent.getIntExtra("Position", 0);
        imge_tv.setText(str[position]);
        // 获取集合数据
        getListInfo(position);
    }

    String url;

    private void getListInfo(final int position) {
        // 获取json网络数据

        url = Kind_Url.imgStr.get(position);
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                String result = objectResponseInfo.result;

                Log.v("result", result);
                // 解析数据
                listInfo = Parser_find_Kind.getListInfo(result);
                gv_five_imgeview.setAdapter(new Show_GridView_Adapter(listInfo, ImageView01.this));
                gv_five_imgeview.setOnItemClickListener(ImageView01.this);

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(ImageView01.this, Five_Image.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("itemId", i);
        intent.putExtra("itemId", i);
        intent.putExtra("url", url);
        startActivity(intent);


    }
}
