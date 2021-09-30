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

public class Activities extends AppCompatActivity {
    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> activity_id, activity_name, activity_description, activity_cost, activity_capacity,
            activity_spaces_available, destination_id;
    CustomAdapterActivity customAdapterActivity;
    String dst_name, dst_id, pkg_id, pkg_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Getting the intent values
        dst_name =getIntent().getStringExtra("destination_name");
        dst_id =getIntent().getStringExtra("destination_id");
        pkg_id =getIntent().getStringExtra("package_id");
        pkg_name =getIntent().getStringExtra("package_name");

        //Setting the title of the activity
        setTitle(dst_name);

        recyclerView = findViewById(R.id.rvActivities);
        myDB = new MyDatabaseHelper(Activities.this);

        //Initialising
        activity_id = new ArrayList<>();
        activity_name = new ArrayList<>();
        activity_description = new ArrayList<>();
        activity_cost = new ArrayList<>();
        activity_capacity = new ArrayList<>();
        activity_spaces_available = new ArrayList<>();
        destination_id = new ArrayList<>();

        //Getting all the activity datas from database and storing them into the lists
        storeDataInArrays(Integer.parseInt(dst_id));


        //setting up the Custom Adapter and recyclerview
        customAdapterActivity = new CustomAdapterActivity
                (Activities.this, activity_id, activity_name, activity_description,
                 activity_cost, activity_capacity, activity_spaces_available,
                        destination_id);
        recyclerView.setAdapter(customAdapterActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(Activities.this));


    }

    //On clicking the back button going back to Destinations class
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, Destinations.class);
                //passing the destination_id, destination_name, package_name, package_id back
                intent.putExtra("destination_id", dst_id);
                intent.putExtra("destination_name", dst_name);
                intent.putExtra("package_name",pkg_name);
                intent.putExtra("package_id",pkg_id);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //On clicking on the floatingButton opens the AddActivity activity
    public void addActivity(View view) {
        Intent intent= new Intent(this, AddActivity.class);
        //passing few intent values
        intent.putExtra("destination_id", dst_id);
        intent.putExtra("destination_name", dst_name);
        intent.putExtra("package_name",pkg_name);
        intent.putExtra("package_id",pkg_id);
        startActivity(intent);
    }

    void storeDataInArrays(int id){
        Cursor cursor = myDB.readAllActivityData(id);
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