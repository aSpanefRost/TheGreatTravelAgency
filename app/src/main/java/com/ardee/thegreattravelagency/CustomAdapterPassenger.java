package com.ardee.thegreattravelagency;

import static android.content.ContentValues.TAG;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class CustomAdapterPassenger extends RecyclerView.Adapter<CustomAdapterPassenger.MyViewHolder> {

    private Context context;
    private ArrayList passenger_id, passenger_name, passenger_type,
            passenger_balance, package_id;
    String str;
    private ArrayList<String> activity_id, destination_id;
    MyDatabaseHelper myDB;

   //Constructor
    CustomAdapterPassenger(Context context, ArrayList passenger_id,
                           ArrayList passenger_name,ArrayList passenger_type,
                           ArrayList passenger_balance,ArrayList package_id){
        this.context=context;
        this.passenger_id=passenger_id;
        this.passenger_name=passenger_name;
        this.passenger_type=passenger_type;
        this.passenger_balance=passenger_balance;
        this.package_id=package_id;
        myDB = new MyDatabaseHelper(context);
        activity_id = new ArrayList<>();
        destination_id = new ArrayList<>();
    }

    @NonNull
    @Override
    public CustomAdapterPassenger.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_passenger, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterPassenger.MyViewHolder holder, int position) {

        //setting texts of different views
        holder.tv_name.setText(String.valueOf(passenger_name.get(position)));
        holder.tv_type.setText(String.valueOf(passenger_type.get(position)));
        holder.tv_balance.setText(String.valueOf(passenger_balance.get(position)));

        //On click on a Passenger, download the details of the Passenger
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str="";
                str+="Passenger Name: ";
                str+=String.valueOf(passenger_name.get(position))+"\n";
                str+="Passenger Number: ";
                str+=String.valueOf(passenger_id.get(position))+"\n";
                str+="Passenger Balance: ";
                str+=String.valueOf(passenger_balance.get(position))+"\n";
                str+="Passenger Type: ";
                str+=String.valueOf(passenger_type.get(position))+"\n\n";
                activity_id.clear();

                //getting all the passenger activity datas
                storeActivityDataInArrays(String.valueOf(passenger_id.get(position)));

                //saving the activity datas in the str string
                help(position);

                //Log.d("text is here",str);
                File file = new File(context.getFilesDir(), "text");
                if (!file.exists()) {
                    file.mkdir();
                }
                try {
                    File gpxfile = new File(file, passenger_name.get(position)+"_details");
                    FileWriter writer = new FileWriter(gpxfile);
                    writer.append(str);
                    writer.flush();
                    writer.close();
                    Toast.makeText(context, "Downloaded Details", Toast.LENGTH_LONG).show();
                } catch (Exception e) { }

            }
        });
    }

    //getting all the activity datas from database
    private void storeActivityDataInArrays(String id) {
        Cursor cursor = myDB.readAllPassengerActivityData(Integer.parseInt(id));
        if(cursor.getCount()==0) {
            Log.d(TAG, "Could not find any activity "+id);
        }else{
            while(cursor.moveToNext()){
                activity_id.add(cursor.getString(2));
            }
            Log.d(TAG, "size of activity "+activity_id.size());
        }
    }


   //saving activity datas of the passenger into str
    private void help(int position){
        str+="(Activity Name,Destination,Price Paid)\n";
        for(int i=0;i<activity_id.size();i++){
            int cost=Integer.parseInt(myDB.getActivityCostById(activity_id.get(i)));
            String ps_type=String.valueOf(passenger_type.get(position));
            Log.d(TAG,String.valueOf(passenger_type.get(position)));
            Log.d(TAG,"cost: "+cost);
            if(ps_type.equals("gold")) cost=(int)(0.9*(double)cost);
            else if(ps_type.equals("premium")) cost=0;
            else Log.d(TAG,"NOTHING");
            str+="-->("+myDB.getActivityNameById(activity_id.get(i))+"," +
                    myDB.getDestinationNameById(activity_id.get(i))+","+
                    cost+")"+"\n";
        }
    }

    //returns the number of passenger
    @Override
    public int getItemCount() {
        return passenger_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name, tv_type, tv_balance;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_passenger_name);
            tv_type = itemView.findViewById(R.id.tv_passenger_type);
            tv_balance = itemView.findViewById(R.id.tv_passenger_balance);
            mainLayout= itemView.findViewById(R.id.id_linearlayout_passenger);
        }
    }


}
