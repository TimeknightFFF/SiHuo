package com.sunshine.sihuo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2015/4/30.
 */
public class Find_ViewPager_Fragment extends Fragment{

    private static final String KEY="url";

    public static Find_ViewPager_Fragment newInstance(String url){
        Log.d("--DEBUG--","- --> "+url);
        Find_ViewPager_Fragment fragment = new Find_ViewPager_Fragment();

        Bundle bundle = new Bundle();
        bundle.putString(KEY,url);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ImageView imageView=new ImageView(getActivity());

        String url ="http://static.sihuo.in/"+getArguments().getString(KEY);
        BitmapUtils utils=new BitmapUtils(getActivity());
        utils.display(imageView,url);

        return imageView;
    }
}
