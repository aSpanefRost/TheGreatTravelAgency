package com.ardee.thegreattravelagency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterActivity extends RecyclerView.Adapter<CustomAdapterActivity.MyViewHolder>{

    private Context context;
    private ArrayList activity_id, activity_name, activity_description, activity_cost, activity_capacity,
            activity_spaces_available, destination_id;

    //Constructor
    CustomAdapterActivity(Context context,
                          ArrayList activity_id,
                          ArrayList activity_name,
                          ArrayList activity_description,
                          ArrayList activity_cost,
                          ArrayList activity_capacity,
                          ArrayList activity_spaces_available,
                          ArrayList destination_id){
        this.context=context;
        this.activity_id=activity_id;
        this.activity_name=activity_name;
        this.activity_description=activity_description;
        this.activity_cost=activity_cost;
        this.activity_capacity=activity_capacity;
        this.activity_spaces_available=activity_spaces_available;
        this.destination_id = destination_id;
    }

    @NonNull
    @Override
    public CustomAdapterActivity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_activity, parent, false);
        return new MyViewHolder(view);
    }

    //setting the texts of the views
    @Override
    public void onBindViewHolder(@NonNull CustomAdapterActivity.MyViewHolder holder, int position) {
        holder.tv_name.setText(String.valueOf(activity_name.get(position)));
        holder.tv_description.setText(String.valueOf(activity_description.get(position)));
        holder.tv_cost.setText(String.valueOf(activity_cost.get(position)));
        holder.tv_spaces.setText(String.valueOf(activity_spaces_available.get(position)));
    }

    //returns the number of activities
    @Override
    public int getItemCount() {
        return activity_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_description, tv_cost, tv_spaces;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_activity_name);
            tv_description=itemView.findViewById(R.id.tv_activity_description);
            tv_cost = itemView.findViewById(R.id.tv_activity_cost);
            tv_spaces = itemView.findViewById(R.id.tv_activity_spaces);
        }
    }
}
