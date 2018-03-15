package com.example.administrator.fivecrowdsourcing_merchant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2018/3/9.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mList;
    private final String[] modules;
    public MyFragmentAdapter(FragmentManager fm, List<Fragment> mList, String[] modules) {
        super(fm);
        this.mList = mList;
        this.modules = modules;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return modules[position];
    }

}
