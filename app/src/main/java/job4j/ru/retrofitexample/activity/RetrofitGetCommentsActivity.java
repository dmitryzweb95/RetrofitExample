package job4j.ru.retrofitexample.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import job4j.ru.retrofitexample.R;
import job4j.ru.retrofitexample.model.Comment;
import job4j.ru.retrofitexample.service.JsonPlaceHolderApi;
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
public class RetrofitGetCommentsActivity extends AppCompatActivity {
    private TextView result;
    private List<Comment> comments = getJsonData();
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments);

        getJsonData();

        this.recycler = findViewById(R.id.comments);
        this.recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        updateUI();
    }

    /**
     * Receive data from json
     */
    private List<Comment> getJsonData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

                /*
        Realization of interface JsonPlaceHolderApi
         */
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Comment>> call = jsonPlaceHolderApi.getComments();


        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                comments.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                t.getMessage();
            }
        });
        return comments;
    }

    public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {
        private final List<Comment> comments;

        public CommentAdapter(List<Comment> comments) {
            this.comments = comments;
        }

        @NonNull
        @Override
        public CommentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.info_comment, viewGroup, false);
            return new CommentHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentHolder commentHolder, int position) {
            Comment comment = this.comments.get(position);

            TextView name = findViewById(R.id.name);
            TextView email = findViewById(R.id.email);
            TextView text = findViewById(R.id.text);

            name.setText(comment.getName());
            email.setText(comment.getEmail());
            text.setText(comment.getText());
        }

        @Override
        public int getItemCount() {
            return this.comments.size();
        }
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        private View view;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    private void updateUI() {
        this.recycler.setAdapter(new CommentAdapter(comments));
    }
}
