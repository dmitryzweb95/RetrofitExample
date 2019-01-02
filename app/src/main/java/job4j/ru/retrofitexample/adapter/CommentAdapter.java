package job4j.ru.retrofitexample.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import job4j.ru.retrofitexample.R;
import job4j.ru.retrofitexample.model.Comment;

/**
 * TODO: comment
 *
 * @author dmitryzweb
 * @since 26/11/2018
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> dataList;

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.info_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * Method that set data
     *
     * @param dataList
     */
    public void setItems(List<Comment> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView name, email, body;

        CommentViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_comment_name);
            email = itemView.findViewById(R.id.txt_comment_email);
            body = itemView.findViewById(R.id.txt_comment_text);
        }

        /**
         * Method bind binding view and data from exam
         *
         * @param comment
         */
        private void bind(Comment comment) {
            name.setText(comment.getName());
            email.setText(comment.getEmail());
            body.setText(comment.getText());
        }
    }
}
