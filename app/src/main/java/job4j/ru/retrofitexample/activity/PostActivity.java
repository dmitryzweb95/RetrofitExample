package job4j.ru.retrofitexample.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import job4j.ru.retrofitexample.BuildConfig;
import job4j.ru.retrofitexample.R;
import job4j.ru.retrofitexample.model.Post;
import job4j.ru.retrofitexample.network.GetPostDataService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * TODO: comment
 *
 * @author dmitryzweb
 * @since 26/11/2018
 */
public class PostActivity extends AppCompatActivity {
    //Buttons
    private Button create;
    private Button edit;
    private Button delete;

    //EditTexts
    private EditText id;
    private EditText userId;
    private EditText title;
    private EditText text;

    //TextViews
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        //Buttons
        create = findViewById(R.id.create);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);

        //EditText
        id = findViewById(R.id.editId);
        userId = findViewById(R.id.editUserId);
        title = findViewById(R.id.editTitle);
        text = findViewById(R.id.editText);

        //Main text
        result = findViewById(R.id.result);

        //Create resource (POST)
        create.setOnClickListener(v -> {
            this.create();
        });

        //Resource editing (PATCH)
        edit.setOnClickListener(v -> {
            this.edit();
        });

        //Resource deleting (DELETE)
        delete.setOnClickListener(v -> {
            this.delete();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Create post
     */
    private void create() {
        //Converting id
        String userIdValue = userId.getText().toString();
        if (userIdValue.isEmpty()) {
            Toast.makeText(this, "Not enough data", Toast.LENGTH_SHORT).show();
            return;
        }
        int finalUserIdValue = Integer.parseInt(userIdValue);

                    /*
        Realization of interface JsonPlaceHolderApi
         */
        GetPostDataService postDataService = getRetrofitSettings().create(GetPostDataService.class);

        Call<Post> call = postDataService.createPost(finalUserIdValue, title.getText().toString(), text.getText().toString());
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.isSuccessful()) {
                    Post postResponse = response.body();
                    String content = String.format(
                            "ID: %s \n user ID: %s \n Title: %s \n Text: %s \n\n",
                            postResponse.getId(), postResponse.getUserId(),
                            postResponse.getTitle(), postResponse.getText()
                    );
                    result.setText(content);
                } else {
                    Toast.makeText(PostActivity.this, String.format("Error, response status: %s", response.code()),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(PostActivity.this, "Connecting Error",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PostActivity.this, "Conversion Error",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Edit post
     */
    private void edit() {
        //Converting userId
        String userIdValue = userId.getText().toString();
        if (userIdValue.isEmpty()) {
            Toast.makeText(this, "Not enough data", Toast.LENGTH_SHORT).show();
            return;
        }
        int finalUserIdValue = Integer.parseInt(userIdValue);

        //Converting ID
        String idValue = id.getText().toString();
        int finalId = Integer.parseInt(idValue);

                    /*
        Realization of interface JsonPlaceHolderApi
         */
        GetPostDataService postDataService = getRetrofitSettings().create(GetPostDataService.class);

        Call<Post> call = postDataService.patchPost(finalId, new Post(finalUserIdValue, title.getText().toString(), text.getText().toString()));
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if (response.isSuccessful()) {
                    Post postResponse = response.body();
                    String content = String.format(
                            "ID: %s \n user ID: %s \n Title: %s \n Text: %s \n\n",
                            postResponse.getId(), postResponse.getUserId(),
                            postResponse.getTitle(), postResponse.getText()
                    );
                    result.setText(content);
                } else {
                    Toast.makeText(PostActivity.this, String.format("Error, response status: %s", response.code()),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(PostActivity.this, "Connecting Error %s",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PostActivity.this, "Conversion Error %s",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Delete post
     */
    private void delete() {
        //Converting ID
        String idValue = id.getText().toString();
        if (idValue.isEmpty()) {
            Toast.makeText(this, "Not enough data", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            int finalId = Integer.parseInt(idValue);

            GetPostDataService postDataService = getRetrofitSettings().create(GetPostDataService.class);

            Call<Void> call = postDataService.deletePost(finalId);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        result.setText(String.valueOf(response.code()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    t.getMessage();
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method that takes retrofit settings with interceptor
     *
     * @return retrofit
     */
    private Retrofit getRetrofitSettings() {
        //Interceptor
        OkHttpClient clientErrorIntercept = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    okhttp3.Response response = chain.proceed(request);

                    if (response.code() >= 400 && response.code() <= 599) {
                        Intent intent = new Intent(PostActivity.this, ErrorInterceptor.class);
                        startActivity(intent);
                        return response;
                    }
                    return response;
                })
                .build();

        //HTTP3 logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        //Set level
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
        client.addInterceptor(interceptor);

        return new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .client(clientErrorIntercept)//interceptor
                .build();
    }
}
