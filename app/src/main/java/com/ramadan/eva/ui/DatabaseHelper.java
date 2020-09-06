package com.ramadan.eva.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "APP_DATABASE";
    private static final int DATABASE_VERSION = 2;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //VERSION 2
        // CREATING ALL TABLES
        sqLiteDatabase.execSQL("CREATE TABLE USERS (" +
                "    ID   NUMERIC   PRIMARY KEY," +
                "    NAME TEXT (50)," +
                "    AGE  NUMERIC," +
                "JOB TEXT" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // VERSION 2
        // MIGRATING FROM V1 TO V2
        // DELETE ALL TABLES , CREATE (onCreate())-> SELECT CREATE INSERT

        sqLiteDatabase.execSQL("DROP TABLE USERS");
        onCreate(sqLiteDatabase);
    }

    // REST OF METHODS

    public void insertUser(int id, String name, int age) {
        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("INSERT INTO USERS (AGE, NAME, ID)" +
//                "VALUES (" + age + "," +
//                "'" + name + "'," +
//                "" + id + "); ");
        ContentValues cv = new ContentValues();
        cv.put("ID", id);
        cv.put("NAME", name);
        cv.put("AGE", age);
        db.insert("USERS", null, cv);
        db.close();
    }


    public String retrieveUser(int id) {
        String name = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT NAME FROM USERS WHERE ID = ?", new String[]{id + ""});
        if (cs.moveToFirst()) {
            name = cs.getString(cs.getColumnIndex("NAME"));
        }
        cs.close();
        db.close();
        return name;
    }

    public ArrayList<String> retrieveAllUser() {
        ArrayList<String> names = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT NAME FROM USERS", null);
        if (cs.moveToFirst()) {
            do {
                names.add(cs.getString(cs.getColumnIndex("NAME")));
            } while (cs.moveToNext());
        }
        cs.close();
        db.close();
        return names;
    }


}
