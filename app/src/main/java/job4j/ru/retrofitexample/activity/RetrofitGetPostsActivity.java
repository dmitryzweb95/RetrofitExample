package job4j.ru.retrofitexample.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import job4j.ru.retrofitexample.R;
import job4j.ru.retrofitexample.model.Post;
import job4j.ru.retrofitexample.network.JsonPlaceHolderApi;
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
public class RetrofitGetPostsActivity extends AppCompatActivity {
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.posts_text);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /*
        Realization of interface JsonPlaceHolderApi
         */
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Post> call = jsonPlaceHolderApi.getPost(1);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    result.setText(String.format("Code: %s", response.code()));
                    return;
                }
                Post post = response.body();
                String content = String.format(
                        "ID: %s \n user ID: %s \n Title: %s \n Text: %s \n\n",
                        post.getId(), post.getUserId(), post.getTitle(), post.getText()
                );
                result.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });

//        call.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                if (!response.isSuccessful()) {
//                    result.setText(String.format("Code: %s", response.code()));
//                    return;
//                }
//                List<Post> posts = response.body();
//                for (Post post : posts) {
//                    String content = String.format(
//                            "ID: %s \n user ID: %s \n Title: %s \n Text: %s \n\n",
//                            post.getId(), post.getUserId(), post.getTitle(), post.getText()
//                    );
//                    result.append(content);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                result.setText(t.getMessage());
//            }
//        });
    }
}
