package com.sunshine.sihuo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.sunshine.sihuo.MainActivity;
import com.sunshine.sihuo.R;
import com.sunshine.sihuo.chatpackage.LoginActivity;


/**
 * Created by aaa on 15-5-4.
 */
public class WelcomeFragment extends Fragment {
    private static final String KEY = "position";
    private int[] images = {R.drawable.navigation_1, R.drawable.navigation_2,
            R.drawable.navigation_3, R.drawable.navigation_4};

    public static WelcomeFragment getFragment(int position) {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, position);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.for_welcomevp, null);
        int position = getArguments().getInt(KEY);
        ImageView imageView = (ImageView) view.findViewById(R.id.welcome_vp_image);
        imageView.setBackgroundResource(images[position]);


        Button btn=(Button) view.findViewById(R.id.welcome_vp_button);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        if(position==3){
            btn.setVisibility(View.VISIBLE);
        }else{
            btn.setVisibility(View.GONE);
        }

        return view;
    }
}
