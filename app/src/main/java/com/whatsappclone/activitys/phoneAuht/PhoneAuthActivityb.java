package com.whatsappclone.activitys.phoneAuht;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.whatsappclone.R;

public class PhoneAuthActivityb extends AppCompatActivity {
    TextView toolbarTitle,subTitle;
    EditText mOtp;
    String code;
    ProgressDialog pd

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth_activityb);

        toolbarTitle = findViewById(R.id.toolbar_title);
        subTitle = findViewById(R.id.sub_title);
        code = getIntent().getStringExtra("OTP");

        mOtp = findViewById(R.id.oyp_tv);
        toolbarTitle.setText("Verify "+getIntent().getStringExtra("Phone"));
        subTitle.setText("We have send an SMS with a code to "+getIntent().getStringExtra("Phone"));

    }


    public void verify(View view) {
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Verifying...");
        pd.show();

    }
}