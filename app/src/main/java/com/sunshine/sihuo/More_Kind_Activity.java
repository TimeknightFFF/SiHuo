package com.sunshine.sihuo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sunshine.sihuo.adapters.Show_GridView_Adapter;
import com.sunshine.sihuo.beans.Index_Info;
import com.sunshine.sihuo.utils.Parser_find_Kind;
import com.sunshine.sihuo.views.GV_Info;

import java.util.List;

/**
 * Created by PLJ on 2015/5/5.
 * 更多页面的展示和点击事件
 */
public class More_Kind_Activity extends Activity {

    private PullToRefreshGridView gridView;
    private List<Index_Info> listInfo;


    private HttpUtils utils = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_kind);

        gridView = ((PullToRefreshGridView) findViewById(R.id.show_kind_grid));

        // 获取从FindFragment中传过来的数据
        int position = getIntent().getIntExtra("more_lv_position", 0);
        String url = getIntent().getStringExtra("more_lv_str");

        // 获取集合数据
        getListInfo(position, url);
    }

    private void getListInfo(final int position, final String url) {
        // 获取json网络数据

        Log.v("getListInfo", url);
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                String result = objectResponseInfo.result;

                Log.v("result", result);
                // 解析数据
                listInfo = Parser_find_Kind.getListInfo(result);
                gridView.setAdapter(new Show_GridView_Adapter(listInfo, More_Kind_Activity.this));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent=new Intent(More_Kind_Activity.this, GV_Info.class);
                        intent.putExtra("gv_position",i);
                        intent.putExtra("gv_url",url);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}
