package com.example.borisova_animals;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("Animals")
    Call<Animal> createPost(@Body Animal dataModal);
    @PUT("Animals")
    Call<Animal> createPut( @Body Animal dataModal, @Query("ID")int id);
    @DELETE("Animals")
    Call<Animal> createDelete(@Query("id") int id);
}

