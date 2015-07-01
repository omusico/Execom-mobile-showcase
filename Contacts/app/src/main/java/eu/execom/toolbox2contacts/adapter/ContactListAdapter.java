package eu.execom.toolbox2contacts.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eu.execom.toolbox2contacts.R;
import eu.execom.toolbox2contacts.activity.DetailsActivity;
import eu.execom.toolbox2contacts.model.Contact;


public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private ArrayList<Contact> contacts;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Context context;

        public TextView displayName;

        public ViewHolder(View view, Context context) {
            super(view);
            this.context = context;
            displayName = (TextView) view;
        }
    }

    public ContactListAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ContactListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Contact contact = contacts.get(position);
        holder.displayName.setText(contact.getDisplayName());
        holder.displayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetails(holder.context, contact);
            }
        });
    }

    private void openDetails(Context context, Contact contact) {
        final Intent openDetails = new Intent(context, DetailsActivity.class);
        openDetails.putExtra(DetailsActivity.CONTACT_ID, contact.getId());
        context.startActivity(openDetails);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}