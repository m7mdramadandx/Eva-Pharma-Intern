package com.ramadan.eva.data.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {


    public ArrayList<ContactModel> parseJsonList(String jsonArrayString) {
        ArrayList<ContactModel> contacts = new ArrayList<>();

        Gson gson = new Gson();

        // Too convert object
//        Contact contact=gson.fromJson("JSON OBJECT STRING",Contact.class);
        contacts = gson.fromJson(jsonArrayString, new TypeToken<List<ContactModel>>() {
        }.getType());
        //new TYPE -> TypeToken create new type of parsed result
        return contacts;
    }
}
