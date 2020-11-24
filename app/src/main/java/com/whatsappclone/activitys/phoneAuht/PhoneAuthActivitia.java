package com.whatsappclone.activitys.phoneAuht;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.whatsappclone.R;

public class PhoneAuthActivitia extends AppCompatActivity {

    TextView mCountryCode,mPhoneNumber;
    CountryCodePicker ccp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth_activitia);
        init();
    }

    public void getOtp(View view) {

    }
    private void init() {
        mCountryCode = findViewById(R.id.country_code_tv);
        mPhoneNumber = findViewById(R.id.phone_tv);
        ccp = findViewById(R.id.ccp);
        afterInit();
    }

    private void afterInit() {
        mCountryCode.setText(ccp.getSelectedCountryCode());
    }
}