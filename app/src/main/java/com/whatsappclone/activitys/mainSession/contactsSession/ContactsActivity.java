package com.whatsappclone.activitys.mainSession.contactsSession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.whatsappclone.R;
import com.whatsappclone.database.ContactsDatabase;
import com.whatsappclone.modelClass.ContactsModel;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<ContactsModel> contactsModels;
    RecyclerView recyclerView;
    ContactsRecyclerAdapter adapter;
    ContactsDatabase contactsDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        init();
    }

    public void setRecyclerView() {
        recyclerView = findViewById(R.id.contacts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        contactsDatabase = new ContactsDatabase(this);
        setAdapter();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            new loadContacts(this, new loadContacts.Load() {
                @Override
                public void onLoaded() {
                    setAdapter();
                }
            }).start();
        }
        setRecyclerView();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode==1) {
            new loadContacts(this, new loadContacts.Load() {
                @Override
                public void onLoaded() {
                    setAdapter();
                }
            }).start();
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contacts_menu, menu);
        return true;
    }



    public void setAdapter() {
        adapter = new ContactsRecyclerAdapter(contactsDatabase.getContacts());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}