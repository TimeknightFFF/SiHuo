package com.sunshine.sihuo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.sunshine.sihuo.CommentActivity;
import com.sunshine.sihuo.GoodActivity;
import com.sunshine.sihuo.R;

/**
 * Created by PLJ on 2015/5/3.
 */
public class MessageFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout msg_rl1,msg_rl2;
    private ListView msg_lv;

    public MessageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.massage_main, null);

        msg_rl1= (RelativeLayout) view.findViewById(R.id.msg_rl1);
        msg_lv= (ListView) view.findViewById(R.id.msg_lv);
        msg_rl2= (RelativeLayout) view.findViewById(R.id.msg_rl2);

        msg_rl1.setOnClickListener(this );
        msg_rl2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.msg_rl1:
                Intent intent1=new Intent(getActivity(),CommentActivity.class);
                startActivity(intent1);
                break;
            case R.id.msg_rl2:

                Intent intent2=new Intent(getActivity(),GoodActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
