package com.whatsappclone.activitys.phoneAuht;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.whatsappclone.R;
import com.whatsappclone.database.User;
import com.whatsappclone.serverHelpers.Server;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneAuthActivityb extends AppCompatActivity {
    TextView toolbarTitle,subTitle;
    EditText mOtp;
    String code;
    ProgressDialog pd;
    String phone;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth_activityb);

        toolbarTitle = findViewById(R.id.toolbar_title);
        subTitle = findViewById(R.id.sub_title);
        code = getIntent().getStringExtra("OTP");

        phone = getIntent().getStringExtra("Phone");
        mOtp = findViewById(R.id.oyp_tv);
        toolbarTitle.setText("Verify "+phone);
        subTitle.setText("We have send an SMS with a code to "+getIntent().getStringExtra("Phone"));

    }


    public void verify(View view) {
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Verifying...");
        pd.show();

        if (code.equals(mOtp.getText().toString())){
            HashMap<String,String> map = new HashMap<>();
            map.put("phone",phone);
            Call<Void> call = new Server().getRetrofitInterface().newUser(map);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(PhoneAuthActivityb.this, response.code()+"", Toast.LENGTH_SHORT).show();
                    User user = new User(getActivity());
                    user.setDetails(phone,null);
                    startActivity(new Intent(getActivity(),PhoneAuthActivityc.class));

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }

    }
    Activity getActivity(){
        return PhoneAuthActivityb.this;
    }
}