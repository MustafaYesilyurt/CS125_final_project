package com.example.myese.fitlyfe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private ChildEventListener childEventListener;

    private ArrayList<Location> locationList;

    private LocationAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("locations");

        // Initializes the local data structure to store the database
        locationList = new ArrayList<Location>();

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

        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                Location selectedItem = (Location) parent.getItemAtPosition(position);

                // Removes that Location from firebase
                myRef.child(selectedItem.getUid()).removeValue();

                // Removes the Location from the local data structure
                locationList.remove(selectedItem);

                // Refreshes the results on the ListView to reflect removal of selected Location
                refresh(selectedItem.getName());
            }
        });
    }

    public void refresh(String update) {
        listAdapter = new LocationAdapter( this, locationList);
        ListView results = findViewById(R.id.listViewResults);
        results.setAdapter(listAdapter);
    }

    public void goHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
