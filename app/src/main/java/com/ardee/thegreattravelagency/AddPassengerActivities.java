package com.ardee.thegreattravelagency;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class AddPassengerActivities extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    private ArrayList activity_id, activity_name, activity_description, activity_cost, activity_capacity,
            activity_spaces_available, destination_id;
    CustomAdapterPassengerActivities customAdapterPassengerActivities;
    int passenger_id;
    String package_id, package_name, passenger_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_passenger_activities);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView= findViewById(R.id.rvPassengerActivites);
        myDB= new MyDatabaseHelper(AddPassengerActivities.this);

        //getting the intent values
        passenger_id=getIntent().getIntExtra("passenger_id",1);
        package_id=getIntent().getStringExtra("package_id");
        package_name=getIntent().getStringExtra("package_name");
        passenger_type=getIntent().getStringExtra("passenger_type");

        //initialising
        activity_id = new ArrayList<>();
        activity_name = new ArrayList<>();
        activity_description = new ArrayList<>();
        activity_cost = new ArrayList<>();
        activity_capacity = new ArrayList<>();
        activity_spaces_available = new ArrayList<>();
        destination_id = new ArrayList<>();

        //storing all the activity datas of the package in the lists from database
        storeDataInArrays(package_id);


        //setting up the Custom Adapter and recycler views
        customAdapterPassengerActivities = new CustomAdapterPassengerActivities
                (AddPassengerActivities.this, activity_id, activity_name, activity_description,
                        activity_cost, activity_capacity, activity_spaces_available,
                        destination_id, passenger_id, passenger_type);
        recyclerView.setAdapter(customAdapterPassengerActivities);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddPassengerActivities.this));

    }

    //on clicking on the back button opens the AddPassenger Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, AddPassenger.class);
                //passing the package_id and package_name
                intent.putExtra("package_name",package_name);
                intent.putExtra("package_id",package_id);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //on clicking on the add button opens up the addPassengerActivities activity
    public void addPassengerActivity(View view) {
        Intent intent= new Intent(this,Passengers.class);
        intent.putExtra("package_name",package_name);
        intent.putExtra("package_id",package_id);
        startActivity(intent);
    }

    //storing activity datas from database to lists
    void storeDataInArrays(String id){
        Cursor cursor = myDB.readAllActivityDataByPackID(Integer.parseInt(id));
        if(cursor.getCount()==0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                activity_id.add(cursor.getString(0));
                activity_name.add(cursor.getString(1));
                activity_description.add(cursor.getString(2));
                activity_cost.add(cursor.getString(3));
                activity_capacity.add(cursor.getString(4));
                activity_spaces_available.add(cursor.getString(5));
                destination_id.add(cursor.getString(6));
            }
        }
    }
}