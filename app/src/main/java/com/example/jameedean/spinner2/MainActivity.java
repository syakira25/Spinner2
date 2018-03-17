package com.example.jameedean.spinner2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jameedean.spinner2.adapter.AgencyAdapter;
import com.example.jameedean.spinner2.data.Reference;
import com.example.jameedean.spinner2.model.AgencyModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mKeys;
    private AgencyAdapter mAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("DB_AGENCY");

    // Firebase Authentication
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mCurrentUser;

    Spinner simpleSpinner;
    //private DatabaseReference mAgencyReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FirebaseApp firebaseApp = FirebaseApp.initializeApp(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentUser = mFirebaseAuth.getCurrentUser();
        mKeys = new ArrayList<>();

        //myRef = FirebaseDatabase.getInstance().getReference("agency");

        // listening for changes
        myRef.child("agency").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> simpleList = new ArrayList<String>();
                // load data
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    String name = noteSnapshot.child("name").getValue(String.class);
                    if (name!=null){
                       simpleList.add(name);
                    }
                }
                simpleSpinner = (Spinner) findViewById(R.id.simple_spinner);
                ArrayAdapter<String> simpleAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, simpleList);
                simpleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                simpleSpinner.setAdapter(simpleAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // stop listening
            }

        });
    }}

