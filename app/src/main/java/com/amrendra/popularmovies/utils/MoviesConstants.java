package com.amrendra.popularmovies.utils;

/**
 * Created by Amrendra Kumar on 24/11/15.
 */
public class MoviesConstants {

    // base urls
    public static final String API_BASE_URL = "https://api.themoviedb.org";
    public static final String API_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185/";

    // end points urls
    public static final String GET_MOVIES_URL = "/3/discover/movie";

    // parameters
    public static final String API_KEY = "api_key";
    public static final String SORT_BY = "sort_by";
    public static final String PAGE = "page";


    // sorting
    public static final String SORT_BY_POPULARITY = "popularity.desc";
    public static final String SORT_BY_RATINGS = "vote_average.desc";
    public static final String SORT_BY_FAVOURITES = "show_favourites";
}
