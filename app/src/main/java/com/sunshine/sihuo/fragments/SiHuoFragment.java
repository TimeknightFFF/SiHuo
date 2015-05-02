package com.sunshine.sihuo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.sunshine.sihuo.R;
import com.sunshine.sihuo.RequestFriends;

/**
 * Created by PLJ on 2015/5/2.
 */
public class SiHuoFragment extends Fragment implements View.OnClickListener {
private ImageButton request_btn_add;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sihuo, null);
        Button btn = (Button) view.findViewById(R.id.sihuo_btn);
        btn.setOnClickListener(this);

        request_btn_add= (ImageButton) view.findViewById(R.id.request_btn_add);


        request_btn_add.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sihuo_btn:
                Intent intent = new Intent(getActivity(), RequestFriends.class);
                startActivity(intent);
                break;
            case R.id.request_btn_add:


                //构造方法的参数： 参数1上下文对象   参数2靠山（popmenu出现的位置）
                PopupMenu popMenu=new PopupMenu(getActivity(), request_btn_add);
                popMenu.getMenuInflater().inflate(R.menu.popupmenu, popMenu.getMenu());

                popMenu.show();
                break;
        }

    }
}
