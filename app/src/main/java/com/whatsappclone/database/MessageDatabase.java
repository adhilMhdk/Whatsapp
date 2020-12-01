package com.whatsappclone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.whatsappclone.constants.Types;
import com.whatsappclone.modelClass.ContactsModel;
import com.whatsappclone.modelClass.MessageModel;

import java.util.ArrayList;

public class MessageDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MESSAGES" ;
    private static final String DATE = "DATE";
    private static final String TIME = "TIME";
    private static String TABLE_NAME = "RANDOM";
    private static final String PHONE = "PHONE" ;
    private static final String TYPE = "TYPE";
    private static final String IMAGE = "IMAGE";
    private static final String VIDEO = "VIDEO";
    private static final String AUDIO = "AUDIO";
    private static final String TEXT = "TEXT_MESSAGE";
    private static final String SEND = "SEND";
    private static final String GET = "GET";
    private static final String READ = "READ";

    Context context;

    public MessageDatabase(Context context,String tableName) {
        super(context, DATABASE_NAME, null, 1);
        TABLE_NAME = tableName;
        this.context = context;
        createTable();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    void createTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String create="create table "+TABLE_NAME+"(ID INTEGER primary key ,"+PHONE+" TEXT,"+TYPE+" TEXT,"+IMAGE+" TEXT,"+VIDEO+" TEXT,"+AUDIO+" TEXT,"+TEXT+" TEXT,"+SEND+" TEXT,"+GET+" TEXT,"+READ+" TEXT,"+DATE+" TEXT,"+TIME+" TIME)";
        try {
            db.execSQL(create);
        }catch (Exception e){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }


    public void setSend(String sendStatus,String tableName,String time){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues data=new ContentValues();
        data.put(SEND,sendStatus);

        DB.update(tableName, data, "ID="+getPosition(time)+"", null);

    }



    public void setMessage(String phone,String type,String image,String video,String audio, String textMessage,String send,String get, String read,String date,String time){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHONE, phone);
        values.put(TYPE, type);
        values.put(IMAGE,image);
        values.put(VIDEO, video);
        values.put(AUDIO, audio);
        values.put(TEXT,textMessage);
        values.put(SEND,send);
        values.put(GET,get);
        values.put(READ,read);
        values.put(DATE,date);
        values.put(TIME,time);

        db.insert(TABLE_NAME,null,values);
    }



    public ArrayList<MessageModel> getMessages() {
        ArrayList<MessageModel> messageModels;
        messageModels = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = this.getReadableDatabase();

        }catch (Exception e){
            return messageModels;
        }
        try {
            Cursor  cursor = db.rawQuery("select * from "+TABLE_NAME,null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String type = cursor.getString(cursor.getColumnIndex(TYPE));
                    String phone = cursor.getString(cursor.getColumnIndex(PHONE));
                    String image = cursor.getString(cursor.getColumnIndex(IMAGE));
                    String audio = cursor.getString(cursor.getColumnIndex(AUDIO));
                    String video = cursor.getString(cursor.getColumnIndex(VIDEO));
                    String text = cursor.getString(cursor.getColumnIndex(TEXT));
                    String send = cursor.getString(cursor.getColumnIndex(SEND));
                    String get = cursor.getString(cursor.getColumnIndex(GET));
                    String read = cursor.getString(cursor.getColumnIndex(READ));

                    MessageModel model = new MessageModel();
                    model.setType(type);
                    model.setPhone(phone);
                    model.setImage(image);
                    model.setAudio(audio);
                    model.setVideo(video);
                    model.setText(text);
                    model.setSend(send);
                    model.setRead(read);
                    model.setGet(get);
                    messageModels.add(model);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            return messageModels;
        }

        return messageModels;
    }

    public int getPosition(String msg) {
        int  position = 1;

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor  cursor = db.rawQuery("select * from "+TABLE_NAME,null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String type = cursor.getString(cursor.getColumnIndex(TYPE));
                    String time = cursor.getString(cursor.getColumnIndex(TIME));
                    if (type.equals(new Types().getTEXT())){
                        if (msg.equals(time)){
                            position = cursor.getPosition()+1;
                        }
                    }
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            return position;
        }

        if (position==1){
            Toast.makeText(context, "Sory", Toast.LENGTH_SHORT).show();
        }
        return position;
    }

    public void clearTable(String tableName){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String clearTable= "DELETE FROM "+tableName;
        sqLiteDatabase.execSQL(clearTable);
    }
}
