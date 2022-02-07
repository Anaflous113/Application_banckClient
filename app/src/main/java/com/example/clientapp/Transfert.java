package com.example.clientapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.Beneficière;
import Model.MultiTransfer;
import Model.Transfer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transfert extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final String MY_PREFS_NAME1 = "my_pref";
    ArrayList<Beneficière> arrayList1 = new ArrayList<>();
    SpinnerAdapter adapters;
    EditText montant,motif;
    Button send;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_transfert);
        getSupportActionBar ().setTitle ("Transfert");
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("Token", null);
        int idName = prefs.getInt("Id", 0);
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://192.168.43.151:9000/client/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Spinner spinner = findViewById (R.id.spinner1);
        montant=findViewById(R.id.Montant1);
        motif=findViewById(R.id.Motif1);
        txt=findViewById(R.id.textView100);
        send=findViewById(R.id.send);

        SharedPreferences prefs1 = getSharedPreferences(MY_PREFS_NAME1, MODE_PRIVATE);
        String fn = prefs1.getString("fn", null);//"No name defined" is the default value.
        String ln = prefs1.getString("ln", null);
        String pn = prefs1.getString("pn", null);
/*
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource (this , R.array.motif, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter (adapter);
        spinner.setOnItemSelectedListener (this);
*/
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Beneficière>> call = jsonPlaceHolderApi.getlistbenef("Bearer " + name, idName);
        call.enqueue(new Callback<List<Beneficière>>() {
            @Override
            public void onResponse(Call<List<Beneficière>> call, Response<List<Beneficière>> response) {
                if (!response.isSuccessful()) {

                }

                arrayList1 = new ArrayList<>();



                List<Beneficière> benef = response.body();
                for (Beneficière benefmodel : benef) {
                    Log.i("f", benefmodel.getAccount_number());
                    arrayList1.add(new Beneficière(benefmodel.getAccount_number(), benefmodel.getFirstName(), benefmodel.getLastName(),benefmodel.getPhoneNumber()));
                    Log.i("thissss", String.valueOf(arrayList1));


                }

                adapters = new SpinnerAdapter(getApplicationContext(), R.layout.row, arrayList1);
                spinner.setAdapter(adapters);

            }

            @Override
            public void onFailure(Call<List<Beneficière>> call, Throwable t) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Beneficière myModel = (Beneficière) parent.getSelectedItem();
                Log.e("dddDATA", myModel.getAccount_number());
                Log.e("dddDATA", myModel.getLastName());
                Log.i("ddddDATA", String.valueOf(position));


                txt.setText(myModel.getLastName()+ "-" +myModel.getFirstName() + "-" + myModel.getAccount_number() + "-" + myModel.getPhoneNumber());



                    }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                List<Transfer> l1 = new ArrayList<>();

                                String[] arrSplit = txt.getText().toString().split("-");

                                Log.i("bla11", arrSplit[0]);
                                Log.i("bla11", arrSplit[1]);
                                Log.i("bla11", arrSplit[2]);
                                Log.i("bla11", arrSplit[3]);

                                int l = Integer.parseInt(montant.getText().toString());

                                Transfer b = new Transfer(Float.parseFloat(montant.getText().toString()), 1, arrSplit[1], arrSplit[0], arrSplit[3]);
                                l1.add(b);
                                Log.i("bla", String.valueOf(l1));

                                Call<MultiTransfer> call2 = jsonPlaceHolderApi.postTransfer(name, idName, new MultiTransfer(idName, fn, ln, pn, Float.parseFloat(montant.getText().toString()), 30.5F, 2, motif.getText().toString(), true, l1));
                                call2.enqueue(new Callback<MultiTransfer>() {
                                    @Override
                                    public void onResponse(Call<MultiTransfer> call, Response<MultiTransfer> response) {

                                        if (!response.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Transfer has been done.", Toast.LENGTH_SHORT).show();


                                            return;
                                        }


                                        Toast.makeText(getApplicationContext(),
                                                "Transfer has been done.", Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onFailure(Call<MultiTransfer> call, Throwable t) {


                                    }
                                });
                                Intent home = new Intent(Transfert.this, Home.class);
                                startActivity(home);
                            }


        });

    }
    public void verifier_otp(View view){
        Intent intentProfile = new Intent(this,otp.class);
        startActivity(intentProfile);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition (position).toString ();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}