package com.example.clientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BeneficiaireAdapter extends RecyclerView.Adapter<BeneficiaireAdapter.MyViewHolder>{
    Context context;
    ArrayList name;



    public BeneficiaireAdapter(Beneficiaire context, ArrayList<String> name) {
        this.context = context;
        this.name = name;
    }

    @NonNull
    @Override
    public MyViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from (context);
        View view = inflater.inflate (R.layout.itembeneficiaire, parent, false);
        return new MyViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeneficiaireAdapter.MyViewHolder holder, int position) {
        holder.Name.setText(String.valueOf(name.get(position)));


    }

    @Override
    public int getItemCount() {
        return name == null ? 0 : name.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name;

        public MyViewHolder(@NonNull View itemView) {
            super (itemView);
            Name = itemView.findViewById (R.id.Number);

        }
    }
}
