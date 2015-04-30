package com.sunshine.sihuo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.sunshine.sihuo.fragments.FindFragment;


public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup= ((RadioGroup) findViewById(R.id.main_radio));

        // 点击监听
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.main_radio01:

                break;
            case R.id.main_radio02:
                addFragment(new FindFragment());
                break;
            case R.id.main_radio03:
                break;
            case R.id.main_radio04:
                break;
            case R.id.main_radio05:
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
