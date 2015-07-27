package eu.execom.oauth2.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

import eu.execom.oauth2.R;

public class GoogleLogin extends Fragment {

    private static final String TAG = GoogleLogin.class.getName();
    private static final int RC_SIGN_IN = 0;

    private GoogleApiClient googleApiClient;
    private boolean isResolving = false;
    private boolean shouldResolve = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        shouldResolve = false;
                        Log.d(TAG, "Login successful");
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        if (!isResolving && shouldResolve) {
                            if (connectionResult.hasResolution()) {
                                try {
                                    connectionResult.startResolutionForResult(getActivity(), RC_SIGN_IN);
                                    isResolving = true;
                                } catch (IntentSender.SendIntentException e) {
                                    Log.e(TAG, "Could not resolve ConnectionResult.", e);
                                    isResolving = false;
                                    googleApiClient.connect();
                                }
                            } else {
                                Log.d(TAG, "Login failed");
                            }
                        } else {
                            Log.d(TAG, "Logged out");
                        }
                    }
                })
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_google_login, container, false);

        final SignInButton googleSignIn = (SignInButton) view.findViewById(R.id.sign_in_button);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shouldResolve = true;
                googleApiClient.connect();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != Activity.RESULT_OK) {
                shouldResolve = false;
            }

            isResolving = false;
            googleApiClient.connect();
        }
    }

}
