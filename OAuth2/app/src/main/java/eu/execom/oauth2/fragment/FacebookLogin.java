package eu.execom.oauth2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import eu.execom.oauth2.R;

public class FacebookLogin extends Fragment {

    private static final String TAG = FacebookLogin.class.getName();
    private static final String SCOPE = "user_friends";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_facebook_login, container, false);

        final LoginButton loginButton = (LoginButton) view.findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions(SCOPE);
        loginButton.setFragment(this);

        final CallbackManager callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "Login successful");
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Login canceled");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG, "Login error");
            }
        });

        return view;
    }

}
