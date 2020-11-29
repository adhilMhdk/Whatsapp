package com.whatsappclone.activitys.mainSession.contactsSession;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.whatsappclone.database.ContactsDatabase;
import com.whatsappclone.modelClass.ContactsModel;

import java.util.ArrayList;
import java.util.function.Consumer;

public class loadContacts extends Thread {

    private static final String TAG = "LOAD_CONTACTS";
    Activity activity;
    ContactsDatabase contactsDatabase;
    Load load;
    ArrayList<ContactsModel> contactsModels;

    public loadContacts(Activity activity, Load load) {
        this.activity = activity;
        this.load = load;
    }

    @Override
    public void run() {
        contactsDatabase = new ContactsDatabase(activity);
        contactsModels = contactsDatabase.getContacts();
        getContactList();
    }


    private void getContactList() {
        ContentResolver cr = activity.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER)).replace(" ","").replace("+","");
                        Boolean contains= false;

                        for (int i=0 ;i<=contactsModels.size()-1;i++){
                            String p = contactsModels.get(i).getPhone().replace(" ", "").replace("+","");
                            if (p.equals(phoneNo)){
                                contains = true;
                                break;
                            }
                        }
                        if (!contains) {
                            contactsDatabase.setDetails(phoneNo, name);
                            ContactsModel model = new ContactsModel();
                            model.setName(name);
                            model.setPhone(phoneNo);
                            contactsModels.add(model);
                        }

                    }
                    pCur.close();
                }
            }
            Log.e(TAG, "getContactList: ENDED" );
            load.onLoaded();
        }
        if(cur!=null){
            cur.close();
        }
    }
    public interface Load{
        public void onLoaded();
    }
}
