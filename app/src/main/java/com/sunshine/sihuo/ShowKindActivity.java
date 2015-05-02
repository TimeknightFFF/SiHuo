package com.sunshine.sihuo;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sunshine.sihuo.adapters.Show_GridView_Adapter;
import com.sunshine.sihuo.beans.Index_Info;
import com.sunshine.sihuo.urls.Kind_Url;
import com.sunshine.sihuo.utils.Parser_find_Kind;

import java.util.List;


public class ShowKindActivity extends FragmentActivity {

    private PullToRefreshGridView gridView;
    private List<Index_Info> listInfo;


    private HttpUtils utils = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_kind);

        gridView = ((PullToRefreshGridView) findViewById(R.id.show_kind_grid));

        // 获取从FindFragment中传过来的数据
        int position = getIntent().getIntExtra("position", 0);

        // 获取集合数据
            getListInfo(position);
    }

    private void getListInfo(final int position) {
        // 获取json网络数据

        String url = Kind_Url.urlStr.get(position);
        Log.v("getListInfo", url);
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                String result = objectResponseInfo.result;

                Log.v("result", result);
                // 解析数据
                listInfo = Parser_find_Kind.getListInfo(result);
                gridView.setAdapter(new Show_GridView_Adapter(listInfo, ShowKindActivity.this));
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

}
