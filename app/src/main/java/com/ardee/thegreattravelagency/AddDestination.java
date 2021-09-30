package com.ardee.thegreattravelagency;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDestination extends AppCompatActivity {

    EditText name;
    Button btn;
    String pkg_id , pkg_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting the intent values
        pkg_id =getIntent().getStringExtra("package_id");
        pkg_name =getIntent().getStringExtra("package_name");

        name = findViewById(R.id.editText_destination_name);
        btn = findViewById(R.id.btAddDestination);

        //On clicking on the add button inserting the Destination into database
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB= new MyDatabaseHelper(AddDestination.this);
                String temp_name=name.getText().toString();

                if(temp_name!= null){
                    myDB.dbAddDestination(name.getText().toString().trim(),
                            Integer.valueOf(pkg_id.trim()));
                }
                else{
                    Toast.makeText(AddDestination.this,"Failed",Toast.LENGTH_SHORT).show();
                }

               //After adding the destination to database, opening the Destinations activity
                Intent intent = new Intent(AddDestination.this, Destinations.class);
                //passing the package_id and package_name back
                intent.putExtra("package_id", pkg_id);
                intent.putExtra("package_name", pkg_name);
                startActivity(intent);

            }
        });
    }


    //On clicking the back Button going back to Destinations activty
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, Destinations.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //passing the package_name and package_id
                intent.putExtra("package_name", pkg_name);
                intent.putExtra("package_id",pkg_id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}