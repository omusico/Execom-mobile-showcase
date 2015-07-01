package eu.execom.toolbox2contacts.service;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.Data;
import android.util.Log;

import java.util.ArrayList;

import eu.execom.toolbox2contacts.Toolbox;
import eu.execom.toolbox2contacts.model.ContactInformation;

public class InfoService {

    private static final String TAG = InfoService.class.getName();
    private static final Context context = Toolbox.instance;

    public static String insert(String contactId, ContactInformation info){
        switch (info.getType()){
            case PHONE:
                return insertPhone(contactId, info);
            case EMAIL:
                return insertEmail(contactId, info);
            case ADDRESS:
                return insertAddress(contactId, info);
            case WEBSITE:
                return insertWebsite(contactId, info);
            default:
                throw new IllegalArgumentException("Unknown info type: " + info.getType());
        }
    }

    public static void update(ContactInformation info){
        switch (info.getType()){
            case PHONE:
                updatePhone(info);
                break;
            case EMAIL:
                updateEmail(info);
                break;
            case ADDRESS:
                updateAddress(info);
                break;
            case WEBSITE:
                updateWebsite(info);
                break;
            default:
                throw new IllegalArgumentException("Unknown info type: " + info.getType());
        }
    }

    public static ArrayList<ContactInformation> readAll(String contactId){
        final ArrayList<ContactInformation> infos = new ArrayList<>();
        infos.addAll(getPhones(contactId));
        infos.addAll(getEmails(contactId));
        infos.addAll(getAddresses(contactId));
        infos.addAll(getWebsites(contactId));
        return infos;
    }

