package com.jwn.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.jwn.contactmanager.data.DatabaseHandler;
import com.jwn.contactmanager.model.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(MainActivity.this);


Log.d("Count","onCreate: " + db.getCount());

        List<Contact> contactList = db.getAllContacts();


        for (Contact contact : contactList) {
            Log.d("MainActivity", "onCreate: " + contact.getName());

        }

    }//end onCreate
}//end MainActivity
