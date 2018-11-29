package job4j.ru.retrofitexample.adapter;

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

    public CommentAdapter(List<Comment> dataList) {
        this.dataList = dataList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.info_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.txtComName.setText(dataList.get(position).getName());
        holder.txtComEmail.setText(dataList.get(position).getEmail());
        holder.txtComBody.setText(dataList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView txtComName, txtComEmail, txtComBody;

        CommentViewHolder(View itemView) {
            super(itemView);
            txtComName = (TextView) itemView.findViewById(R.id.txt_comment_name);
            txtComEmail = (TextView) itemView.findViewById(R.id.txt_comment_email);
            txtComBody = (TextView) itemView.findViewById(R.id.txt_comment_text);
        }
    }
}
