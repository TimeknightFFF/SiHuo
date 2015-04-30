package com.sunshine.sihuo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.sunshine.sihuo.R;
import com.sunshine.sihuo.views.MyGridView;

/**
 * Created by Administrator on 2015/4/30.
 */
public class FindFragment extends Fragment {

    private ViewPager viewPager;
    private MyGridView myGridView;
    private ListView listView;
    private ImageView image01,image02,image03,image04,image05;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find, null);

        // 初始化控件
        init(view);


        return view;
    }

    private void init(View view) {
        viewPager= ((ViewPager) view.findViewById(R.id.find_viewpager));
        myGridView= ((MyGridView) view.findViewById(R.id.find_gridView));
        listView= ((ListView) view.findViewById(R.id.find_listView));

        image01= ((ImageView) view.findViewById(R.id.find_image_01));
        image02= ((ImageView) view.findViewById(R.id.find_image_02));
        image03= ((ImageView) view.findViewById(R.id.find_image_03));
        image04= ((ImageView) view.findViewById(R.id.find_image_04));
        image05= ((ImageView) view.findViewById(R.id.find_image_05));
    }
}
