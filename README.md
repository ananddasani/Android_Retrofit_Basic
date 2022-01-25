# Android_Retrofit_Basic
Simple GET request using Retrofit &amp; Fetching Data from API

# Dependency
```
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```

# Code

### Post.java
- Type of Model Class (The name should be exact of the API Data to be fetched
```
public class Post {

    private String userId, id, title, body;

    public Post(String userId, String id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
```

### JSONPlaceholder Inerface
- The Mehtod will be implemented by JsonConverter we don't have to implement the method
```
public interface JSONPlaceHolder {

    @GET("posts")
    Call<List<Post>> getPost();

    //this is interface so will not implement the method
    //Gson Converter will take care of it
}
```

#### MainActivity.java
```
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
```

# App Highlight

<img src="app_images/Retrofit Basic Code.png" /><br>

<img src="app_images/Retrofit Basic App1.png" width="300" /> <img src="app_images/Retrofit Basic App2.png" width="300" />
