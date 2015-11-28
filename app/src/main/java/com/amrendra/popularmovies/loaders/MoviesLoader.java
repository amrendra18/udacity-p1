package com.amrendra.popularmovies.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.amrendra.popularmovies.BuildConfig;
import com.amrendra.popularmovies.http.MovieClientService;
import com.amrendra.popularmovies.logger.Debug;
import com.amrendra.popularmovies.model.Movie;
import com.amrendra.popularmovies.model.MovieList;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

/**
 * Created by Amrendra Kumar on 28/11/15.
 */
public class MoviesLoader extends AsyncTaskLoader<List<Movie>> {

    // Note: Be careful not to leak resources here


    private List<Movie> mData;

    public MoviesLoader(Context context) {
        super(context);
    }

    @Override
    public List<Movie> loadInBackground() {
        Call<MovieList> call = MovieClientService.getInstance().getMovieList(BuildConfig
                .THE_MOVIE_DB_API_KEY_TOKEN, "popularity.desc");
        Response<MovieList> response = null;
        List<Movie> data = null;
        try {
            response = call.execute();
            Debug.i(response.raw().toString(), false);
            MovieList movieList = response.body();
            data = movieList.results;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }


    @Override
    public void deliverResult(List<Movie> data) {
        if (isReset()) {
            releaseResources(data);
            return;
        }

        List<Movie> oldData = mData;
        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

        if (oldData != null && oldData != data) {
            releaseResources(oldData);
        }
    }

    private void releaseResources(List<Movie> oldData) {
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }

        if (takeContentChanged() || mData == null) {
            forceLoad();
        }
    }


    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if (mData != null) {
            releaseResources(mData);
            mData = null;
        }
    }

    @Override
    public void onCanceled(List<Movie> data) {
        super.onCanceled(data);
        releaseResources(data);
    }
}
