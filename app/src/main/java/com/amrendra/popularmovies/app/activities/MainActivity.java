package com.amrendra.popularmovies.app.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amrendra.popularmovies.R;
import com.amrendra.popularmovies.app.fragments.MainFragment;
import com.amrendra.popularmovies.logger.Debug;
import com.amrendra.popularmovies.model.Movie;
import com.amrendra.popularmovies.utils.AppConstants;


public class MainActivity extends AppCompatActivity implements MainFragment.Callback {

    private static final String TAG_MAIN_FRAGMENT = "main_fragment";

    private MainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        Debug.c();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Debug.c();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClickMovieThumbnail(Movie movie, Bitmap bitmap, View view) {
        Debug.e("Movie clicked : " + movie.title, false);
        //Debug.showToastShort(movie.title + " clicked", this);
        ActivityOptions options = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Debug.e("Animation", false);
            options = ActivityOptions.
                    makeSceneTransitionAnimation(this, view, AppConstants.SHARED_IMAGE_VIEW);
        }
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(AppConstants.MOVIE_SHARE, movie);
        intent.putExtra(AppConstants.MOVIE_BITMAP_SHARE, bitmap);
        if (options != null) {
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
