package com.ramadan.eva.ui.activitiy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ramadan.eva.R;
import com.ramadan.eva.data.model.User;

import java.io.File;

public class Profile extends AppCompatActivity {
    User user;
    TextView fullName, email, phone;
    ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        profilePicture = findViewById(R.id.profilePicture);
//        setPicture();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().hasExtra("user")) {
            user = (User) getIntent().getSerializableExtra("user");
            fullName.setText(user.firstName + " " + user.lastName);
            email.setText(user.email);
            phone.setText(user.phone);
        }
    }

    void setPicture() {
        SignUp signUp = new SignUp();
        Bitmap bitmap;
        System.out.println(signUp.currentPhotoPath + "*/*/*");
        File file = new File(signUp.currentPhotoPath);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), bmOptions);
        bmOptions.inSampleSize = 2;
        bmOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file.getPath(), bmOptions);
        profilePicture.setImageBitmap(bitmap);

    }
}