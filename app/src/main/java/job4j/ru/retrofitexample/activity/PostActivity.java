package job4j.ru.retrofitexample.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import job4j.ru.retrofitexample.BuildConfig;
import job4j.ru.retrofitexample.R;
import job4j.ru.retrofitexample.model.Post;
import job4j.ru.retrofitexample.network.GetPostDataService;
import okhttp3.Interceptor;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .client(clientErrorIntercept)//interceptor
                .build();

        //Buttons
        Button create = findViewById(R.id.create);
        Button edit = findViewById(R.id.edit);
        Button delete = findViewById(R.id.delete);

        //EditText
        EditText id = findViewById(R.id.editId);
        EditText userId = findViewById(R.id.editUserId);
        EditText title = findViewById(R.id.editTitle);
        EditText text = findViewById(R.id.editText);

        //Main text
        TextView result = findViewById(R.id.result);

        //Create resource (POST)
        create.setOnClickListener(v -> {
            //Converting id
            String userIdValue = userId.getText().toString();
            int finalUserIdValue = Integer.parseInt(userIdValue);

                    /*
        Realization of interface JsonPlaceHolderApi
         */
            GetPostDataService postDataService = retrofit.create(GetPostDataService.class);

            Call<Post> call = postDataService.createPost(finalUserIdValue, title.getText().toString(), text.getText().toString());
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
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
        });

        //Resource editing (PATCH)
        edit.setOnClickListener(v -> {
            //Converting userId
            String userIdValue = userId.getText().toString();
            int finalUserIdValue = Integer.parseInt(userIdValue);

            //Converting ID
            String idValue = id.getText().toString();
            int finalId = Integer.parseInt(idValue);

                    /*
        Realization of interface JsonPlaceHolderApi
         */
            GetPostDataService postDataService = retrofit.create(GetPostDataService.class);

            Call<Post> call = postDataService.patchPost(finalId, new Post(finalUserIdValue, title.getText().toString(), text.getText().toString()));
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
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
        });

        delete.setOnClickListener(v -> {

            //Converting ID
            String idValue = id.getText().toString();
            int finalId = Integer.parseInt(idValue);
                    /*
        Realization of interface JsonPlaceHolderApi
         */
            GetPostDataService postDataService = retrofit.create(GetPostDataService.class);

            Call<Void> call = postDataService.deletePost(finalId);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        result.setText(String.valueOf(response.code()));
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.getMessage();
                }
            });
        });

    }
}
