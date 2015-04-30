package com.sunshine.fragment_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sunshine.sihuo.R;

/**
 * Created by aaa on 15-4-30.
 */
public class Fragment_mine extends Fragment {
    private ImageView icon_default;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=View.inflate(getActivity(), R.layout.mine,null);


        return view;
    }


}
