package com.example.myese.ics45jfinalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myese.ics45jfinalproject.MainActivity;
import com.example.myese.ics45jfinalproject.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("locations");
    }

    public void addLocation(View view)
    {

        EditText editName = findViewById(R.id.editTextName);
        String name = editName.getText().toString();
        EditText editCity = findViewById(R.id.editTextCity);
        String city = editCity.getText().toString();
        EditText editState = findViewById(R.id.editTextState);
        String state = editState.getText().toString();

        if( name.length() > 0 )
        {
            String key = myRef.push().getKey(); // Generates unique random key
            Location loc = new Location(name, city, state, key);
            myRef.child(key).setValue(loc);   // Adds new Contact to the Database
            Toast.makeText(this, loc.getName() + " successfully added.", Toast.LENGTH_LONG).show();
        }

        // Resets fields
        editName.setText("");
        editCity.setText("");
        editState.setText("");

    }

    public void goHome( View view )
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
