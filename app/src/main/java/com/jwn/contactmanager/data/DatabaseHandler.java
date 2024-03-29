package com.jwn.contactmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jwn.contactmanager.R;
import com.jwn.contactmanager.model.Contact;
import com.jwn.contactmanager.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context){
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }


    // create table_name(id, name, phone_number);
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL command
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT,"
                +Util.KEY_PHONE_NUMBER + " TEXT" + ")";

        db.execSQL(CREATE_CONTACT_TABLE);// create the table
    }// end onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

        //Create table again
        onCreate(db);

    }//end onUpgrade


    //CRUD ~ Create,Read,Update,Delete
    //Add Contact
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());

        //Insert to row
        db.insert(Util.TABLE_NAME, null, values);
        Log.d("DBHandler", "addContact:" + "item added");
        db.close();

    }//end addContact

    //Get contact
    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME,
                new String[]{Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor !=null) cursor.moveToFirst();// first row

            Contact contact = new Contact();
            contact.setId(Integer.parseInt(cursor.getString(/*column index*/0)));
            contact.setName(cursor.getString(1));
            contact.setPhoneNumber(cursor.getString(2));

            return contact;

    }//end getContact

    //Get all Contacts
    public List<Contact> getAllContacts(){
        List<Contact> contactList= new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        //Select all Contacts
        String selectAll = "SELECT * FROM " +Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        //Loop through the data
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(/*column index*/0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                //add contact objects to our list
                contactList.add(contact);
            } while ( cursor.moveToNext());
        }

        return contactList;

    }//end List<Contact>

    //Update contact
    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        //update the row
        //update(table_name, values, where id = xx
        return db.update(Util.TABLE_NAME, values,Util.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
    }//end updateContact

    //Delete single contact
    public void deletContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});

        db.close();

    }//end deleteContact


    public int getCount(){
       String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery(countQuery, null);

       return cursor.getCount();
    }

}//end DatabaseHandler
