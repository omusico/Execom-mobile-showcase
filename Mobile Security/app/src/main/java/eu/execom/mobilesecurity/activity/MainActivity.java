package eu.execom.mobilesecurity.activity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;

import eu.execom.mobilesecurity.adapter.MainFragmentAdapter;
import eu.execom.mobilesecurity.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(),this);
        final PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        viewPager.setAdapter(mainFragmentAdapter);
        pagerSlidingTabStrip.setViewPager(viewPager);
    }

}
