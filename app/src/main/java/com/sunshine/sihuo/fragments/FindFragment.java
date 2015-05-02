package com.sunshine.sihuo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sunshine.sihuo.R;
import com.sunshine.sihuo.ShowKindActivity;
import com.sunshine.sihuo.ShowMoreActivity;
import com.sunshine.sihuo.adapters.Find_GridView_Adapter;
import com.sunshine.sihuo.adapters.Find_List_Adapter;
import com.sunshine.sihuo.beans.Banner;
import com.sunshine.sihuo.beans.Hot_category;
import com.sunshine.sihuo.beans.Index_Info;
import com.sunshine.sihuo.beans.Special_topic;
import com.sunshine.sihuo.urls.Find_Url;
import com.sunshine.sihuo.utils.Parser_Find_list;
import com.sunshine.sihuo.utils.Parser_find_L;
import com.sunshine.sihuo.views.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/4/30.
 */
public class FindFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ViewPager viewPager;
    private GridView gridView;
    private ListView listView;
    private ImageView image01, image02, image03, image04, image05;
    private HashMap<String, List> map;
    private List<Banner> banners;
    private List<Special_topic> specialTopic;

    private int index = 0;
    /**
     * 控制ViewPager的页面跳转
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                viewPager.setCurrentItem(index % banners.size());
                index++;
                handler.sendEmptyMessageDelayed(0, 5000);
            }
        }
    };

    // 加载数据
    private HttpUtils utils = new HttpUtils();
    private View headView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find, null);

        listView = ((ListView) view.findViewById(R.id.find_listView));
        listView.setItemsCanFocus(true);
        // 初始化控件

        headView = View.inflate(getActivity(), R.layout.headview, null);

        init(headView);

        // 加载数据
        getActivityInfo();

        getListInfo();
        return view;
    }


    private void addViewPager(List<Banner> list) {

        if (banners != null) {
            viewPager.setAdapter(new ViewPagerFragment(getFragmentManager()));

            handler.sendEmptyMessageDelayed(0, 5000);
        }
    }

    /**
     * 控件初始化
     *
     * @param view View
     */
    private void init(View view) {
        viewPager = ((ViewPager) view.findViewById(R.id.find_viewpager));
        gridView = ((GridView) view.findViewById(R.id.find_grid));

        image01 = ((ImageView) view.findViewById(R.id.find_image_01));
        image02 = ((ImageView) view.findViewById(R.id.find_image_02));
        image03 = ((ImageView) view.findViewById(R.id.find_image_03));
        image04 = ((ImageView) view.findViewById(R.id.find_image_04));
        image05 = ((ImageView) view.findViewById(R.id.find_image_05));

        gridView.setOnItemClickListener(this);

    }

    /**
     * 获取网络数据,首页面有两部分的数据。一个是POST请求的数据，一个是GET请求的数据，先获取GET 请求的数据
     */
    public void getActivityInfo() {


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
                addGridViewInfo(category);

                // TODO 加载特殊推荐
                specialTopic = map.get("special_topic");
                addImageFive(specialTopic);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    /**
     * 思路： 点击GridView后，把相应位置的position传到新建的Activity中
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        if (position != 7) {
            intent = new Intent(getActivity(), ShowKindActivity.class);
        }else if(position == 7){
            intent = new Intent(getActivity(), ShowMoreActivity.class);
        }
        intent.putExtra("position", position);
        startActivity(intent);
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

    /**
     * GridView数据加载
     */
    public void addGridViewInfo(List<Hot_category> list) {

        gridView.setAdapter(new Find_GridView_Adapter(list, getActivity()));

    }

    /**
     * 特殊推荐
     */
    public void addImageFive(List<Special_topic> list) {

        List<String> listOfUrl = new ArrayList<>();
        BitmapUtils utils = new BitmapUtils(getActivity());

        for (Special_topic topic : list) {
            listOfUrl.add(topic.getIcon());
        }
        String url = "http://static.sihuo.in/" + listOfUrl.get(0);
        utils.display(image01, url);

        String url1 = "http://static.sihuo.in/" + listOfUrl.get(1);
        utils.display(image03, url1);

        String ur2 = "http://static.sihuo.in/" + listOfUrl.get(2);
        utils.display(image04, ur2);

        String ur3 = "http://static.sihuo.in/" + listOfUrl.get(3);
        utils.display(image02, ur3);

        String ur4 = "http://static.sihuo.in/" + listOfUrl.get(4);
        utils.display(image05, ur4);

    }

    private void getListInfo() {

        // POST请求的属性
        RequestParams params = new RequestParams();

        // type=2&key=0&top=1&
        params.addBodyParameter("type", "2");
        params.addBodyParameter("key", "0");
        params.addBodyParameter("top", "1");

        utils.send(HttpRequest.HttpMethod.POST, Find_Url.LIST_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                String url = objectResponseInfo.result;
                Log.v("--DEBUG--", "-->  " + url);
                // TODO 解析数据
                List<Index_Info> listInfo = Parser_find_L.getListInfo(url);

                listView.addHeaderView(headView);
                // TODO 给ListView 添加适配器
                listView.setAdapter(new Find_List_Adapter(getActivity(), listInfo));

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}
