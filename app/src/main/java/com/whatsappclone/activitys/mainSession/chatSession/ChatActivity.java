package com.whatsappclone.activitys.mainSession.chatSession;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whatsappclone.R;
import com.whatsappclone.database.ContactsDatabase;
import com.whatsappclone.modelClass.ContactsModel;
import com.whatsappclone.serverHelpers.Server;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    TextView profileName;
    String phone,name,imageUrl;
    CircleImageView profileImage;
    ContactsDatabase contactsDatabase;

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

        profileName = findViewById(R.id.profile_name);
        profileImage = findViewById(R.id.profile_image);

        afterInit();
    }

    private void afterInit() {
        profileName.setText(name);
        if (imageUrl!=null){
            Picasso.get().load(imageUrl).into(profileImage);
        }
    }


}