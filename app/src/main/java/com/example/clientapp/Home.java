package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import Model.Client;
import Model.Compte;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {
    private static final String MY_PREFS_NAME ="MyPrefsFile";
    private static final String MY_PREFS_NAME1 = "my_pref";
    TextView ln,fn,email,phone;
    TextView solde;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("Token", null);
        int idName = prefs.getInt("Id", 0);
        ln=findViewById(R.id.username);
        fn=findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phone=findViewById(R.id.tele);
        solde=findViewById(R.id.textView11);

        getSupportActionBar ().setTitle ("Home");
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://192.168.43.151:9000/client/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        JsonPlaceHolderApi jsonPlaceHolderApi =  retrofit.create(JsonPlaceHolderApi.class);


        Call<Client> call = jsonPlaceHolderApi.getclient("Bearer "+name, idName);
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if(!response.isSuccessful()){

                }
                Client client = response.body();

                String content="";

                ln.setText(client.getLastName());
                email.setText(client.getEmail());
                phone.setText(client.getPhoneNumber());
                fn.setText(client.getFirstName());

                for (Compte trsmodel : client.getCompte()) {
                    Log.i("yey", String.valueOf(trsmodel.getSolde()));
                     solde.setText( String.valueOf(trsmodel.getSolde())+"MAD");


                }
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME1, MODE_PRIVATE).edit();
                editor.putString("fn", client.getFirstName());
                editor.putString("ln", client.getLastName());
                editor.putString("pn", client.getPhoneNumber());

                editor.apply();




            }


            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        "Redirecting...",Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    public void ouvrir_home(View view) {
        Intent intentProfile = new Intent(this, Home.class);
        startActivity(intentProfile);
    }

    public void ouvrir_beneficiaire(View view){
        Intent intentProfile = new Intent(this,Beneficiaire.class);
        startActivity(intentProfile);
    }
    public void ouvrir_historique(View view){
        Intent intentProfile;
        intentProfile = new Intent(this,Historique.class);
        startActivity(intentProfile);
    }
    public void ouvrir_transfert(View view){
        Intent intentProfile = new Intent(this,Transfert.class);
        startActivity(intentProfile);
    }


}

