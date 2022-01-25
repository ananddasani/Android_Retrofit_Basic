package com.example.retrofitbasics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitbasics.RetrofitWork.JSONPlaceHolder;
import com.example.retrofitbasics.RetrofitWork.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    String url = "https://jsonplaceholder.typicode.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //crete a retrofit object
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JSONPlaceHolder jsonPlaceHolder = retrofit.create(JSONPlaceHolder.class);

                Call<List<Post>> call = jsonPlaceHolder.getPost();

                call.enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                        //check response is successful or not
                        if (!response.isSuccessful()) {
                            Log.d("retrofit", response.message());
                            Toast.makeText(MainActivity.this, "You're offline " + response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        button.setVisibility(View.GONE);
                        textView.setText("");

                        //collect it to the List<Post> (Our model class)
                        List<Post> postList = response.body();

                        for (int i = 0; i < postList.size(); i++) {
                            textView.append("id : " + postList.get(i).getId() + "\ntitle : " + postList.get(i).getTitle() + "\n\n\n");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Unable to fetch the data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}