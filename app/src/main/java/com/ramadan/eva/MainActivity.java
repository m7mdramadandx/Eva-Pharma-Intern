package com.ramadan.eva;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ramadan.eva.ui.activitiy.Contact;
import com.ramadan.eva.ui.service.CallAPI;

public class MainActivity extends AppCompatActivity {

    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusTextView = findViewById(R.id.tv_progress_status);

//        List<String> names = new ArrayList<>();
//        Stream<String> stream = names.stream();
//        List<String> newNames =  stream.map(it -> it + " New").collect(Collectors.toList());
//        List<String> newNames2 =  stream.map(it -> it + " New").collect(Collectors.toList());

    }


    public void StartAsync(View view) {
//        new MyAsyncTask().execute("", "", "");
        new MyAsyncTask().execute();


    }


    class MyAsyncTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Main Thread
            statusTextView.setText("Loading");

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
                publishProgress(i);
                Log.d("Async", "Current number is: " + i);
            }


            return "Task Finished";
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Main Thread -> called within background thread execution
            statusTextView.setText("Progressing " + values[0] * 20 + "%");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            statusTextView.setText("Finished with result " + s);
            new CallAPI(MainActivity.this);
            startActivity(new Intent(MainActivity.this, Contact.class));
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