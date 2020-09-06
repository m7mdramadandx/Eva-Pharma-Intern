package com.ramadan.eva.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertContact(Contact contact);

    @Query("SELECT * FROM CONTACTS")
    List<Contact> retrieveContacts();

    @Query("SELECT * FROM CONTACTS WHERE CONTACT_ID = :id")
    Contact retrieveContactByID(int id);

    @Delete
    Completable deleteContact(Contact contact);
}
