package eu.execom.toolbox1sugarorm;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.orm.SugarApp;

import eu.execom.toolbox1sugarorm.util.DatabaseUtil;

public class Toolbox extends SugarApp {

    public static final String CURRENT_USER = "currentUser";
    public static final String TEST_DATA_GENERATED = "testDataGenerated";

    @Override
    public void onCreate() {
        super.onCreate();
        generateTestData();
    }

    private void generateTestData() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean isTestDataGenerated = sharedPreferences.getBoolean(TEST_DATA_GENERATED, false);
        if (!isTestDataGenerated){
            DatabaseUtil.generateTestData();
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(TEST_DATA_GENERATED, true);
            editor.apply();
        }
    }

}
