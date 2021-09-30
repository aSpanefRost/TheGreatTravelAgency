package com.ardee.thegreattravelagency;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class PackageInformation extends AppCompatActivity {

    TextView tv;
    String package_name, package_id, str_itinerary, str_passengers_list, str_activity_list;
    MyDatabaseHelper myDB;
    ArrayList<String> destination_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_information);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        package_name=getIntent().getStringExtra("package_name");
        package_id =getIntent().getStringExtra("package_id");
        myDB= new MyDatabaseHelper(PackageInformation.this);

        //Setting name of Package in the activity
        tv=findViewById(R.id.id_set_name);
        settingText();

        //Getting all the destinations in the package
        destination_id=  new ArrayList<>();
        storeDestinationDataInArrays(Integer.parseInt(package_id));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Itinerary();
    }

    //On clicking the back Button, Opens the TravelPackage Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, TravelPackages.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void settingText(){
        tv.setText(package_name);
    }

    //On clicking the Destinations Button opens the Destination Activity
    public void OpenDestinationsActivity(View view) {
        Intent intent= new Intent(this, Destinations.class);
        //passing package_id and package_name to destination activity
        intent.putExtra("package_id", package_id);
        intent.putExtra("package_name", package_name);
        startActivity(intent);
    }

    //On clicking the Passengers Button opens the Passengers Activity
    public void OpenPassengersActivity(View view) {
        Intent intent= new Intent(this, Passengers.class);
        //passing package_id and package_name to passengers activity
        intent.putExtra("package_id", package_id);
        intent.putExtra("package_name", package_name);
        startActivity(intent);
    }

    //Function to create the Itinerary
    public void Itinerary(){
        str_itinerary ="Package Name:\n"+"  "+package_name+"\n\n";
        for(int i=0;i<destination_id.size();i++){
            str_itinerary+=myDB.getDestinationName(destination_id.get(i))+"::\n";
            storeActivityDataInArrays(Integer.parseInt(destination_id.get(i)));
        }
    }

    //Function to help us in downloading the Itinerary
    public void downloadItinerary(View view) {
        File file = new File(this.getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, package_name+"_Itinerary");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(str_itinerary);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Itinerary Downloaded", Toast.LENGTH_LONG).show();
        } catch (Exception e) { }
    }

    //Storing all the destinations of the package into destinaton_id
    void storeDestinationDataInArrays(int id) {
        Cursor cursor = myDB.readAllDestinationData(id);
        if (cursor.getCount() == 0) {
            //Log.d(TAG,"0");
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                destination_id.add(cursor.getString(0));
            }
            cursor.close();
        }
    }

    //Adding all the Activities of the package into str_itinerary
    void storeActivityDataInArrays(int id){
        Cursor cursor = myDB.readAllActivityData(id);
        if(cursor.getCount()==0) {
            //Log.d(TAG,"2");
            //Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                str_itinerary +="-->\n";
                str_itinerary +="   "+cursor.getString(1)+"\n";   //Activity_name
                str_itinerary +="   Description:\n   "+cursor.getString(2)+"\n"; //Activity_description
                str_itinerary +="   Cost:  "+cursor.getString(3)+"\n"; //Activity_cost
                str_itinerary +="   Spaces Available:  "+cursor.getString(5); //Spaces Available in Activity
                str_itinerary +="\n";
            }
            str_itinerary+="\n";
            cursor.close();
        }
    }

    //storing all the passengers data into str_passengers_list
    private void storePassengersDataInArrays(String id) {
        Cursor cursor = myDB.readAllPassengerData(Integer.parseInt(id));
        if(cursor.getCount()==0) {
            //Log.d(TAG,"1");
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                str_passengers_list+=cursor.getString(0); //Passenger_id
                str_passengers_list+="    ";
                str_passengers_list+=cursor.getString(1); //Passenger_name
                str_passengers_list+="\n";
            }
        }
    }

    //function to help us download passengers list of the package
    public void downloadPassengerList(View view) {
        str_passengers_list=package_name+"\n";
        //passenger capacity of package
        str_passengers_list+="Package Capacity: "+myDB.dbGetPassengerCapacity(package_id)+"\n";
        //passengers enrolled
        str_passengers_list+="Passengers Enrolled: "+(myDB.dbGetPassengerCapacity(package_id)- myDB.dbGetPassengerSpaces(package_id))+"\n\n";
        str_passengers_list+="Name     Id\n";
        storePassengersDataInArrays(package_id);

        File file = new File(this.getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, package_name+"_passengers_list");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(str_passengers_list);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Passengers List Downloaded", Toast.LENGTH_LONG).show();
        } catch (Exception e) { }

    }

    //function to download all the activities of the package that still have spaces left
    public void downloadSpaciousActivities(View view) {
        str_activity_list="Activities With Spaces Available\n\n";
        for(int i=0;i<destination_id.size();i++){
            Cursor cursor = myDB.dbGetActivitiesWithSpaces(Integer.parseInt(destination_id.get(i)));
            if(cursor.getCount()==0) {
            }else{
                while(cursor.moveToNext()){
                    str_activity_list+=cursor.getString(1)+"    ";  //Activity_name
                    str_activity_list+="Spaces: "+cursor.getString(5);  //Spaces left in the Activity
                    str_activity_list+="\n";
                }
                cursor.close();
            }
        }

        File file = new File(this.getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, package_name+"_activity_list");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(str_activity_list);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Activity List Downloaded", Toast.LENGTH_LONG).show();
        } catch (Exception e) { }


    }
}