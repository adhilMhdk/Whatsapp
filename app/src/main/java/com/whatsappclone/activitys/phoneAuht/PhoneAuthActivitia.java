package com.whatsappclone.activitys.phoneAuht;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.whatsappclone.R;
import com.whatsappclone.modelClass.Model;
import com.whatsappclone.serverHelpers.Server;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneAuthActivitia extends AppCompatActivity {

    TextView mCountryCode,mPhoneNumber;
    CountryCodePicker ccp;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth_activitia);
        init();
    }

    public void getOtp(View view) {
        pd.show();
        HashMap<String,String> map = new HashMap<>();
        map.put("phone",ccp.getSelectedCountryCodeWithPlus()+mPhoneNumber.getText().toString());
        Call<Model> call = new Server().getRetrofitInterface().generateOTP(map);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.code()==200){
                    startActivity(new Intent(PhoneAuthActivitia.this,PhoneAuthActivityb.class)
                            .putExtra("OTP",response.body().getOtp()).putExtra("Phone",ccp.getSelectedCountryCodeWithPlus()+mPhoneNumber.getText().toString()));
                }else{
                    pd.dismiss();
                    Toast.makeText(PhoneAuthActivitia.this, "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(PhoneAuthActivitia.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void init() {
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Connecting...");
        mCountryCode = findViewById(R.id.country_code_tv);
        mPhoneNumber = findViewById(R.id.phone_tv);
        ccp = findViewById(R.id.ccp);
        afterInit();
        setCcp();
    }

    private void setCcp() {
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                mCountryCode.setText(ccp.getSelectedCountryCode());
            }
        });
    }

    private void afterInit() {
        mCountryCode.setText(ccp.getSelectedCountryCode());
    }
}