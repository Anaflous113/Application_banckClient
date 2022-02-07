package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import java.util.List;

import Model.AuthenticationDTO;
import Model.AuthenticationTokenDTO;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    Button login;
    EditText email,password;
    String emailclient;
    String passwordClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        login=findViewById(R.id.Login);
        email=(EditText)findViewById(R.id.Email);
        password=(EditText)findViewById(R.id.Password);

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://192.168.43.151:9000/client/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi =  retrofit.create(JsonPlaceHolderApi.class);
        emailclient=email.getText().toString();
        passwordClient = password.getText().toString();
        Log.i ("email : " ,"email"+emailclient);




        login.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Log.i ("email : " ,"email"+emailclient);
                Log.i ("email : " ,"email"+password.getText().toString());
                Call<AuthenticationTokenDTO> call1 = jsonPlaceHolderApi.postemail(new AuthenticationDTO(email.getText().toString(),password.getText().toString()));
                call1.enqueue(new Callback<AuthenticationTokenDTO>() {
                    @Override
                    public void onResponse(Call<AuthenticationTokenDTO> call, Response<AuthenticationTokenDTO> response) {

                        if(!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Mauvais mot de passe!", Toast.LENGTH_SHORT).show();


                            return;
                        }else{



                            Intent home = new Intent(MainActivity.this, Home.class);
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("Token", response.body().getToken());
                            editor.putInt("Id", response.body().getId());
                            editor.apply();

                            startActivity(home);
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticationTokenDTO> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Redirecting...",Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });



    }





}