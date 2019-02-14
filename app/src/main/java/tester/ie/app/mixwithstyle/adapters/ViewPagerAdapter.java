package tester.ie.app.mixwithstyle.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tester.ie.app.mixwithstyle.R;
import tester.ie.app.mixwithstyle.fragments.IngredientsFragment;
import tester.ie.app.mixwithstyle.fragments.MethodFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new IngredientsFragment();
        } else if (position == 1) {
            return new MethodFragment();
        }else{
            return new IngredientsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "Hello";
            case 1:
                return "World";
            default:
                return null;
        }
    }
}
