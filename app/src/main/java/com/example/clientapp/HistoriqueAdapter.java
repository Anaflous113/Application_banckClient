package com.example.clientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Model.Transfer;

public class HistoriqueAdapter extends RecyclerView.Adapter<HistoriqueAdapter.ViewHolder>{
    Context context;

    List<Transfer> payment_list;


    public HistoriqueAdapter(List<Transfer> arrayList2) {
        this.payment_list=arrayList2;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from (parent.getContext());
        View view = inflater.inflate (R.layout.itemhistory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(payment_list!=null && payment_list.size()>0){
        Transfer model =payment_list.get(position);
        holder.Number.setText(model.getTransfer_reference());
        holder.Etat.setText(String.valueOf(model.getTransfer_amount()));}
        else{
            return;
        }

    }

    @Override
    public int getItemCount() {
        return payment_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Number, Etat;

        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            Number = itemView.findViewById(R.id.Number);
            Etat = itemView.findViewById(R.id.Etat);
        }
    }
}
