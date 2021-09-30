package com.ardee.thegreattravelagency;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddPassenger extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText name, balance;
    String type;
    Button btn;
    String package_id, package_name;
    private Spinner spinner;
    private static final String[] paths = {"Standard", "Gold", "Premium"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_passenger);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setting standard/gold/premium options
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(AddPassenger.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //getting intents
        package_id=getIntent().getStringExtra("package_id");
        package_name=getIntent().getStringExtra("package_name");

        //assigning views
        name= findViewById(R.id.et_passenger_name);
        balance= findViewById(R.id.et_passenger_balance);
        btn=findViewById(R.id.btAddPassenger);

        //On clicking on the add button adds the passenger details into database
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB= new MyDatabaseHelper(AddPassenger.this);
                int spaces=myDB.dbGetPassengerSpaces(package_id);

                //if number of spaces in the package is 0, we can't add passenger
                if(spaces==0){
                    Toast.makeText(AddPassenger.this, "Cannot Add More Passengers!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddPassenger.this, Passengers.class);
                    //passing few values
                    intent.putExtra("package_name",package_name);
                    intent.putExtra("package_id",package_id);
                    startActivity(intent);
                }
                else{
                    //adding the details into database
                    myDB.dbAddPassenger(name.getText().toString().trim(),
                            type,
                            Integer.valueOf(balance.getText().toString().trim()),
                            Integer.valueOf(package_id.trim())
                    );
                    //decrementing number of spaces available in the package by 1
                    myDB.dbDecrementPassengerSpaces(package_id);
                    int passenger_id=myDB.dbGetPassengerId(name.getText().toString().trim());
                    Log.d(TAG, "got this passenger id"+passenger_id);

                    //Starting the AddPassengerActivities activity
                    Intent intent = new Intent(AddPassenger.this, AddPassengerActivities.class);
                    intent.putExtra("package_name",package_name);
                    intent.putExtra("package_id",package_id);
                    intent.putExtra("passenger_id",passenger_id);
                    intent.putExtra("passenger_type",type);
                    startActivity(intent);
                }
            }
        });

    }

    //On clicking on the back button, goes back to Passengers activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, Passengers.class);
                //passing package_id and package_name values
                intent.putExtra("package_name",package_name);
                intent.putExtra("package_id",package_id);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //setting up the variable "type" depending the passenger_type chosen by the user
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                type="standard";
                break;
            case 1:
                type="gold";
                break;
            case 2:
                type="premium";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}