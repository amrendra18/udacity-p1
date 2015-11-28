package com.amrendra.popularmovies.app.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amrendra.popularmovies.BuildConfig;
import com.amrendra.popularmovies.R;
import com.amrendra.popularmovies.http.MovieClientService;
import com.amrendra.popularmovies.logger.Debug;
import com.amrendra.popularmovies.model.Movie;
import com.amrendra.popularmovies.model.MovieList;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends AppCompatActivity implements Callback<MovieList> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Debug.e(BuildConfig.THE_MOVIE_DB_API_KEY_TOKEN, false);
    }

    @Override
    protected void onResume() {
        super.onResume();


        Debug.c();

        final Call<MovieList> call = MovieClientService.getInstance().getMovieList(BuildConfig
                .THE_MOVIE_DB_API_KEY_TOKEN, "popularity.desc");
        //asynchronous call

        new Thread() {
            @Override
            public void run() {
                try {
                    Response<MovieList> response = call.execute();
                    Debug.i(response.raw().toString(), false);
                    MovieList movieList = response.body();
                    for(Movie movie: movieList.results) {
                        Debug.i(movie.toString(), false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }

    @Override
    public void onResponse(Response<MovieList> response, Retrofit retrofit) {
        Debug.c();
    }

    @Override
    public void onFailure(Throwable t) {
        Debug.c();
    }
}
