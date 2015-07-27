package eu.execom.oauth2.activity;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import eu.execom.oauth2.R;
import eu.execom.oauth2.adapter.MainFragmentAdapter;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "sflkJICMzwm13caGu3CKbSpeJ";
    private static final String TWITTER_SECRET = "EMq9lap2kc55DqBkaGxi0itNglHJmx8vR360LG5aK6gWh4P8ub";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(),this);
        final PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        viewPager.setAdapter(mainFragmentAdapter);
        pagerSlidingTabStrip.setViewPager(viewPager);
    }

}
