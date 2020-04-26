package com.sankalp.newsapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sankalp.newsapp.R;
import com.sankalp.newsapp.model.Article;
import com.sankalp.newsapp.utils.Constants;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.CustomViewHolder> {
    private ArrayList<Article> data;
    private int position;
    private Constants.ListType _listType;

    public NewsFeedAdapter( Constants.ListType listType) {
        this.data = new ArrayList<>();
        _listType = listType;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        private ImageView coverImage;
        CustomViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.title);
            coverImage = itemView.findViewById(R.id.coverImage);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("onCreateViewHolder", "onCreateViewHolder");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (_listType.equals(Constants.ListType.HomeScreen)) {
            view = layoutInflater.inflate(R.layout.card_trending_news, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.card_news, parent, false);
        }
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        holder.txtTitle.setText(data.get(position).getTitle());

        if (!TextUtils.isEmpty(data.get(position).getUrlToImage() )) {
            Picasso.get().load(data.get(position).getUrlToImage())
                    .placeholder(R.drawable.placeholder).into(holder.coverImage);
        }
        holder.itemView.setOnLongClickListener(view -> {
            setPosition(holder.getAdapterPosition());
            return false;
        });
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<Article> newData) {
        data.clear();
        data.addAll(newData);
    }
}