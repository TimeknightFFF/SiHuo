package com.sunshine.sihuo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sunshine.sihuo.MineNameInfoActivity;
import com.sunshine.sihuo.MyBuyActivity;
import com.sunshine.sihuo.MyFriendActivity;
import com.sunshine.sihuo.MyPageActivity;
import com.sunshine.sihuo.MyPurseActivity;
import com.sunshine.sihuo.MySaleActivity;
import com.sunshine.sihuo.MySaleOutActivity;
import com.sunshine.sihuo.MySettingActivity;
import com.sunshine.sihuo.R;

/**
 * Created by PLJ on 2015/5/3.
 */
public class MyFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout mine_user, my_page, my_purse, mine_salse, mine_saleout, mine_buy, mine_setting, mine_friends;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mine, null);
        mine_user = (RelativeLayout) view.findViewById(R.id.mine_user);
        my_page = (RelativeLayout) view.findViewById(R.id.my_page);
        my_purse = (RelativeLayout) view.findViewById(R.id.my_purse);
        mine_salse = (RelativeLayout) view.findViewById(R.id.mine_salse);
        mine_saleout = (RelativeLayout) view.findViewById(R.id.mine_saleout);
        mine_buy = (RelativeLayout) view.findViewById(R.id.mine_buy);
        mine_setting = (RelativeLayout) view.findViewById(R.id.mine_setting);
        mine_friends = (RelativeLayout) view.findViewById(R.id.mine_friends);
        mine_user.setOnClickListener(this);
        my_page.setOnClickListener(this);
        my_purse.setOnClickListener(this);
        mine_salse.setOnClickListener(this);
        mine_saleout.setOnClickListener(this);
        mine_buy.setOnClickListener(this);
        mine_setting.setOnClickListener(this);
        mine_friends.setOnClickListener(this);
        return view;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.mine_user:
                Intent intent1 = new Intent(getActivity(), MineNameInfoActivity.class);
                startActivity(intent1);
                break;
            case R.id.my_page:
                Intent intent2 = new Intent(getActivity(), MyPageActivity.class);
                startActivity(intent2);
                break;
            case R.id.my_purse:

                Intent intent3 = new Intent(getActivity(), MyPurseActivity.class);
                startActivity(intent3);
                break;
            case R.id.mine_salse:
                Intent intent4 = new Intent(getActivity(), MySaleActivity.class);
                startActivity(intent4);
                break;
            case R.id.mine_saleout:
                Intent intent5 = new Intent(getActivity(), MySaleOutActivity.class);
                startActivity(intent5);
                break;
            case R.id.mine_buy:
                Intent intent6 = new Intent(getActivity(), MyBuyActivity.class);
                startActivity(intent6);
                break;
            case R.id.mine_setting:
                Intent intent7 = new Intent(getActivity(), MySettingActivity.class);
                startActivity(intent7);
                break;
            case R.id.mine_friends:
                Intent intent8 = new Intent(getActivity(), MyFriendActivity.class);
                startActivity(intent8);
                break;
        }
    }
}
