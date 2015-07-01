package eu.execom.toolbox2contacts.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import eu.execom.toolbox2contacts.R;
import eu.execom.toolbox2contacts.adapter.ContactListAdapter;
import eu.execom.toolbox2contacts.service.ContactService;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView contactList = (RecyclerView) findViewById(R.id.contact_list);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contactList.setLayoutManager(layoutManager);

        final ContactListAdapter contactListAdapter = new ContactListAdapter(ContactService.readAll());
        contactList.swapAdapter(contactListAdapter, true);
    }

}
