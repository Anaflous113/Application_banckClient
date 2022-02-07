package com.example.clientapp;

import java.util.List;

import Model.Agent;
import Model.AuthenticationDTO;
import Model.AuthenticationTokenDTO;


import Model.Beneficière;
import Model.Client;
import Model.MultiTransfer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {



    @GET("findemail/{email}")
    Call<List<AuthenticationTokenDTO>> getToken();

    @POST("findemail/{email}")
    Call<AuthenticationTokenDTO> postemail(@Body AuthenticationDTO body);
    @GET("me")
    Call<Client> getclient(@Header("Authorization") String token,
                           @Header("id") Integer id);
    @GET("client")
    Call<List<MultiTransfer>> getMultitransfers(@Header("Authorization") String token,
                                                @Header("id") Integer id);

    @POST("beneficiaire")
    Call<Beneficière> postbenef(@Header("Authorization") String token,
                                @Header("id") Integer id , @Body Beneficière body);
    @POST("Transfer")
    Call<MultiTransfer> postTransfer(@Header("Authorization") String token,
                                     @Header("id") Integer id,@Body MultiTransfer body);
    @GET("beneficiaires")
    Call<List<Beneficière>> getlistbenef(@Header("Authorization") String token,
                                          @Header("id") Integer id);







}

