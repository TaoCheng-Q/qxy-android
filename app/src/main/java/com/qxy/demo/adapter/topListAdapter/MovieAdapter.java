package com.qxy.demo.adapter.topListAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qxy.demo.R;
import com.qxy.demo.room.entity.topListEntity.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context context){
        this.context = context;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MovieHolder) holder).binding(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList==null?0:movieList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{
        public ImageView movie_cover;
        public TextView
                movie_actor,
                movie_director,
                movie_name,
                movie_discuss_pop,
                movie_rel_area,
                movie_influence_pop,
                movie_rel_time,
                movie_theme_pop,
                movie_search_pop;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            movie_cover = itemView.findViewById(R.id.movie_cover);
            movie_actor = itemView.findViewById(R.id.movie_actor);
            movie_director = itemView.findViewById(R.id.movie_director);
            movie_name= itemView.findViewById(R.id.movie_name);
            movie_discuss_pop=itemView.findViewById(R.id.movie_discuss_pop);
            movie_influence_pop=itemView.findViewById(R.id.movie_influence_pop);
            movie_rel_area=itemView.findViewById(R.id.movie_rel_area);
            movie_rel_time=itemView.findViewById(R.id.movie_rel_time);
            movie_theme_pop=itemView.findViewById(R.id.movie_theme_pop);
            movie_search_pop = itemView.findViewById(R.id.movie_search_pop);

        }

        public void binding(Movie movie){
            movie_actor.setText(movie.getActors());
            movie_director.setText(movie.getDirectors());
            movie_name.setText(movie.getName());
            movie_discuss_pop.setText(movie.getDiscussion_hot());
            movie_rel_area.setText(movie.getAreas());
            movie_influence_pop.setText(movie.getInfluence_hot());
            movie_rel_time.setText(movie.getRelease_date());
            movie_theme_pop.setText(movie.getTopic_hot());
            movie_search_pop.setText(movie.getSearch_hot());
            Glide
                    .with(itemView.getContext())
                    .load(movie.getPoster())
                    .into(movie_cover);

        }
    }
}
