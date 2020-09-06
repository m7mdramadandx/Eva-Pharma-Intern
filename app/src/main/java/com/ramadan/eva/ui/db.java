package com.ramadan.eva.ui;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ramadan.eva.R;
import com.ramadan.eva.database.Contact;
import com.ramadan.eva.database.Database;
import com.ramadan.eva.ui.service.CallAPI;

import java.util.List;

public class db extends AppCompatActivity {

    public static final String NAME_KEY = "NAME";
    public static final String SHARED_PREFERENCES_NAME = "MyShared";

    private EditText idEditText;
    private EditText nameEditText;
    private EditText ageEditText;

    Contact contact;

    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db);
//        getSupportFragmentManager().beginTransaction().replace(R.id.holder,new BlankFragment()).commit();

        idEditText = findViewById(R.id.et_id);
        nameEditText = findViewById(R.id.et_name);
        ageEditText = findViewById(R.id.et_age);

        helper = new DatabaseHelper(this);

    }

    public void saveToSharedPref(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME_KEY, "New Name");
        editor.apply();
    }

    public void retrieveFromSharedPref(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(NAME_KEY, "No Last Name found");
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }


    public void saveToDatabase(View view) {
        helper.insertUser(Integer.parseInt(idEditText.getText().toString()),
                nameEditText.getText().toString(),
                Integer.parseInt(ageEditText.getText().toString()));
    }

    public void retrieveFromDB(View view) {
        String allNames = helper.retrieveAllUser().toString();
        Toast.makeText(this, allNames, Toast.LENGTH_SHORT).show();
    }

    public void initThread(View view) {
        contact = new Contact();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            CallAPI.contactsList.forEach(contactModel -> {
                System.out.println("One");
                contact.contactID = contactModel.getId();
                contact.name = contactModel.getName();
                contact.email = contactModel.getEmail();
                contact.address = contactModel.getAddress();
                contact.gender = contactModel.getGender();
                contact.mobile = contactModel.getPhone().getMobile();
                contact.home = contactModel.getPhone().getHome();
            });
        } else {
            System.out.println("SDK N Problem");
        }

        new MyThread().start();
    }

    private void saveToRoomDB() {
//        Database database = Database.getInstance(this);
//        database.contactDao().insertContact(contact);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(db.this, contact.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void retrieveToRoomDB() {
//        Database database = Database.getInstance(this);
//        List<Contact> contactList = database.contactDao().retrieveContacts();
//        System.out.println(contactList.size() + " ContactList Size ");
    }

    void test(String... names) {

        for (String name : names) {


        }
    }

    class MyThread extends Thread {

        @Override
        public void run() {
            super.run();
            // Code to run in another thread
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    nameEditText.setText("Name 00");
//                }
//            });
            saveToRoomDB();
            retrieveToRoomDB();

        }
    }
}