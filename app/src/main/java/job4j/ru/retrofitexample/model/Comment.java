package job4j.ru.retrofitexample.model;

import com.google.gson.annotations.SerializedName;

/**
 * TODO: comment
 *
 * @author dmitryzweb
 * @since 26/11/2018
 */
public class Comment {

    private int postId;
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("body")
    private String text;

    public Comment(int postId, int id, String name, String email, String text) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.text = text;
    }

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }
}
