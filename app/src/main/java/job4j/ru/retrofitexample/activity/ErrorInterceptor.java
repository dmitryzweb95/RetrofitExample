package job4j.ru.retrofitexample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import job4j.ru.retrofitexample.R;

/**
 * TODO: comment
 *
 * @author dmitryzweb
 * @since 26/11/2018
 */
public class ErrorInterceptor extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_interceptor);
    }
}
