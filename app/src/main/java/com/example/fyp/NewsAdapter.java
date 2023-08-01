package com.example.fyp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context ctx;
    private List<News> newsList;

    public NewsAdapter(Context ctx, List<News> newss) {
        this.ctx = ctx;
        this.newsList = newss;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.item_news, null);
        return new NewsAdapter.NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {

        News news = newsList.get(position);
        holder.newsContent.setText(news.getContent());
        holder.newsDate.setText(news.getDate());

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView newsContent, newsDate;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            newsContent = itemView.findViewById(R.id.newsContent);
            newsDate = itemView.findViewById(R.id.newsDate);

        }
    }
}
