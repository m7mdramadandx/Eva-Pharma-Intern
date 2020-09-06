package com.ramadan.eva.ui.activitiy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.ramadan.eva.R;
import com.ramadan.eva.data.model.User;
import com.ramadan.eva.database.Database;
import com.ramadan.eva.ui.adapter.ContactAdapter;
import com.ramadan.eva.ui.service.CallAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Dashboard extends AppCompatActivity {

    CardView gmail, profile, dial, back;
    TextView hello;
    User user;
    static com.ramadan.eva.database.Contact contact;
    ArrayList<com.ramadan.eva.database.Contact> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        hello = findViewById(R.id.Hello);
        gmail = findViewById(R.id.gmail);
        profile = findViewById(R.id.profile);
        dial = findViewById(R.id.dial);
        back = findViewById(R.id.back);
        gmail.setOnClickListener(v -> emailIntent());
        profile.setOnClickListener(v -> {
            Intent profile = new Intent(this, Profile.class);
            profile.putExtra("user", user);
            startActivity(profile);
        });
        dial.setOnClickListener(v -> dialIntent());
        back.setOnClickListener(v -> contacts());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().hasExtra("user")) {
            user = (User) getIntent().getSerializableExtra("user");
            System.out.println(user.firstName + "---------");
            hello.setText("Hello " + user.firstName);
        }
    }

    void emailIntent() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", user.email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));

    }

    void dialIntent() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + user.phone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        } else {
            try {
                startActivity(callIntent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    void contacts() {
        new CallAPI(Dashboard.this);
        new MyThread().start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(Dashboard.this, Contact.class));


    }


    class MyThread extends Thread {

        @Override
        public void run() {
            super.run();
            Database database = Database.getInstance(Dashboard.this);
            List<com.ramadan.eva.database.Contact> list = database.contactDao().retrieveContacts();
            System.out.println(list.size());
            Contact.adapter = new ContactAdapter(list);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                list.forEach(_contact -> {
                            if (_contact.name.equals("Dido")) {
                                database.contactDao().deleteContact(contact)
                                        .subscribeOn(Schedulers.computation())
                                        .subscribe(new CompletableObserver() {
                                            @Override
                                            public void onSubscribe(Disposable d) {
                                                System.out.println(_contact.email);
                                            }

                                            @Override
                                            public void onComplete() {
                                                System.out.println("Deleted");
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }
                                        });

                            }
                        }
                );
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(Dashboard.this, list.size(), Toast.LENGTH_SHORT).show();
                }
            });

//            dbInsertion();

        }
    }


    class MyAsyncTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Main Thread

        }

        @Override
        protected String doInBackground(String... strings) {
            // Background Thread
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("Async", "Current number is: " + i);
            }
            return "Task Finished";
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Main Thread -> called within background thread execution
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Main
        }

        /*
        When compare AsyncTaskLoader vs. AsyncTask, as you may know when you rotate your device screen, it may destroy and re-create your activity, to make it clear let image rotate your device while networking transaction is going on:

AsyncTask will be re-executed as background thread again, and previous background thread processing was just be redundant and zombie.

AsyncTaskLoader will be just re-used basing on Loader ID that registered in Loader Manager before, so avoid re-executing network transaction.

In summary, AsyncTaskLoader prevent duplication of background threads and eliminate duplication of zombie activities.
         */
    }
}