package com.sunshine.sihuo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.sunshine.sihuo.Vp_WebView;

/**
 * Created by Administrator on 2015/4/30.
 */
public class Find_ViewPager_Fragment extends Fragment {

    private static final String KEY = "url";
    private static final String KEY1 = "imgString";

    public static Find_ViewPager_Fragment newInstance(String url, String imgString) {
        Log.d("--DEBUG--", "- --> " + url);
        Find_ViewPager_Fragment fragment = new Find_ViewPager_Fragment();

        Bundle bundle = new Bundle();
        bundle.putString(KEY, url);
        bundle.putString(KEY1, imgString);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ImageView imageView = new ImageView(getActivity());

        final String url = "http://static.sihuo.in/" + getArguments().getString(KEY);
        final String imgUrl = getArguments().getString(KEY1);


        BitmapUtils utils = new BitmapUtils(getActivity());


        utils.display(imageView, url);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), Vp_WebView.class);
                Bundle bundle = new Bundle();
                bundle.putString("imageUrl", imgUrl);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        return imageView;
    }
}
