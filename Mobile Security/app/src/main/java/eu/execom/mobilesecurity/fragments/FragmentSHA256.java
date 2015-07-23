package eu.execom.mobilesecurity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.security.MessageDigest;
import java.util.Arrays;

import eu.execom.mobilesecurity.R;

public class FragmentSHA256 extends Fragment {
    private static final String TAG = FragmentSHA256.class.getName();
    private static final String ALGORITHM = "SHA-256";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sha2, container, false);

        final TextView hashedValue = (TextView) view.findViewById(R.id.hashed_value);
        final TextView rawValue = (TextView) view.findViewById(R.id.raw_value);

        final Button buttonHash = (Button) view.findViewById(R.id.hash_function);
        buttonHash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
                    messageDigest.update(rawValue.getText().toString().getBytes());
                    hashedValue.setText(Arrays.toString(messageDigest.digest()));
                } catch (Exception e) {
                    Log.e(TAG, "No such algorithm.", e);
                }
            }
        });

        return view;
    }
}
