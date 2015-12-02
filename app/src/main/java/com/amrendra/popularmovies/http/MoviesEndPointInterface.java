package com.amrendra.popularmovies.http;

import com.amrendra.popularmovies.model.MovieList;
import com.amrendra.popularmovies.utils.MoviesConstants;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Amrendra Kumar on 24/11/15.
 */
public interface MoviesEndPointInterface {

    @GET(MoviesConstants.GET_MOVIES_URL)
    Call<MovieList> getMovieList(@Query(MoviesConstants.API_KEY) String apiKey,
                                 @Query(MoviesConstants.SORT_BY) String sortBy, @Query(MoviesConstants.PAGE) int
                                         page, @Query(MoviesConstants.VOTE_COUNT_GTE) int vote_count);
}
