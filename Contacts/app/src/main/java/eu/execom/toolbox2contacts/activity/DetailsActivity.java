package eu.execom.toolbox2contacts.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import eu.execom.toolbox2contacts.R;
import eu.execom.toolbox2contacts.adapter.InfoListAdapter;
import eu.execom.toolbox2contacts.model.Contact;
import eu.execom.toolbox2contacts.service.ContactService;
import eu.execom.toolbox2contacts.service.InfoService;

public class DetailsActivity extends AppCompatActivity {

    public static final String CONTACT_ID = "contactId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final String contactId = getIntent().getExtras().getString(CONTACT_ID);
        final Contact contact = ContactService.read(contactId);

        final EditText displayName = (EditText) findViewById(R.id.display_name);
        if (contact != null) {
            displayName.setText(contact.getDisplayName());
            displayName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        contact.setDisplayName(displayName.getText().toString());
                        ContactService.update(contact);
                    }
                    return false;
                }
            });

            final RecyclerView contactList = (RecyclerView) findViewById(R.id.info_list);

            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            contactList.setLayoutManager(layoutManager);

            final InfoListAdapter infoListAdapter = new InfoListAdapter(InfoService.readAll(contactId));
            contactList.swapAdapter(infoListAdapter, true);
        }
    }

}
