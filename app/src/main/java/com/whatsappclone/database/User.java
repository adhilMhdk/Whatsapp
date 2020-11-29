package com.whatsappclone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class User extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "USER" ;
    private static final String TABLE_NAME = "ME";
    private static final String PHONE = "PHONE" ;
    private static final String NAME = "NAME";

    public User(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create="create table "+TABLE_NAME+"(ID INTEGER primary key ,"+PHONE+" TEXT,"+NAME+" TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public void setDetails(String phone,String name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHONE, phone);
        values.put(NAME, name);

        db.insert(TABLE_NAME,null,values);
    }

    public String getPhone(){
        String RETURN_VALUE ;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        cursor.moveToNext();
        try {
            RETURN_VALUE = cursor.getString(cursor.getColumnIndex(PHONE));
            return RETURN_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }



    public String getName() {
        String RETURN_VALUE;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        cursor.moveToNext();
        try {
            RETURN_VALUE = cursor.getString(cursor.getColumnIndex(NAME));
            return RETURN_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void clearTable(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String clearTable= "DELETE FROM "+TABLE_NAME;
        sqLiteDatabase.execSQL(clearTable);
    }
}
