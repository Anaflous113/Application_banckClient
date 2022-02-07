package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.Beneficière;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Beneficiaire extends AppCompatActivity {
    RecyclerView taskRecycleView;

    ArrayList<String> name ;
    BeneficiaireAdapter beneficiaireAdapter;
    private SearchView searchView;
    List<Addbeneficiaire> itemList;
    private Object String;
    private static final String MY_PREFS_NAME ="MyPrefsFile";

    Button add_data,add_bene ;
    EditText add_name ,gsm,nom,prenom,cin;
    TextView numGsm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_beneficiaire);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("Token", null);
        int idName = prefs.getInt("Id", 0);



        add_bene = findViewById(R.id.valider12);

        gsm = findViewById (R.id.text21);
        nom = findViewById (R.id.text12);
        prenom = findViewById (R.id.text23);
        cin = findViewById (R.id.text22);
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://192.168.43.151:9000/client/")
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

                            Intent home = new Intent(Beneficiaire.this, Home.class);

                            startActivity(home);


                            return;
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Ajouté...", Toast.LENGTH_SHORT).show();

                            Intent home = new Intent(Beneficiaire.this, Home.class);

                            startActivity(home);

                        }
                    }

                    @Override
                    public void onFailure(Call<Beneficière> call, Throwable t) {
                        Intent home = new Intent(Beneficiaire.this, Home.class);
                        startActivity(home);

                    }
                });
            }

        });
}
}