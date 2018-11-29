package job4j.ru.retrofitexample.network;

import java.util.List;
import java.util.Map;

import job4j.ru.retrofitexample.model.Comment;
import job4j.ru.retrofitexample.model.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * TODO: comment
 *
 * @author dmitryzweb
 * @since 26/11/2018
 */
public interface JsonPlaceHolderApi {
    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}")
    Call<Post> getPost(@Path("id") int postId);

    @GET("posts")
    Call<Post> getQueryMapPost(@QueryMap Map<String, String> parameters);

}
