package com.ardee.thegreattravelagency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText name, description, capacity, cost;
    String destination_id, destination_name, package_id, package_name;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Assigning views
        name=findViewById(R.id.et_activity_name);
        description=findViewById(R.id.et_activity_description);
        capacity=findViewById(R.id.et_activity_capacity);
        cost=findViewById(R.id.et_activity_cost);

        //getting the intent values
        destination_id =getIntent().getStringExtra("destination_id");
        destination_name=getIntent().getStringExtra("destination_name");
        package_id =getIntent().getStringExtra("package_id");
        package_name =getIntent().getStringExtra("package_name");

        btn=findViewById(R.id.btAddActivity);

        //On clicking on the Add button adding the activity details to Database
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB= new MyDatabaseHelper(AddActivity.this);
                myDB.dbAddActivity(name.getText().toString().trim(),
                        description.getText().toString().trim(),
                        Integer.valueOf(cost.getText().toString().trim()),
                        Integer.valueOf(capacity.getText().toString().trim()),
                        Integer.valueOf(destination_id.trim()),
                        package_id);

                //After inserting into database , opening the Activities class
                Intent intent = new Intent(AddActivity.this, Activities.class);
                //passing intent values
                intent.putExtra("destination_id", destination_id);
                intent.putExtra("destination_name", destination_name);
                intent.putExtra("package_name",package_name);
                intent.putExtra("package_id",package_id);
                startActivity(intent);
            }
        });
    }

    //after clicking on the back button opens up the Activities class
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, Activities.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //passing few values
                intent.putExtra("destination_id", destination_id);
                intent.putExtra("destination_name", destination_name);
                intent.putExtra("package_name",package_name);
                intent.putExtra("package_id",package_id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}