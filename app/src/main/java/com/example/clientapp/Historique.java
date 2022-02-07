package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.MultiTransfer;
import Model.Transfer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Historique extends AppCompatActivity {
    RecyclerView historyRecycleView;
    ArrayList<String> number ;
    ArrayList<String> etat;
    HistoriqueAdapter  historiqueAdapter;
    List<Transfer> arrayList2= new ArrayList<>();

    List<Transfer> payment_list;
    private static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_historique);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("Token", null);//"No name defined" is the default value.
        int idName = prefs.getInt("Id", 0);
        historyRecycleView = findViewById(R.id.historyrecycle);

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://192.168.43.151:9000/client/MultiTransfers/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi =  retrofit.create(JsonPlaceHolderApi.class);
        Call<List<MultiTransfer>> call1 = jsonPlaceHolderApi.getMultitransfers(name,idName);
        call1.enqueue(new Callback<List<MultiTransfer>>() {
            @Override
            public void onResponse(Call<List<MultiTransfer>> call, Response<List<MultiTransfer>> response) {
                if (!response.isSuccessful()) {

                    return;
                }



                List<MultiTransfer> benef = response.body();

                for (MultiTransfer benefmodel : benef) {
                    Log.i("fddd", benefmodel.getSender_fname());
                    payment_list = benefmodel.getTransfers();
                    for (Transfer trsmodel : benefmodel.getTransfers()) {
                        if(trsmodel.getTransfer_status()==1){
                            arrayList2.add(new Transfer("REF:"+trsmodel.getTransfer_reference()+",Somme:", trsmodel.getTransfer_amount()));
                        }

                    }





                    setRecyclerView(arrayList2);




                }}
            @Override
            public void onFailure(Call<List<MultiTransfer>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
                Toast.makeText(getApplicationContext(),
                        "Redirecting...",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setRecyclerView(List<Transfer> a) {
        historyRecycleView.setHasFixedSize(true);
        historyRecycleView.setLayoutManager(new LinearLayoutManager(Historique.this));
        historiqueAdapter = new HistoriqueAdapter(a);
        historyRecycleView.setAdapter(historiqueAdapter);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }


    public void ouvrir_detaille(View view){
        Intent intentProfile = new Intent(this,detail_historique.class);
        startActivity(intentProfile);
    }

}
