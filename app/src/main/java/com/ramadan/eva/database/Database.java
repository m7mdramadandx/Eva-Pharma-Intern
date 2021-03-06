package com.ramadan.eva.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Contact.class}, version = 2)
public abstract class Database extends RoomDatabase {

    public abstract ContactDao contactDao();

    private static Database database;

    public static synchronized Database getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), Database.class, "ContactsDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
}
