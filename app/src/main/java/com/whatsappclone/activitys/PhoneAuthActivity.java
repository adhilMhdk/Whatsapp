package com.whatsappclone.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.whatsappclone.R;
import com.whatsappclone.activitys.phoneAuht.PhoneAuthActivitia;

public class PhoneAuthActivity extends AppCompatActivity {
    CountryCodePicker ccp;
    TextView mCountryCode,mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        ccp= findViewById(R.id.ccp);
        mCountryCode = findViewById(R.id.country_code_tv);
        mPhoneNumber = findViewById(R.id.phone_tv);
        mCountryCode.setText(ccp.getSelectedCountryCodeAsInt()+"");
    }

    public void startPhoneAuthActivity(View view) {
        startActivity(new Intent(PhoneAuthActivity.this, PhoneAuthActivitia.class));
    }
}