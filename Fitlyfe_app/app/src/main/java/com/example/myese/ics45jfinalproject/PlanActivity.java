package com.example.myese.ics45jfinalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    //private FirebaseDatabase database2;
    //private DatabaseReference myRef2;

    private ChildEventListener childEventListener;
    //private ChildEventListener childEventListener2;

    private ArrayList<Location> locationList;
    private ArrayList<Location> plan;

    private LocationAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("locations");
        //database2 = FirebaseDatabase.getInstance();
        //myRef2 = database2.getReference("plan");

        // Initializes the local data structure to store the database
        locationList = new ArrayList<Location>();

        //Initializes the list that will contain the selected items
        plan = new ArrayList<Location>();

        // Sets up the event listener that will specify what happens when access of a node
        // occurs in the database
        childEventListener = new ChildEventListener(){
            // Method is run when any new node is added to the database, and once
            // for every existing node when the activity is loaded
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Adds the Location to the local data structure
                locationList.add(dataSnapshot.getValue(Location.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
/*
        childEventListener2 = new ChildEventListener(){
            // Method is run when any new node is added to the database, and once
            // for every existing node when the activity is loaded
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Adds the Location to the local data structure
                plan.add(dataSnapshot.getValue(Location.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
*/
        // Need to add the event listener to the database
        myRef.addChildEventListener(childEventListener);
        //myRef2.addChildEventListener(childEventListener2);

        // Sets up the list adapter to read from the results array
        listAdapter = new LocationAdapter( this, locationList);
        ListView results = findViewById(R.id.listViewResults);
        results.setAdapter(listAdapter);

        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                Location selectedItem = (Location) parent.getItemAtPosition(position);

                Location loc = new Location(selectedItem.getName(), selectedItem.getCity(), selectedItem.getState(), selectedItem.getUid());
                //myRef2.child(selectedItem.getUid()).setValue(loc); //The selected location is stored in the "plan" database.

                //Add selected Location to list of selections
                plan.add(selectedItem);

                // Removes the Location from the local data structure
                locationList.remove(selectedItem);

                // Refreshes the results on the ListView to reflect removal of selected Location
                refresh(selectedItem.getName(), locationList);
            }
        });
    }

    public void refresh(String update, ArrayList<Location> arr) {
        listAdapter = new LocationAdapter( this, arr);
        ListView results = findViewById(R.id.listViewResults);
        results.setAdapter(listAdapter);
    }

    public void makePlan(View view)
    {
        refresh("", plan);
        EditText name = (EditText) findViewById(R.id.editTextName);
        String txtName = name.getText().toString();
        Toast.makeText(this, txtName + " displayed.", Toast.LENGTH_LONG).show();
    }

    public void goHome( View view )
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}