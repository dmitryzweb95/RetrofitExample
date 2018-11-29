package job4j.ru.retrofitexample.network;

import java.util.List;

import job4j.ru.retrofitexample.model.Comment;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetCommentDataService {
    @GET("comments")
    Call<List<Comment>> getCommentData();
}
