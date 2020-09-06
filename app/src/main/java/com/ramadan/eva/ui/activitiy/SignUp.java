package com.ramadan.eva.ui.activitiy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.ramadan.eva.R;
import com.ramadan.eva.databinding.SignUpBinding;
import com.ramadan.eva.ui.viewmodel.ViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUp extends AppCompatActivity {
    private int REQUEST_CAMERA = 0;
    String currentPhotoPath;
    SignUpBinding binding;
    ViewModel viewModel;
    ImageView b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.sign_up);
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        binding.setUserModel(viewModel);
        b = findViewById(R.id.profilePicture);
        b.setOnClickListener(v -> cameraIntent());
    }

    private void cameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    0);
        } else {
            try {
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            saveImageToExternalStorage(imageBitmap);
            b.setImageBitmap(imageBitmap);
        }
    }

    private void saveImageToExternalStorage(Bitmap bitmap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    0);
        } else {
            try {
                try {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    File dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    String filePath = dirPath + "/" + timeStamp + ".jpg";
                    File image = new File(filePath);
                    currentPhotoPath = filePath;
                    if (!dirPath.exists())
                        dirPath.mkdirs();
                    image.createNewFile();
                    OutputStream outStream;
                    outStream = new FileOutputStream(image);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                } catch (Exception e) {
                    Log.e("saveToExternalStorage()", e.getMessage());
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

}