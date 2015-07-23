package eu.execom.mobilesecurity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.commons.lang3.ArrayUtils;

import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import eu.execom.mobilesecurity.R;

public class FragmentAES128 extends Fragment {

    private static final String TAG = FragmentAES128.class.getName();

    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String ENCRYPTION_MODE = "CBC";
    private static final String ENCRYPTION_PADDING = "PKCS5Padding";
    private static final String ENCRYPTION_PARAMS = ENCRYPTION_ALGORITHM + "/" + ENCRYPTION_MODE + "/" + ENCRYPTION_PADDING;
    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String STRING_CHARSET = "UTF-8";
    private static final int SECRET_KEY_LENGTH = 128;
    private static final int ITERATION_COUNT = 2048;
    private static final int SALT_LENGTH = 16;
    private static final int IV_LENGTH = 16;

    private static char[] password = new char[]{'S', 'e', 'c', 'r', 'e', 't'};

    private byte[] encryptedData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_aes128, container, false);

        final TextView input = (TextView) view.findViewById(R.id.input_text);
        final TextView encryptedValue = (TextView) view.findViewById(R.id.encrypt_text);
        final TextView decryptedValue = (TextView) view.findViewById(R.id.decrypt_text);

        final Button encrypt = (Button) view.findViewById(R.id.encrypt_button);
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    encryptedData = encrypt(input.getText().toString());
                    encryptedValue.setText(Arrays.toString(encryptedData));
                } catch (Exception e){
                    Log.e(TAG, "Error while encrypting.", e);
                }
            }
        });

        final Button buttonDecrypt = (Button) view.findViewById(R.id.decrypt_button);
        buttonDecrypt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            decryptedValue.setText(decrypt(encryptedData));
                        } catch (Exception e) {
                            Log.e(TAG, "Error while decrypting.", e);
                        }
                    }
                }
        );

        return view;
    }

    private static byte[] encrypt(String rawData) throws Exception {
        final byte[] salt = generateSalt();

        final Cipher cipher = Cipher.getInstance(ENCRYPTION_PARAMS);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(salt));

        final AlgorithmParameters params = cipher.getParameters();
        final byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();

        final byte[] encryptedValue = cipher.doFinal(rawData.getBytes(STRING_CHARSET));

        byte[] data = ArrayUtils.addAll(salt, encryptedValue);
        data = ArrayUtils.addAll(ivBytes, data);
        return data;
    }

    private static String decrypt(byte[] encryptedData) throws Exception {
        final byte[] ivBytes = ArrayUtils.subarray(encryptedData, 0, IV_LENGTH);
        final byte[] salt = ArrayUtils.subarray(encryptedData, IV_LENGTH, IV_LENGTH + SALT_LENGTH);
        final byte[] encryptedValue = ArrayUtils.subarray(encryptedData, IV_LENGTH + SALT_LENGTH, encryptedData.length);

        final Cipher cipher = Cipher.getInstance(ENCRYPTION_PARAMS);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(salt), new IvParameterSpec(ivBytes));

        return new String(cipher.doFinal(encryptedValue), STRING_CHARSET);
    }

    private static SecretKeySpec getSecretKeySpec(byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final SecretKeyFactory factory = SecretKeyFactory.getInstance(HASH_ALGORITHM);
        final KeySpec keySpec = new PBEKeySpec(password, salt, ITERATION_COUNT, SECRET_KEY_LENGTH);
        final SecretKey secretKey = factory.generateSecret(keySpec);
        return new SecretKeySpec(secretKey.getEncoded(), ENCRYPTION_ALGORITHM);
    }

    private static byte[] generateSalt(){
        final SecureRandom secureRandom = new SecureRandom();
        final byte[] bytes = new byte[SALT_LENGTH];
        secureRandom.nextBytes(bytes);
        return bytes;
    }

}