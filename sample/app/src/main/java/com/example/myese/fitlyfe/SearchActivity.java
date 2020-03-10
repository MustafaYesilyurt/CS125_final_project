package com.example.myese.fitlyfe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private ChildEventListener childEventListener;

    private ArrayList<Location> locationList;
    private ArrayList<Location> searchResults;

    private LocationAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("locations");

        // Initializes the local data structure to store the database
        locationList = new ArrayList<Location>();

        // Set up an array that will have the contents that you want to display
        searchResults = new ArrayList<Location>();

        // Sets up the event listener that will specify what happens when access of a node
        // occurs in the database
        childEventListener = new ChildEventListener(){
            // Method is run when any new node is added to the database, and once
            // for every existing node when the activity is loaded
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Adds the Contact to the local data structure
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

        // Need to add the event listener to the database
        myRef.addChildEventListener(childEventListener);

        // Sets up the list adapter to read from the results array
        listAdapter = new LocationAdapter( this, locationList);
        ListView results = findViewById(R.id.listViewResults);
        results.setAdapter(listAdapter);
    }

    public void search(View view)
    {
        boolean found = false;
        String search = ((EditText)findViewById(R.id.editTextName)).getText().toString();

        for (Location loc: locationList) {
            if(loc.getName().substring(0, search.length()).equalsIgnoreCase(search.substring(0, search.length()))) {
                searchResults.add(loc);
                found = true;
            }
        }
        listAdapter = new LocationAdapter( this, searchResults);
        ListView results = findViewById(R.id.listViewResults);
        results.setAdapter(listAdapter);

        EditText name = (EditText)findViewById(R.id.editTextName);
        if(!found) {
            //Toast.makeText(this, name.getText().toString() + " not found.", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Input was not found.", Toast.LENGTH_LONG).show();
        }
        name.setText("");
    }

    public void goHome( View view )
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity( intent);
    }
}
