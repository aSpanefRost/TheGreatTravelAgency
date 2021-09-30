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

public class Passengers extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    private ArrayList passenger_id, passenger_name, passenger_type,
            passenger_balance, package_id;
    CustomAdapterPassenger customAdapterPassenger;
    String pkg_id, pkg_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting the intent values
        pkg_id =getIntent().getStringExtra("package_id");
        pkg_name =getIntent().getStringExtra("package_name");

        recyclerView= findViewById(R.id.rvPassengers);
        myDB= new MyDatabaseHelper(Passengers.this);

        //initialising lists
        passenger_id = new ArrayList<>();
        passenger_name = new ArrayList<>();
        passenger_type = new ArrayList<>();
        passenger_balance = new ArrayList<>();
        package_id = new ArrayList<>();

        //storing all the passengers data from database to lists
        storeDataInArrays(pkg_id);

        //setting up the Custom Adapter and recyclerview
        customAdapterPassenger = new CustomAdapterPassenger(Passengers.this, passenger_id,
                passenger_name, passenger_type, passenger_balance, package_id);
        recyclerView.setAdapter(customAdapterPassenger);
        recyclerView.setLayoutManager(new LinearLayoutManager(Passengers.this));
    }

    //On clicking on the back button opening the PackageInformation activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, PackageInformation.class);
                //passing package_id and package_name
                intent.putExtra("package_name",pkg_name);
                intent.putExtra("package_id",pkg_id);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //on clicking on the floatingButton opens up the AddPassenger activity
    public void addPassenger(View view) {
        Intent intent= new Intent(Passengers.this, AddPassenger.class);
        //passing the package_id and package_name
        intent.putExtra("package_id", pkg_id);
        intent.putExtra("package_name",pkg_name);
        startActivity(intent);
    }

    private void storeDataInArrays(String id) {
        Cursor cursor = myDB.readAllPassengerData(Integer.parseInt(id));
        if(cursor.getCount()==0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                passenger_id.add(cursor.getString(0));
                passenger_name.add(cursor.getString(1));
                passenger_type.add(cursor.getString(2));
                passenger_balance.add(cursor.getString(3));
                package_id.add(cursor.getString(3));
            }
        }
    }
}