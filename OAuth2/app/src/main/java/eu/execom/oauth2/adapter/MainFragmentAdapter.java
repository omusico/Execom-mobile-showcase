package eu.execom.oauth2.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import eu.execom.oauth2.R;
import eu.execom.oauth2.fragment.FacebookLogin;
import eu.execom.oauth2.fragment.GoogleLogin;
import eu.execom.oauth2.fragment.TwitterLogin;

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_OF_FRAGMENTS = 3;

    private Context context;

    public MainFragmentAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new GoogleLogin();
            case 1:
                return new FacebookLogin();
            case 2:
                return new TwitterLogin();
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
                return context.getString(R.string.google_login_title);
            case 1:
                return context.getString(R.string.facebook_login_title);
            case 2:
                return context.getString(R.string.twitter_login_title);
            default:
                throw new IllegalArgumentException();
        }
    }

}
