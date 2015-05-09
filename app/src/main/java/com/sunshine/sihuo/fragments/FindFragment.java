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
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.sunshine.sihuo.LV_Info;
import com.sunshine.sihuo.R;
import com.sunshine.sihuo.ShowKindActivity;
import com.sunshine.sihuo.ShowMoreActivity;
import com.sunshine.sihuo.adapters.Find_GridView_Adapter;
import com.sunshine.sihuo.adapters.Find_List_Adapter;
import com.sunshine.sihuo.beans.Banner;
import com.sunshine.sihuo.beans.Hot_category;
import com.sunshine.sihuo.beans.Index_Info;
import com.sunshine.sihuo.beans.Special_topic;
import com.sunshine.sihuo.fiveimages.ImageView01;
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
public class FindFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ViewPager viewPager;
    private GridView gridView;
    private ListView listView;
    private ImageView image01, image02, image03, image04, image05;
    private HashMap<String, List> map;
    private List<Banner> banners;
    private List<Special_topic> specialTopic;
    private ImageView[] images = new ImageView[5];

    private  View view;
    private int index = 0;
    /**
     * 控制ViewPager的页面跳转
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (banners != null) {
                    viewPager.setAdapter(new ViewPagerFragment(getFragmentManager()));
                }

            }else{
                viewPager.setCurrentItem(index % 3);
                index++;
                handler.sendEmptyMessageDelayed(2, 2000);

            }
        }
    };

    // 加载数据
    private HttpUtils utils = new HttpUtils();
    private View headView;

    private ImageView imgvDot1,imgvDot2,imgvDot3;
    private final ImageView[] imgvDots = new ImageView[3];

    private SwipeRefreshLayout refresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.find, null);

        refresh= ((SwipeRefreshLayout) view.findViewById(R.id.refresh));

        listView = ((ListView) view.findViewById(R.id.find_listView));
        listView.setItemsCanFocus(true);
        // 初始化控件

        headView = View.inflate(getActivity(), R.layout.headview, null);


        init(headView);


        // 加载数据
        getActivityInfo();

        getListInfo();

        refresh.setOnRefreshListener(this);


        return view;
    }


//    private void addViewPager(List<Banner> list) {
//
//        if (banners != null) {
//            viewPager.setAdapter(new ViewPagerFragment(getFragmentManager()));
//
//            handler.sendEmptyMessageDelayed(0, 5000);
//
//        }
//    }

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

        imgvDot1 = ((ImageView) view.findViewById(R.id.imgvDot1));
        imgvDot2 = ((ImageView) view.findViewById(R.id.imgvDot2));
        imgvDot3 = ((ImageView) view.findViewById(R.id.imgvDot3));

        imgvDots[0]=imgvDot1;
        imgvDots[1]=imgvDot2;
        imgvDots[2]=imgvDot3;



        image01.setOnClickListener(this);
        image02.setOnClickListener(this);
        image03.setOnClickListener(this);
        image04.setOnClickListener(this);
        image05.setOnClickListener(this);

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
                // addViewPager(banners);
                // 发送消息
                handler.sendEmptyMessage(1);
                handler.sendEmptyMessageDelayed(2, 2000);

                getViewPager();

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

    @Override
    public void onClick(View view) {

        Intent intent = null;

        switch (view.getId()) {

            case R.id.find_image_01:
                intent = new Intent(getActivity(), ImageView01.class);
                intent.putExtra("Position", 0);
                startActivity(intent);
                break;
            case R.id.find_image_02:
                intent = new Intent(getActivity(), ImageView01.class);
                intent.putExtra("Position", 1);
                startActivity(intent);

                break;
            case R.id.find_image_03:
                intent = new Intent(getActivity(), ImageView01.class);
                intent.putExtra("Position", 2);
                startActivity(intent);
                break;
            case R.id.find_image_04:
                intent = new Intent(getActivity(), ImageView01.class);
                intent.putExtra("Position", 3);
                startActivity(intent);
                break;
            case R.id.find_image_05:
                intent = new Intent(getActivity(), ImageView01.class);
                intent.putExtra("Position", 4);
                startActivity(intent);
                break;
        }
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        // 加载数据
        getActivityInfo();
        refresh.setRefreshing(false);
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
            return Find_ViewPager_Fragment.newInstance(banners.get(position).getSrc(), banners.get(position).getUrl());
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

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getActivity(), LV_Info.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("position",i-1);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void getViewPager(){
        //两个文本的管理----》vp翻页
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imgvDots.length; i++) {
                    if (i == position) {
                        imgvDots[i].setImageResource(R.drawable.page_now);
                    } else {
                        imgvDots[i].setImageResource(R.drawable.page);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
