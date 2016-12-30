package com.opensource.xyz.reader.ui.detailarticle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ArticleDetailAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();
    private Integer totalArticles = 0;


    public ArticleDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, Integer totalArticles) {
        mFragments.add(fragment);
        this.totalArticles = totalArticles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return totalArticles;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}