package eu.execom.toolbox1sugarorm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import eu.execom.toolbox1sugarorm.R;
import eu.execom.toolbox1sugarorm.Toolbox;
import eu.execom.toolbox1sugarorm.service.UserService;
import eu.execom.toolbox1sugarorm.model.User;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = (EditText) findViewById(R.id.login_email);
        final EditText password = (EditText) findViewById(R.id.login_password);
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    login(email.getText().toString(), password.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        final User currentUser = UserService.getCurrentUser(this);
        if (currentUser != null){
            startMainActivity();
        }
    }

    private void login(String email, String password){
        final User user = UserService.login(email, password);
        if (user == null){
            Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
        } else {
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(Toolbox.CURRENT_USER, user.getId());
            editor.apply();

            startMainActivity();
        }
    }

    private void startMainActivity() {
        final Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);
        finish();
    }

    public void retrievePassword(View view){
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.forgot_password_popup, null);

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.login_retrieve_password_title))
                .setView(dialogView)
                .setPositiveButton(getString(R.string.login_retrieve_password_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final EditText email = (EditText) dialogView.findViewById(R.id.email);
                        final EditText name = (EditText) dialogView.findViewById(R.id.name);
                        final String password = UserService.retrievePassword(email.getText().toString(), name.getText().toString());
                        if (password == null) {
                            Toast.makeText(LoginActivity.this, getString(R.string.login_retrieve_password_error), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, getString(R.string.login_retrieve_password_success) + password, Toast.LENGTH_LONG).show();
                        }
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                }
        ).show();
    }

    public void register(View view){
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.registration_popup, null);

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.login_registration_title))
                .setView(dialogView)
                .setPositiveButton(getString(R.string.login_register_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final EditText email = (EditText) dialogView.findViewById(R.id.email);
                        final EditText password = (EditText) dialogView.findViewById(R.id.password);
                        final EditText name = (EditText) dialogView.findViewById(R.id.name);
                        UserService.register(email.getText().toString(), password.getText().toString(), name.getText().toString());
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                }
        ).show();
    }
}
