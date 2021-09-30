package com.ardee.thegreattravelagency;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterDestination extends RecyclerView.Adapter<CustomAdapterDestination.MyViewHolder>{

    private Context context;
    private ArrayList destination_id, destination_name, package_id;
    String pkg_id,pkg_name;

    //Constructor
    CustomAdapterDestination(Context context,
                             ArrayList destination_id,
                             ArrayList destination_name,
                             ArrayList package_id,
                             String pkg_id,
                             String pkg_name){
        this.context=context;
        this.destination_id=destination_id;
        this.destination_name=destination_name;
        this.package_id=package_id;
        this.pkg_id=pkg_id;
        this.pkg_name=pkg_name;
    }


    @NonNull
    @Override
    public CustomAdapterDestination.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_destination, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterDestination.MyViewHolder holder, int position) {
        holder.tv_name.setText(String.valueOf(destination_name.get(position)));
        //on clicking on a destination opens up the Activities
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, Activities.class);
                //passing the destination_name, destination_id, package_id, package_name to
                //Activities activity
                intent.putExtra("destination_name",String.valueOf(destination_name.get(position)));
                intent.putExtra("destination_id",String.valueOf(destination_id.get(position)));
                intent.putExtra("package_id",pkg_id);
                intent.putExtra("package_name",pkg_name);
                context.startActivity(intent);
            }
        });
    }

    //returns number of destinations in the package
    @Override
    public int getItemCount() {
        return destination_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_destination_name);
            mainLayout= itemView.findViewById(R.id.id_linearlayout_destination);
        }
    }
}
