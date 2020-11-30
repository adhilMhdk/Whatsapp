package com.whatsappclone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.whatsappclone.modelClass.ContactsModel;

import java.io.IOException;
import java.util.ArrayList;

public class ContactsDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CONTACTS" ;
    private static final String TABLE_NAME = "PEOPLES";
    private static final String PHONE = "PHONE" ;
    private static final String NAME = "NAME";
    private static final String IMAGE = "IMAGE";

    public ContactsDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create="create table "+TABLE_NAME+"(ID INTEGER primary key ,"+PHONE+" TEXT,"+NAME+" TEXT,"+IMAGE+" TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public void setDetails(String phone,String name,String image){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHONE, phone);
        values.put(NAME, name);
        values.put(IMAGE,image);

        db.insert(TABLE_NAME,null,values);
    }



    public ArrayList<ContactsModel> getContacts() {
        ArrayList<ContactsModel> contacts;
        contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor  cursor = db.rawQuery("select * from "+TABLE_NAME,null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String name = cursor.getString(cursor.getColumnIndex(NAME));
                    String phone = cursor.getString(cursor.getColumnIndex(PHONE));
                    String image = cursor.getString(cursor.getColumnIndex(IMAGE));
                    ContactsModel model = new ContactsModel();
                    model.setName(name);
                    model.setPhone(phone);
                    model.setImage(image);
                    contacts.add(model);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            return contacts;
        }

        return contacts;
    }

    public void clearTable(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String clearTable= "DELETE FROM "+TABLE_NAME;
        sqLiteDatabase.execSQL(clearTable);
    }
}
