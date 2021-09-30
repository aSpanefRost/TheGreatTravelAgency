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

public class Destinations extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> destination_id ,package_id, destination_name;
    CustomAdapterDestination customAdapterDestination;
    String pkg_id, pkg_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting the intent values
        pkg_id =getIntent().getStringExtra("package_id");
        pkg_name =getIntent().getStringExtra("package_name");


        recyclerView= findViewById(R.id.rvDestinations);
        myDB= new MyDatabaseHelper(Destinations.this);

        //Initialising
        destination_id = new ArrayList<>();
        destination_name = new ArrayList<>();
        package_id = new ArrayList<>();

        //Storing datas from database into lists
        storeDataInArrays(Integer.parseInt(pkg_id));

        //Setting up the recyclerview
        customAdapterDestination = new CustomAdapterDestination(Destinations.this, destination_id,
                destination_name,package_id,pkg_id,pkg_name);
        recyclerView.setAdapter(customAdapterDestination);
        recyclerView.setLayoutManager(new LinearLayoutManager(Destinations.this));
    }


    //On clicking the back button, opens the PackageInformation Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, PackageInformation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //passing the package_name and package_id to packageInformation activity
                intent.putExtra("package_name", pkg_name);
                intent.putExtra("package_id",pkg_id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //On clicking the floating button, opens up the AddDestination Activity
    public void addDestination(View view) {
        Intent intent = new Intent(Destinations.this,AddDestination.class);
        //passing the package_id and package_name
        intent.putExtra("package_id", pkg_id);
        intent.putExtra("package_name", pkg_name);
        startActivity(intent);
    }

    //storing data from database to lists
    void storeDataInArrays(int id){
        Cursor cursor = myDB.readAllDestinationData(id);
        if(cursor.getCount()==0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                destination_id.add(cursor.getString(0));
                destination_name.add(cursor.getString(1));
                package_id.add(cursor.getString(2));
            }
        }
    }


}