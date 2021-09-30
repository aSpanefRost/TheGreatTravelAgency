package com.ardee.thegreattravelagency;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class TravelPackages extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> package_id, package_name, package_capacity, package_spaces_available;
    CustomAdapterPackage customAdapterPackage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_packages);

        recyclerView= findViewById(R.id.rvTravelPackages);
        myDB= new MyDatabaseHelper(TravelPackages.this);

        //Initialising
        package_id = new ArrayList<>();
        package_name = new ArrayList<>();
        package_capacity = new ArrayList<>();
        package_spaces_available = new ArrayList<>();

        //getting data from database and storing them in lists
        storeDataInArrays();

        //Setting Up the Recycler View
        customAdapterPackage = new CustomAdapterPackage(TravelPackages.this, package_id, package_name, package_capacity,package_spaces_available);
        recyclerView.setAdapter(customAdapterPackage);
        recyclerView.setLayoutManager(new LinearLayoutManager(TravelPackages.this));
    }

    public void addPackage(View view) {
        Intent intent= new Intent(this, AddTravelPackage.class);
        startActivity(intent);
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllPackageData();
        if(cursor.getCount()==0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                package_id.add(cursor.getString(0));
                package_name.add(cursor.getString(1));
                package_capacity.add(cursor.getString(2));
                package_spaces_available.add(cursor.getString(3));
            }
        }
    }
}