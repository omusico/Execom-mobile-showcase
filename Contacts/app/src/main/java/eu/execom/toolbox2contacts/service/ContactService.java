package eu.execom.toolbox2contacts.service;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;

import java.util.ArrayList;

import eu.execom.toolbox2contacts.Toolbox;
import eu.execom.toolbox2contacts.model.Contact;

public class ContactService {

    private static final String TAG = ContactService.class.getName();

    public static String insert(Contact contact){
        final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(ContentProviderOperation
                .newInsert(RawContacts.CONTENT_URI)
                .withValue(RawContacts.ACCOUNT_TYPE, null)
                .withValue(RawContacts.ACCOUNT_NAME, null)
                .build());
        operations.add(ContentProviderOperation
                .newInsert(Data.CONTENT_URI)
                .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                .withValue(StructuredName.DISPLAY_NAME, contact.getDisplayName())
                .build());
        try {
            final ContentProviderResult[] results = Toolbox.instance.getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
            Log.i(TAG, "Inserted new user: " + contact.getDisplayName());
            return results[0].uri.getLastPathSegment();
        } catch (Exception e) {
            Log.e(TAG, "Error while inserting new contact.", e);
            return null;
        }
    }

    public static void update(Contact contact){
        final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(ContentProviderOperation
                .newUpdate(Data.CONTENT_URI)
                .withSelection(Data.CONTACT_ID + " = ? AND " + Data.MIMETYPE + " = ?", new String[]{contact.getId(), StructuredName.CONTENT_ITEM_TYPE})
                .withValue(StructuredName.DISPLAY_NAME, contact.getDisplayName())
                .build());
        try {
            Toolbox.instance.getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
            Log.i(TAG, "Contact updated");
        } catch (Exception e) {
            Log.e(TAG, "Error while inserting new contact.", e);
        }
    }

    public static ArrayList<Contact> readAll(){
        final ArrayList<Contact> contacts = new ArrayList<>();

        final Uri URI = ContactsContract.Contacts.CONTENT_URI;
        final Cursor cursor = Toolbox.instance.getContentResolver().query(URI, null, null, null, null);
        if (cursor != null){
            Log.i("Test", "Found: " + cursor.getCount() + " contacts.");
            while (cursor.moveToNext()) {
                final String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                final String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                final Contact contact = new Contact();
                contact.setId(id);
                contact.setDisplayName(displayName);

                contacts.add(contact);
            }
            cursor.close();
        }
        return contacts;
    }

    public static Contact read(String id){
        final Uri URI = ContactsContract.Contacts.CONTENT_URI;
        final String CONTACT_SELECTION = ContactsContract.Contacts._ID + " = ?";
        final String[] PROJECTION = new String[] { ContactsContract.Contacts.DISPLAY_NAME };
        final String[] PARAMS = new String[] { id };

        final Cursor cursor = Toolbox.instance.getContentResolver().query(URI, PROJECTION, CONTACT_SELECTION, PARAMS, null);
        if (cursor != null){
            cursor.moveToFirst();
            final String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            final Contact contact = new Contact();
            contact.setId(id);
            contact.setDisplayName(displayName);

            cursor.close();
            return contact;
        } else {
            return null;
        }
    }

}
