package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Model.Beneficière;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Addbeneficiaire extends AppCompatActivity {
    private static final String MY_PREFS_NAME ="MyPrefsFile";

    Button add_data,add_bene ;
    EditText add_name ,gsm,nom,prenom,cin;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_addbeneficiaire);
        getSupportActionBar ().setTitle ("Add beneficiaire");
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("Token", null);
        int idName = prefs.getInt("Id", 0);



        add_bene = findViewById(R.id.valider12);

        gsm = findViewById (R.id.text21);
        nom = findViewById (R.id.text12);
        prenom = findViewById (R.id.text23);
        cin = findViewById (R.id.text22);
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://192.168.43.151:9000/api_client/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();





        JsonPlaceHolderApi jsonPlaceHolderApi =  retrofit.create(JsonPlaceHolderApi.class);
        add_bene.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Call<Beneficière> call = jsonPlaceHolderApi.postbenef(name,idName,new Beneficière(nom.getText().toString(),prenom.getText().toString(),cin.getText().toString(),gsm.getText().toString()));
                call.enqueue(new Callback<Beneficière>() {
                    @Override
                    public void onResponse(Call<Beneficière> call, Response<Beneficière> response) {

                        if(!response.isSuccessful()) {

                            Intent home = new Intent(Addbeneficiaire.this, Home.class);

                            startActivity(home);


                            return;
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Ajouté...", Toast.LENGTH_SHORT).show();

                            Intent home = new Intent(Addbeneficiaire.this, Home.class);

                            startActivity(home);

                        }
                    }

                    @Override
                    public void onFailure(Call<Beneficière> call, Throwable t) {
                        Intent home = new Intent(Addbeneficiaire.this, Home.class);
                        startActivity(home);

                    }
                });
                }

        });

    }


    }




