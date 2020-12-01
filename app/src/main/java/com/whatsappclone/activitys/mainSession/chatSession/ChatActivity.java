package com.whatsappclone.activitys.mainSession.chatSession;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whatsappclone.R;
import com.whatsappclone.constants.Status;
import com.whatsappclone.constants.Types;
import com.whatsappclone.database.ContactsDatabase;
import com.whatsappclone.database.MessageDatabase;
import com.whatsappclone.modelClass.ContactsModel;
import com.whatsappclone.modelClass.MessageModel;
import com.whatsappclone.serverHelpers.Server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    TextView profileName;
    EditText messageBox;
    String phone,name,imageUrl;
    CircleImageView profileImage;
    MessageDatabase messageDatabase;
    ArrayList<MessageModel> messageModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        init();


    }

    private void init() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        name = intent.getStringExtra("name");
        imageUrl = intent.getStringExtra("image");
        messageBox = findViewById(R.id.message_box);

        profileName = findViewById(R.id.profile_name);
        profileImage = findViewById(R.id.profile_image);

        messageDatabase = new MessageDatabase(this,name);
        messageModels = messageDatabase.getMessages();

        afterInit();
    }

    private void afterInit() {
        profileName.setText(name);
        if (imageUrl!=null){
            Picasso.get().load(imageUrl).into(profileImage);
        }
    }


    public void SendMessage(View view) {

        String date = new SimpleDateFormat("dd/MM/yy").format(new Date());
        String time = new SimpleDateFormat("hh:mm:ss").format(new Date());
        messageDatabase.setMessage(phone
                ,new Types().getTEXT()
                ,null
                ,null
                ,null
                ,messageBox.getText().toString()
                ,null
                ,null
                ,null
                ,date
                ,time);
        //Reload chat from database
        reloadChat();

       new send(time).start();
    }



    private void reloadChat() {
        messageModels = messageDatabase.getMessages();
    }
    private class send extends Thread{

        String time;

        public send(String time) {
            this.time = time;
        }

        @Override
        public void run() {
            startSending();
        }

        private void startSending() {
            HashMap<String,String> map = new HashMap<>();
            map.put("phone",phone);
            String message = messageBox.getText().toString();
            map.put("message", message);
            Call<Void> call = new Server().getRetrofitInterface().sendTextMessage(map);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code()==200){
                        messageDatabase.setSend(new Status().getTRUE(),name,time);
                    }else {
                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        startSending();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    }
}