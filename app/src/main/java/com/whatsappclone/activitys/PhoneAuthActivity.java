package com.whatsappclone.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.whatsappclone.R;
import com.whatsappclone.activitys.phoneAuht.PhoneAuthActivitia;

public class PhoneAuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

    }



    public void startPhoneAuthActivity(View view) {
        startActivity(new Intent(PhoneAuthActivity.this, PhoneAuthActivitia.class));
    }
}