package com.sunshine.sihuo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sunshine.sihuo.adapters.ShowMoreLAdapter;
import com.sunshine.sihuo.beans.MoreBeans;
import com.sunshine.sihuo.urls.Kind_Url;
import com.sunshine.sihuo.utils.ParserShowMore;

import java.util.List;

/**
 * 更多的listView的布局
 */

public class ShowMoreActivity extends FragmentActivity {

    private HttpUtils utils;

    private ImageButton imageButton;
    private ListView listView;
    private List<MoreBeans> moreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_more);

        utils = new HttpUtils();

        int position = getIntent().getIntExtra("position", 7);
        imageButton = ((ImageButton) findViewById(R.id.show_kind_back));
        listView = ((ListView) findViewById(R.id.show_more_list));

        getMoreList(position);

    }

    private void getMoreList(int position) {

        utils.send(HttpRequest.HttpMethod.GET, Kind_Url.urlStr.get(position), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                String result = objectResponseInfo.result;

                moreList = ParserShowMore.getMoreList(result);

                listView.setAdapter(new ShowMoreLAdapter(ShowMoreActivity.this, moreList));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(ShowMoreActivity.this,More_Kind_Activity.class);
                        intent.putExtra("more_lv_position", i);
                        intent.putExtra("more_lv_str", Kind_Url.MoreStr.get(i));
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