    private static ArrayList<ContactInformation> getPhones(String contactId) {
        final ArrayList<ContactInformation> phones = new ArrayList<>();

        final Uri CONTENT_URI = Phone.CONTENT_URI;
        final String SELECTION = Phone.CONTACT_ID + " = ?";
        final String[] PARAMS = new String[]{ contactId };

        final Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, SELECTION, PARAMS, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                final String id = cursor.getString(cursor.getColumnIndex(Phone._ID));
                final String value = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
                final int type = cursor.getInt(cursor.getColumnIndex(Phone.TYPE));

                final ContactInformation phone = new ContactInformation();
                phone.setType(ContactInformation.Type.PHONE);
                phone.setId(id);
                phone.setValue(value);
                switch (type){
                    case Phone.TYPE_HOME:
                        phone.setCategory(ContactInformation.Category.PRIVATE);
                        break;
                    case Phone.TYPE_WORK:
                        phone.setCategory(ContactInformation.Category.WORK);
                        break;
                    case Phone.TYPE_OTHER:
                        phone.setCategory(ContactInformation.Category.OTHER);
                        break;
                }
                phones.add(phone);
            }
            cursor.close();
        }
        return phones;
    }

    private static ArrayList<ContactInformation> getEmails(String contactId) {
        final ArrayList<ContactInformation> emails = new ArrayList<>();

        final Uri CONTENT_URI = Email.CONTENT_URI;
        final String SELECTION = Email.CONTACT_ID + " = ?";
        final String[] PARAMS = new String[]{ contactId };

        final Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, SELECTION, PARAMS, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                final String id = cursor.getString(cursor.getColumnIndex(Email._ID));
                final String value = cursor.getString(cursor.getColumnIndex(Email.ADDRESS));
                final int type = cursor.getInt(cursor.getColumnIndex(Email.TYPE));

                final ContactInformation email = new ContactInformation();
                email.setType(ContactInformation.Type.EMAIL);
                email.setId(id);
                email.setValue(value);
                switch (type){
                    case Email.TYPE_HOME:
                        email.setCategory(ContactInformation.Category.PRIVATE);
                        break;
                    case Email.TYPE_WORK:
                        email.setCategory(ContactInformation.Category.WORK);
                        break;
                    case Email.TYPE_OTHER:
                        email.setCategory(ContactInformation.Category.OTHER);
                        break;
                }
                emails.add(email);
            }
            cursor.close();
        }

        return emails;
    }

    private static ArrayList<ContactInformation> getAddresses(String contactId){
        final ArrayList<ContactInformation> addresses = new ArrayList<>();

        final Uri CONTENT_URI = StructuredPostal.CONTENT_URI;
        final String SELECTION = StructuredPostal.CONTACT_ID + " = ?";
        final String[] PARAMS = new String[]{ contactId };

        final Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, SELECTION, PARAMS, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                final String id = cursor.getString(cursor.getColumnIndex(StructuredPostal._ID));
                final String value = cursor.getString(cursor.getColumnIndex(StructuredPostal.FORMATTED_ADDRESS));
                final int type = cursor.getInt(cursor.getColumnIndex(StructuredPostal.TYPE));

                final ContactInformation address = new ContactInformation();
                address.setType(ContactInformation.Type.ADDRESS);
                address.setId(id);
                address.setValue(value);
                switch (type){
                    case StructuredPostal.TYPE_HOME:
                        address.setCategory(ContactInformation.Category.PRIVATE);
                        break;
                    case StructuredPostal.TYPE_WORK:
                        address.setCategory(ContactInformation.Category.WORK);
                        break;
                    case StructuredPostal.TYPE_OTHER:
                        address.setCategory(ContactInformation.Category.OTHER);
                        break;
                }
                addresses.add(address);
            }
            cursor.close();
        }

        return addresses;
    }

    private static ArrayList<ContactInformation> getWebsites(String contactId){
        final ArrayList<ContactInformation> websites = new ArrayList<>();

        final Uri CONTENT_URI = ContactsContract.Data.CONTENT_URI;
        final String SELECTION = Website.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        final String[] PARAMS = new String[]{ contactId, Website.CONTENT_ITEM_TYPE};

        final Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, SELECTION, PARAMS, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                final String id = cursor.getString(cursor.getColumnIndex(Website._ID));
                final String value = cursor.getString(cursor.getColumnIndex(Website.URL));
                final int type = cursor.getInt(cursor.getColumnIndex(Website.TYPE));

                final ContactInformation website = new ContactInformation();
                website.setType(ContactInformation.Type.WEBSITE);
                website.setId(id);
                website.setValue(value);
                switch (type){
                    case Website.TYPE_HOME:
                        website.setCategory(ContactInformation.Category.PRIVATE);
                        break;
                    case Website.TYPE_WORK:
                        website.setCategory(ContactInformation.Category.WORK);
                        break;
                    case Website.TYPE_OTHER:
                        website.setCategory(ContactInformation.Category.OTHER);
                        break;
                }
                websites.add(website);
            }
            cursor.close();
        }

        return websites;
    }

    private static String insertPhone(String contactId, ContactInformation info) {
        try {
            final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
            operations.add(ContentProviderOperation
                    .newInsert(Data.CONTENT_URI)
                    .withValue(Data.RAW_CONTACT_ID, contactId)
                    .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                    .withValue(Phone.NUMBER, info.getValue())
                    .withValue(Phone.TYPE, getPhoneType(info))
                    .build());
            final ContentProviderResult[] results = Toolbox.instance.getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
            Log.i(TAG, "Phone inserted");
            return results[0].uri.getLastPathSegment();
        } catch (Exception e) {
            Log.e(TAG, "Error while inserting value: " + info.getValue(), e);
            return null;
        }
    }

    private static void updatePhone(ContactInformation info) {
        try {
            final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
            operations.add(ContentProviderOperation.newUpdate(Data.CONTENT_URI)
                    .withSelection(Data._ID + " = ?", new String[]{ info.getId() })
                    .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                    .withValue(Phone.NUMBER, info.getValue())
                    .withValue(Phone.TYPE, getPhoneType(info))
                    .build());
            Toolbox.instance.getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
            Log.i(TAG, "Phone updated.");
        } catch (Exception e) {
            Log.i(TAG, "Error while updating value: " + info.getValue(), e);
        }
    }

    private static int getPhoneType(ContactInformation info){
        switch (info.getCategory()){
            case PRIVATE:
                return Phone.TYPE_HOME;
            case WORK:
                return Phone.TYPE_WORK;
            case OTHER:
                return Phone.TYPE_OTHER;
            default:
                throw new IllegalArgumentException("Unrecognized phone type.");
        }
    }

    private static String insertEmail(String contactId, ContactInformation info) {
        try {
            final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
            operations.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
                    .withValue(Data.RAW_CONTACT_ID, contactId)
                    .withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                    .withValue(Data.DATA1, info.getValue())
                    .withValue(Email.TYPE, getEmailType(info))
                    .build());
            final ContentProviderResult[] results = Toolbox.instance.getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
            Log.i(TAG, "Email inserted");
            return results[0].uri.getLastPathSegment();
        } catch (Exception e) {
            Log.e(TAG, "Error while inserting value: " + info.getValue(), e);
            return null;
        }
    }

    private static void updateEmail(ContactInformation info) {
        try {
            final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
            operations.add(ContentProviderOperation.newUpdate(Data.CONTENT_URI)
                    .withSelection(Data._ID + " = ?", new String[]{ info.getId() })
                    .withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                    .withValue(Data.DATA1, info.getValue())
                    .withValue(Email.TYPE, getEmailType(info))
                    .build());
            Toolbox.instance.getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
            Log.i(TAG, "Email updated.");
        } catch (Exception e) {
            Log.e(TAG, "Error while updating value: " + info.getValue(), e);
        }
    }

    private static int getEmailType(ContactInformation info){
        switch (info.getCategory()){
            case PRIVATE:
                return Email.TYPE_HOME;
            case WORK:
                return Email.TYPE_WORK;
            case OTHER:
                return Email.TYPE_OTHER;
            default:
                throw new IllegalArgumentException("Info category not recognized.");
        }
    }

    private static String insertAddress(String contactId, ContactInformation info) {
        try {
            final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
            operations.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
                    .withValue(Data.RAW_CONTACT_ID, contactId)
                    .withValue(Data.MIMETYPE, StructuredPostal.CONTENT_ITEM_TYPE)
                    .withValue(StructuredPostal.FORMATTED_ADDRESS, info.getValue())
                    .withValue(StructuredPostal.TYPE, getAddressType(info))
                    .build());
            final ContentProviderResult[] results = Toolbox.instance.getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
            Log.i(TAG, "Address inserted");
            return results[0].uri.getLastPathSegment();
        } catch (Exception e) {
            Log.e(TAG, "Error while inserting value: " + info.getValue(), e);
            return null;
        }
    }

    private static void updateAddress(ContactInformation info) {
        try {
            final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
            operations.add(ContentProviderOperation.newUpdate(Data.CONTENT_URI)
                    .withSelection(Data._ID + " = ?", new String[]{ info.getId() })
                    .withValue(Data.MIMETYPE, StructuredPostal.CONTENT_ITEM_TYPE)
                    .withValue(StructuredPostal.FORMATTED_ADDRESS, info.getValue())
                    .withValue(StructuredPostal.TYPE, getAddressType(info))
                    .build());
            Toolbox.instance.getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
            Log.i(TAG, "Address updated.");
        } catch (Exception e) {
            Log.i(TAG, "Error while updating value: " + info.getValue(), e);
        }
    }

    private static int getAddressType(ContactInformation info){
        switch (info.getCategory()){
            case PRIVATE:
                return StructuredPostal.TYPE_HOME;
            case WORK:
                return StructuredPostal.TYPE_WORK;
            case OTHER:
                return StructuredPostal.TYPE_OTHER;
            default:
                throw new IllegalArgumentException("Unknown info category: " + info.getCategory());
        }
    }

    private static String insertWebsite(String contactId, ContactInformation info) {
        try {
            final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
            operations.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
                    .withValue(Data.RAW_CONTACT_ID, contactId)
                    .withValue(Data.MIMETYPE, Website.CONTENT_ITEM_TYPE)
                    .withValue(Data.DATA1, info.getValue())
                    .withValue(Website.TYPE, getWebsiteType(info))
                    .build());
            final ContentProviderResult[] results = Toolbox.instance.getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
            Log.i(TAG, "Website inserted");
            return results[0].uri.getLastPathSegment();
        } catch (Exception e) {
            Log.e(TAG, "Error while inserting value: " + info.getValue(), e);
            return null;
        }
    }

    private static void updateWebsite(ContactInformation info) {
        try {
            final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
            operations.add(ContentProviderOperation.newUpdate(Data.CONTENT_URI)
                    .withSelection(Data._ID + " = ?", new String[]{ info.getId() })
                    .withValue(Data.MIMETYPE, Website.CONTENT_ITEM_TYPE)
                    .withValue(Data.DATA1, info.getValue())
                    .withValue(Website.TYPE, getWebsiteType(info))
                    .build());
            Toolbox.instance.getContentResolver().applyBatch(ContactsContract.AUTHORITY, operations);
            Log.i(TAG, "Website updated.");
        } catch (Exception e) {
            Log.i(TAG, "Error while updating value: " + info.getValue(), e);
        }
    }

    private static int getWebsiteType(ContactInformation info){
        switch (info.getCategory()){
            case PRIVATE:
                return Website.TYPE_HOME;
            case WORK:
                return Website.TYPE_WORK;
            case OTHER:
                return Website.TYPE_OTHER;
            default:
                throw new IllegalArgumentException("Unknown info category: " + info.getCategory());
        }
    }

}
