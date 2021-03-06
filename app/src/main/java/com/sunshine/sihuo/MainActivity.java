package com.sunshine.sihuo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.sunshine.sihuo.fragments.FindFragment;
import com.sunshine.sihuo.fragments.MessageFragment;
import com.sunshine.sihuo.fragments.MyFragment;
import com.sunshine.sihuo.fragments.PhotoFragment;
import com.sunshine.sihuo.fragments.SiHuoFragment;
import com.sunshine.sihuo.utils.SysApplication;


public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    public static String userJID=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SysApplication.getInstance().addActivity(this);
        Intent intent = getIntent();
        userJID=intent.getStringExtra("userJID");
        radioGroup= ((RadioGroup) findViewById(R.id.main_radio));

        // 点击监听
        radioGroup.setOnCheckedChangeListener(this);
        addFragment(new SiHuoFragment());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.main_radio01:
                addFragment(new SiHuoFragment());
                break;
            case R.id.main_radio02:
                addFragment(new FindFragment());
                break;
            case R.id.main_radio03:
                addFragment(new PhotoFragment());
                break;
            case R.id.main_radio04:
                addFragment(new MessageFragment());
                break;
            case R.id.main_radio05:
                addFragment(new MyFragment());
                break;
        }
    }

    /**
     * 代码加载Fragment
     */
    public void addFragment(Fragment fragment){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_container,fragment);
        transaction.commit();
    }
}
