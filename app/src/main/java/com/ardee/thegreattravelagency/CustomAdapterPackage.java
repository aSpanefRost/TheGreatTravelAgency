package com.ardee.thegreattravelagency;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterPackage extends RecyclerView.Adapter<CustomAdapterPackage.MyViewHolder> {

    private Context context;
    private ArrayList package_id, package_name, package_capacity, package_spaces_available;

    CustomAdapterPackage(Context context, ArrayList package_id,
                         ArrayList package_name,
                         ArrayList package_capacity,
                         ArrayList package_spaces_available){
        this.context=context;
        this.package_id=package_id;
        this.package_name=package_name;
        this.package_capacity=package_capacity;
        this.package_spaces_available=package_spaces_available;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_package, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(String.valueOf(package_name.get(position)));
        holder.tv_spaces.setText(String.valueOf(package_spaces_available.get(position)));

        //On clicking on the package starting the PackageInformation activity
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, PackageInformation.class);

                //passing the package_name and package_id to the PackageInformation activity
                intent.putExtra("package_name",String.valueOf(package_name.get(position)));
                intent.putExtra("package_id",String.valueOf(package_id.get(position)));
                context.startActivity(intent);
            }
        });
    }

    //Number of Items
    @Override
    public int getItemCount() {
        return package_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_spaces;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_spaces = itemView.findViewById(R.id.tv_spaces);
            mainLayout= itemView.findViewById(R.id.id_linearlayout_package);
        }
    }
}
