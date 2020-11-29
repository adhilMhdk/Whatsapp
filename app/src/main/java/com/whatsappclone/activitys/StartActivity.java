package com.whatsappclone.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.whatsappclone.R;
import com.whatsappclone.activitys.mainSession.MainActivity;
import com.whatsappclone.activitys.phoneAuht.PhoneAuthActivityc;
import com.whatsappclone.database.User;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_main);

        User user = new User(this);
        if (user.getPhone()==null){
            startActivity(new Intent(this,PhoneAuthActivity.class));
        }else if (user.getName()==null){
            startActivity(new Intent(this, PhoneAuthActivityc.class));
        }else{
            startActivity(new Intent(this, MainActivity.class));

        }
        finish();

    }
}