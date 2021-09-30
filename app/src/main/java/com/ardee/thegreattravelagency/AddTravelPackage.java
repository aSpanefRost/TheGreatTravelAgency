package com.ardee.thegreattravelagency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTravelPackage extends AppCompatActivity {

    EditText name, capacity;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel_package);

        name = findViewById(R.id.editTextName);
        capacity = findViewById(R.id.editTextCapacity);
        btn = findViewById(R.id.btAddPackageges);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Inserting Package data into database
                MyDatabaseHelper myDB= new MyDatabaseHelper(AddTravelPackage.this);
                myDB.dbAddPackage(name.getText().toString().trim(),
                        Integer.valueOf(capacity.getText().toString().trim()));

                //Starting the TravelPackages Activity
                Intent intent = new Intent(AddTravelPackage.this, TravelPackages.class);
                startActivity(intent);
            }
        });
    }
}