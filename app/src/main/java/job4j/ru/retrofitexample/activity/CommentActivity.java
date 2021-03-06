package job4j.ru.retrofitexample.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import job4j.ru.retrofitexample.R;
import job4j.ru.retrofitexample.adapter.CommentAdapter;
import job4j.ru.retrofitexample.model.Comment;
import job4j.ru.retrofitexample.network.GetCommentDataService;
import job4j.ru.retrofitexample.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * TODO: comment
 *
 * @author dmitryzweb
 * @since 26/11/2018
 */
public class CommentActivity extends AppCompatActivity {

    private CommentAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments);

        initRecyclerView();

        /*Create handle for the Retrofit interface*/
        GetCommentDataService service = RetrofitInstance.getRetrofitInstance().create(GetCommentDataService.class);

        /*Call the method with parameter in the interface to get the comment data*/
        Call<List<Comment>> call = service.getCommentData();

        /*Log the URL called*/
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(@NonNull Call<List<Comment>> call, @NonNull Response<List<Comment>> response) {
                setCommentList(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Comment>> call, @NonNull Throwable t) {
                Toast.makeText(CommentActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method that initialises recycler
     */
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_comment_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
        adapter = new CommentAdapter();
        recyclerView.setAdapter(adapter);
    }

    /**
     * Method to generate List of Comments using RecyclerView with custom adapter
     *
     * @param comDataList list of comments
     */
    private void setCommentList(List<Comment> comDataList) {
        adapter.setItems(comDataList);
    }
}