package eu.execom.toolbox1sugarorm.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import eu.execom.toolbox1sugarorm.R;
import eu.execom.toolbox1sugarorm.fragment.MessagesForMeFragment;
import eu.execom.toolbox1sugarorm.fragment.MyMessagesFragment;
import eu.execom.toolbox1sugarorm.fragment.UsersFragment;

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private Context context;

    public MainFragmentAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MessagesForMeFragment();
            case 1:
                return new MyMessagesFragment();
            case 2:
                return new UsersFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.messages_for_me_fragment_title);
            case 1:
                return context.getString(R.string.my_messages_fragment_title);
            case 2:
                return context.getString(R.string.users_fragment_title);
        }
        return null;
    }

}