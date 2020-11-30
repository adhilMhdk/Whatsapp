package com.whatsappclone.activitys.mainSession;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.whatsappclone.R;
import com.whatsappclone.activitys.mainSession.contactsSession.ContactsActivity;
import com.whatsappclone.database.ContactsDatabase;
import com.whatsappclone.database.User;

public class MainActivity extends AppCompatActivity {

    User user;
    ViewPagerAdapter pagerAdapter;
    ViewPager viewPage;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User(this);
        viewPage = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),4);
        viewPage.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPage);
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.setIcon(R.drawable.camera_icon_foreground);
        tab.setText("");







    }

    Boolean onceClicked = false;
    @Override
    public void onBackPressed() {
        if (!onceClicked){
            onceClicked = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onceClicked = false;
                }
            },1000);
        }else{
            finish();
        }
    }

    public void startContactsActivity(View view) {
        startActivity(new Intent(MainActivity.this, ContactsActivity.class));

    }
}