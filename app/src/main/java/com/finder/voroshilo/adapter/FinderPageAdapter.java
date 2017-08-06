package com.finder.voroshilo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.finder.voroshilo.fragment.enter.EnterFragment;
import com.finder.voroshilo.fragment.enter.SmartFragmentStatePagerAdapter;

public class FinderPageAdapter extends SmartFragmentStatePagerAdapter {
    private final static int NUM_ITEMS = 3;

    public FinderPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return EnterFragment.newInstance(0);
            case 1:
                return EnterFragment.newInstance(1);
            case 2:
                return EnterFragment.newInstance(2);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
