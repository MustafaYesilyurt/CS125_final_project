package com.example.myese.ics45jfinalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addLocation(View view) {
        Intent intent = new Intent( this, com.example.myese.ics45jfinalproject.AddActivity.class);
        startActivity(intent);
    }

    public void editLocation(View view) {
        Intent intent = new Intent( this, com.example.myese.ics45jfinalproject.EditActivity.class);
        startActivity(intent);
    }

    public void planLocation(View view) {
        Intent intent = new Intent( this, com.example.myese.ics45jfinalproject.PlanActivity.class);
        startActivity(intent);
    }

    public void deleteLocation(View view) {
        Intent intent = new Intent( this, com.example.myese.ics45jfinalproject.DeleteActivity.class);
        startActivity(intent);
    }

    public void searchLocation(View view) {
        Intent intent = new Intent( this, com.example.myese.ics45jfinalproject.SearchActivity.class);
        startActivity(intent);
    }

    public void viewLocation(View view) {
        Intent intent = new Intent( this, com.example.myese.ics45jfinalproject.ViewActivity.class);
        startActivity(intent);
    }

}
