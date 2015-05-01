package com.sunshine.sihuo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sunshine.sihuo.R;
import com.sunshine.sihuo.beans.Banner;
import com.sunshine.sihuo.beans.Hot_category;
import com.sunshine.sihuo.urls.Find_Url;
import com.sunshine.sihuo.utils.Parser_Find_list;
import com.sunshine.sihuo.views.MyGridView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/4/30.
 */
public class FindFragment extends Fragment {

    private ViewPager viewPager;
    private GridView gridView;
    private ListView listView;
    private ImageView image01, image02, image03, image04, image05;
    private HashMap<String, List> map;
    private List<Banner> banners;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find, null);

        // 初始化控件
        init(view);

        // 加载数据
        getListInfo();
        return view;
    }

    private void addViewPager(List<Banner> list) {

        if (banners != null) {
            viewPager.setAdapter(new ViewPagerFragment(getFragmentManager()));
        }
    }

    /**
     * 控件初始化
     *
     * @param view View
     */
    private void init(View view) {
        viewPager = ((ViewPager) view.findViewById(R.id.find_viewpager));
        gridView = ((GridView) view.findViewById(R.id.find_gridView));
        listView = ((ListView) view.findViewById(R.id.find_listView));

        image01 = ((ImageView) view.findViewById(R.id.find_image_01));
        image02 = ((ImageView) view.findViewById(R.id.find_image_02));
        image03 = ((ImageView) view.findViewById(R.id.find_image_03));
        image04 = ((ImageView) view.findViewById(R.id.find_image_04));
        image05 = ((ImageView) view.findViewById(R.id.find_image_05));
    }

    /**
     * 获取网络数据,首页面有两部分的数据。一个是POST请求的数据，一个是GET请求的数据，先获取GET 请求的数据
     */
    public void getListInfo() {
        // 加载数据
        HttpUtils utils = new HttpUtils();

        utils.send(HttpRequest.HttpMethod.GET, Find_Url.FIRST_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                String json = objectResponseInfo.result;

                // 解析数据
                map = Parser_Find_list.parserList(json);

                // TODO 根据获取到的数据，进行控件数据加载
                // 添加ViewPager 数据, 要把数据提取出来方便给WebView 传送网络地址
                banners = map.get("banner");
                addViewPager(banners);

                // TODO 加载GridView数据
                List<Hot_category> category = map.get("hot_category");

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    /**
     * ViewPager 的适配器
     */
    public class ViewPagerFragment extends FragmentPagerAdapter {

        public ViewPagerFragment(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return Find_ViewPager_Fragment.newInstance(banners.get(position).getSrc());
        }

        @Override
        public int getCount() {
            return banners.size();
        }
    }

}
