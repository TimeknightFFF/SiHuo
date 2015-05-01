package com.sunshine.sihuo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.sunshine.sihuo.Beans.My_profile_Data;

import java.util.Date;

/**
 * Created by Administrator on 2015/4/30.
 */
public class Fragment_my_profile extends Fragment {
    private ListView listView;
    private SimpleAdapter adapter;
    private  View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_profile, container, false);
        listView = (ListView) view.findViewById(R.id.list_item);
        adapter=new SimpleAdapter(getActivity(), My_profile_Data.data,R.layout.fragment_my_profile_listitem,
                new String[]{"text1","text2"},new int[]{R.id.text1,R.id.text2});
        listView.setAdapter(adapter);
        return view;
    }

}
