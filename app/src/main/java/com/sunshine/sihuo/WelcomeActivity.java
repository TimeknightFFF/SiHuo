package com.sunshine.sihuo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sunshine.sihuo.fragments.WelcomeFragment;
import com.sunshine.sihuo.utils.SysApplication;


public class WelcomeActivity extends FragmentActivity {
    private ViewPager welcome_vp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SysApplication.getInstance().addActivity(this);

        welcome_vp= ((ViewPager) findViewById(R.id.welcome_vp));
        welcome_vp.setAdapter(new MyAdapter(getSupportFragmentManager()));
        welcome_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return WelcomeFragment.getFragment(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
