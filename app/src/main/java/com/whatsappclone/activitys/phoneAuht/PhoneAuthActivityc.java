package com.whatsappclone.activitys.phoneAuht;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.whatsappclone.R;
import com.whatsappclone.activitys.mainSession.MainActivity;
import com.whatsappclone.database.User;
import com.whatsappclone.serverHelpers.Server;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneAuthActivityc extends AppCompatActivity {

    private static final int PICK_IMAGE = 11;
    CircleImageView profileImage;
    TextView userName;

    Uri picUri;
    User user;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth_activityc);

        profileImage = findViewById(R.id.profile_image);
        userName = findViewById(R.id.profile_name);

        user = new User(this);
        setImage();
    }

    private void setImage() {
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            if (data!=null){
                picUri = data.getData();
                mBitmap = loadBitmap(picUri);
                profileImage.setImageBitmap(mBitmap);
                multipartImageUpload("a");
            }
        }
    }


    Bitmap loadBitmap(Uri selectedFileUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void multipartImageUpload(String type) {
        try {
            File filesDir = getApplicationContext().getFilesDir();
            File file = new File(filesDir, user.getPhone() +type + ".png");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();


            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();


            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");

            Call<ResponseBody> req = new Server().getRetrofitInterface().setImage(body, name);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.code() == 200) {
                        if (!type.equals("b")){
                            Bitmap bitmapImage = mBitmap;
                            int nh = (int) ( bitmapImage.getHeight() * (64.0 / bitmapImage.getWidth()) );
                            mBitmap = Bitmap.createScaledBitmap(bitmapImage, 64, nh, true);
                            multipartImageUpload("b");
                        }
                    }

                    Toast.makeText(getApplicationContext(), response.code() + " ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setName(View view) {
        if (!userName.getText().toString().equals("")){
            HashMap<String,String> map = new HashMap<>();
            map.put("name",userName.getText().toString());
            map.put("phone",user.getPhone());
            Call<Void> call = new Server().getRetrofitInterface().setName(map);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code()==200){
                        String phone = user.getPhone();
                        user.clearTable();
                        user.setDetails(phone,userName.getText().toString());
                        startActivity(new Intent(PhoneAuthActivityc.this, MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });


        }else {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
        }
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
}