package com.amrendra.popularmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Amrendra Kumar on 24/11/15.
 */
public class Movie {

    @SerializedName("id")
    public long id;
    @SerializedName("vote_count")
    public long votesCount;

    @SerializedName("title")
    public String title;
    @SerializedName("overview")
    public String overview;
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("backdrop_path")
    public String backdropPath;
    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("popularity")
    public double popularity;
    @SerializedName("vote_average")
    public double averageVote;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Movie{\n");
        sb.append("[id=" + id + "]");
        sb.append("[title=" + title + "]");
        sb.append("[vote_count=" + votesCount + "]");
        sb.append("[release_date=" + releaseDate + "]");
        sb.append("[popularity=" + popularity + "]");
        sb.append("[vote_average=" + averageVote + "]");
        sb.append("[backdrop_path=" + backdropPath + "]");
        sb.append("[poster_path=" + posterPath + "]");
        sb.append("[overview=" + overview + "]");
        sb.append("}");
        return sb.toString();
    }
}
