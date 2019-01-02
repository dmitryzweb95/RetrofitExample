package job4j.ru.retrofitexample.model;

import com.google.gson.annotations.SerializedName;

/**
 * TODO: comment
 *
 * @author dmitryzweb
 * @since 26/11/2018
 */
public class Post {
    private int userId;
    private Integer id;
    private String title;

    public Post(int userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    @SerializedName("body")
    private String text;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
