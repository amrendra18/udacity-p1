package com.amrendra.popularmovies.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.amrendra.popularmovies.R;
import com.amrendra.popularmovies.logger.Debug;
import com.amrendra.popularmovies.model.Movie;
import com.amrendra.popularmovies.utils.AppConstants;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Debug.c();
        Bundle bundle = getIntent().getExtras();
        Debug.bundle(bundle);

        Movie movie = (Movie) bundle.get(AppConstants.MOVIE_SHARE);
        setTitle(movie.title);
    }

}
