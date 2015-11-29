package com.amrendra.popularmovies.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.amrendra.popularmovies.BuildConfig;
import com.amrendra.popularmovies.R;
import com.amrendra.popularmovies.logger.Debug;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        Debug.e(BuildConfig.THE_MOVIE_DB_API_KEY_TOKEN, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Debug.c();
    }
}
