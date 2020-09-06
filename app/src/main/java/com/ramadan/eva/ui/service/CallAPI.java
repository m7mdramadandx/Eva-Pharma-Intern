package com.ramadan.eva.ui.service;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ramadan.eva.data.model.ContactModel;
import com.ramadan.eva.database.Contact;
import com.ramadan.eva.database.Database;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CallAPI implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue queue;
    public static ArrayList<ContactModel> contactsList;
    static Contact contact;

    public CallAPI(Context context) {
        if (contactsList == null || contact == null) {
            contactsList = new ArrayList<>();
            contact = new Contact();
        }
        new VolleyHandler(context).callAPI(response -> {
                    Gson gson = new Gson();
                    try {
                        String contactsJsonString = response.getJSONArray("contacts").toString();
                        contactsList = gson.fromJson(contactsJsonString, new TypeToken<List<ContactModel>>() {
                        }.getType());
                        insertContacts(context);
                    } catch (JSONException e) {
                        Log.e("Volley", e.getMessage());
                    }
                },
                error -> {
                    System.out.println("Error");
                });
        Log.d("Volley", " ---");


        new VolleyHandler(context).callAPI(this, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Volley", "ErrorResponse");
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("Volley", "response");
        System.out.println("Success");

    }

    public static void insertContacts(Context context) {
        Database database = Database.getInstance(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            CallAPI.contactsList.forEach(contactModel -> {
                contact.contactID = contactModel.getId();
                contact.name = contactModel.getName();
                contact.email = contactModel.getEmail();
                contact.address = contactModel.getAddress();
                contact.gender = contactModel.getGender();
                contact.mobile = contactModel.getPhone().getMobile();
                contact.home = contactModel.getPhone().getHome();


                database.contactDao().insertContact(contact).subscribeOn(Schedulers.computation())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onComplete() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println(contact.name + " Failed");
                            }
                        });
            });


        } else {
            System.out.println("SDK Problem");
        }
    }

    public static void retrieveContacts(Context context) {

        Database database = Database.getInstance(context);
//        com.ramadan.eva.ui.activitiy.Contact.adapter.setDataList(database.contactDao().retrieveContacts());

    }


}
