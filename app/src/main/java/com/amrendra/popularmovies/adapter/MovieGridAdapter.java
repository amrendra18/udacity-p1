package com.amrendra.popularmovies.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amrendra.popularmovies.R;
import com.amrendra.popularmovies.logger.Debug;
import com.amrendra.popularmovies.model.Movie;
import com.amrendra.popularmovies.utils.MoviesConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Amrendra Kumar on 28/11/15.
 */
public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.ViewHolder> {

    private List<Movie> movieList = new ArrayList<>();


    public MovieGridAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_column_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        holder.gridMovieNameTv.setText(movie.title);
        String imageUrl = MoviesConstants.API_IMAGE_BASE_URL + movie.posterPath;

        holder.ratingBar.setRating(((float) movie.averageVote) / 2.0f);

        Picasso.with(holder.gridMoviePosterImage.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.place_holder)
                        //.error(R.drawable.user_placeholder_error)
                .into(holder.gridMoviePosterImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Debug.showToastShort("" + movie.title + " clicked", holder.gridMoviePosterImage.getContext());
            }
        });

    }

    @Override
    public int getItemCount() {
        return ((movieList == null) ? (0) : (movieList.size()));
    }

    public void resetMovieList(List<Movie> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        movieList = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.grid_item_movie_poster_image)
        ImageView gridMoviePosterImage;

        @Bind(R.id.grid_item_movie_name_text_view)
        TextView gridMovieNameTv;

        @Bind(R.id.rating)
        RatingBar ratingBar;

        @Bind(R.id.grid_item_container)
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
