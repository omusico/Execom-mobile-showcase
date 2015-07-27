package eu.execom.oauth2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import eu.execom.oauth2.R;
import io.fabric.sdk.android.Fabric;

public class TwitterLogin extends Fragment {

    private static final String TAG = TwitterLogin.class.getName();

    private static final String CONSUMER_KEY = "ud2vSVxwRb2V3RxkNqo1md63K";
    private static final String CONSUMER_KEY_SECRET = "0nPRAUy2ZEyVLqsspxx6woi5IBUT6r7goKr9VaGqwJpS1gF4VL";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TwitterAuthConfig authConfig =  new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_KEY_SECRET);
        Fabric.with(getActivity(), new TwitterCore(authConfig));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_twitter_login, container, false);

        final TwitterLoginButton twitterLoginButton = (TwitterLoginButton) view.findViewById(R.id.twitter_login_button);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "Login successful");
            }

            @Override
            public void failure(TwitterException e) {
                Log.d(TAG, "Login failure");
            }
        });

        return view;
    }

}
