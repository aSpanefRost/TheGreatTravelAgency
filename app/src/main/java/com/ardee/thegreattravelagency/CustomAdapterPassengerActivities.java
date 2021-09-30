package com.ardee.thegreattravelagency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterPassengerActivities extends RecyclerView.Adapter<CustomAdapterPassengerActivities.MyViewHolder>{

    private Context context;
    private ArrayList activity_id, activity_name, activity_description, activity_cost, activity_capacity,
            activity_spaces_available, destination_id;
    int passenger_id;
    String passenger_type;
    MyDatabaseHelper myDB;

    //Constructor
    CustomAdapterPassengerActivities(Context context,
                          ArrayList activity_id,
                          ArrayList activity_name,
                          ArrayList activity_description,
                          ArrayList activity_cost,
                          ArrayList activity_capacity,
                          ArrayList activity_spaces_available,
                          ArrayList destination_id,
                                     int passenger_id,
                                     String passenger_type){
        this.context=context;
        this.activity_id=activity_id;
        this.activity_name=activity_name;
        this.activity_description=activity_description;
        this.activity_cost=activity_cost;
        this.activity_capacity=activity_capacity;
        this.activity_spaces_available=activity_spaces_available;
        this.destination_id = destination_id;
        this.passenger_id=passenger_id;
        this.passenger_type=passenger_type;
        myDB = new MyDatabaseHelper(context);
    }

    @NonNull
    @Override
    public CustomAdapterPassengerActivities.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_passenger_activities, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterPassengerActivities.MyViewHolder holder, int position) {
        //setting texts to views
        holder.tv_name.setText(String.valueOf(activity_name.get(position)));
        holder.tv_cost.setText(String.valueOf(activity_cost.get(position)));
        holder.tv_spaces.setText(String.valueOf(activity_spaces_available.get(position)));

        //checkbox listener
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //Checkbox is checked
                if(b){
                    //Getting the balance of passenger and cost of the acitivity, spaces ....
                    int balance=myDB.dbGetPassengerBalance(passenger_id);
                    int cost=Integer.parseInt(String.valueOf(activity_cost.get(position)));
                    int act_id=Integer.parseInt(String.valueOf(activity_id.get(position)));
                    int spaces=myDB.dbGetActivitySpaces(act_id);

                    //Changing the cost acc to passenger type
                    if(passenger_type=="gold") cost=(int)0.9*cost;
                    else if(passenger_type=="premium") cost=0;

                    //if don't have enough balance
                    if(balance<cost){
                        Toast.makeText(context,"Balance: "+balance+"\n Don't have enough Balance",
                                Toast.LENGTH_SHORT).show();
                        //Uncheck automatically
                        holder.cb.setChecked(false);
                    }
                    else if(spaces==0){ //If no spaces in the activity
                        Toast.makeText(context,"Space is Full!",
                                Toast.LENGTH_SHORT).show();
                        //Uncheck automatically
                        holder.cb.setChecked(false);
                    }
                    else{
                        //add the passenger in the activity
                        myDB.dbAddPassengerActivity(passenger_id, Integer.parseInt(String.valueOf(activity_id.get(position))));
                        //decrement spaces of the activity
                        myDB.dbDecrementActivitySpaces(Integer.parseInt(String.valueOf(activity_id.get(position))));
                        //Update the passenger's balance
                        myDB.dbUpdatePassengerBalance(passenger_id,passenger_type,Integer.parseInt(String.valueOf(activity_cost.get(position))),0);
//                        Toast.makeText(context,String.valueOf(activity_name.get(position)),
//                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Checkbox is unchecked
                    //delete the passenger from the activity
                    myDB.dbDeletePassengerActivity(passenger_id, Integer.parseInt(String.valueOf(activity_id.get(position))));
                    //Increment spaces of the activity
                    myDB.dbIncrementActivitySpaces(Integer.parseInt(String.valueOf(activity_id.get(position))));
                    //Update the passenger's balance
                    myDB.dbUpdatePassengerBalance(passenger_id,passenger_type,Integer.parseInt(String.valueOf(activity_cost.get(position))),1);
//                    Toast.makeText(context,String.valueOf(activity_name.get(position))+"unchecked",
//                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Returns number of activities in the package
    @Override
    public int getItemCount() {
        return activity_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name, tv_cost, tv_spaces;
        CheckBox cb;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_passenger_activity_name);
            tv_cost= itemView.findViewById(R.id.tv_passenger_activity_cost);
            tv_spaces= itemView.findViewById(R.id.tv_passenger_activity_spaces);
            cb = itemView.findViewById(R.id.tv_passenger_activity_checkbox);
        }
    }
}
