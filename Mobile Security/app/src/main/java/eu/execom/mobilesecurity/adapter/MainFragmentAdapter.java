package eu.execom.mobilesecurity.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import eu.execom.mobilesecurity.R;
import eu.execom.mobilesecurity.fragments.FragmentAES128;
import eu.execom.mobilesecurity.fragments.FragmentSHA256;

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_OF_FRAGMENTS = 2;

    private Context context;

    public MainFragmentAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentAES128();
            case 1:
                return new FragmentSHA256();
            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public int getCount() { // Return the number of pages
        return NUMBER_OF_FRAGMENTS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.aes_title);
            case 1:
                return context.getString(R.string.sha_title);
            default:
                throw new IllegalArgumentException();
        }
    }

}
