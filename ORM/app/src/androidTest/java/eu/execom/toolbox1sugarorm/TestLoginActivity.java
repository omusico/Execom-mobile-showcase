package eu.execom.toolbox1sugarorm;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import junit.framework.Assert;

import eu.execom.toolbox1sugarorm.activity.LoginActivity;
import eu.execom.toolbox1sugarorm.activity.MainActivity;


public class TestLoginActivity extends ActivityInstrumentationTestCase2<LoginActivity> {

    private static final String EMAIL = "nantic@execom.eu";
    private static final String PASSWORD = "test";
    private static final String NAME = "Nikola Antic";

    private Solo solo;

    public TestLoginActivity() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testRegistrationShouldWork() {
        solo.assertCurrentActivity("Wrong activity started.", LoginActivity.class);

        solo.clickOnText(solo.getString(R.string.login_register));
        Assert.assertTrue("Registration dialog did not open.", solo.searchText(solo.getString(R.string.login_registration_title)));

        solo.enterText(0, EMAIL);
        solo.enterText(1, PASSWORD);
        solo.enterText(2, NAME);
        solo.clickOnButton(solo.getString(R.string.login_register_button));
        Assert.assertFalse("Registration was not successful", solo.searchText(solo.getString(R.string.login_registration_title)));

        solo.clickOnEditText(0);
        solo.enterText(0, EMAIL);
        solo.pressSoftKeyboardNextButton();
        solo.enterText(1, PASSWORD);
        solo.pressSoftKeyboardNextButton();
        solo.sleep(1000);
        solo.assertCurrentActivity("Main activity did not open.", MainActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
