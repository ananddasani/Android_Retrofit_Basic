package com.example.retrofitbasics.RetrofitWork;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceHolder {

    @GET("posts")
    Call<List<Post>> getPost();

    //this is interface so will not implement the method
    //Gson Converter will take care of it
}
