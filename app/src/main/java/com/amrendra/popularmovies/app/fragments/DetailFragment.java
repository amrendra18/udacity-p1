package com.amrendra.popularmovies.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amrendra.popularmovies.R;
import com.amrendra.popularmovies.logger.Debug;
import com.amrendra.popularmovies.model.Movie;
import com.amrendra.popularmovies.utils.AppConstants;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    Movie mMovie = null;

    public DetailFragment() {
        Debug.c();
    }

    public static DetailFragment getInstance(Bundle bundle) {
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        Debug.c();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Debug.c();
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detail_share:
                shareMovie();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        shareIntent.setType("text/plain");

        String movieText = "I think you'll like this movie.";
        if (mMovie != null) {
            movieText += " " + mMovie.title + " #" + getResources().getString(R.string.app_name) + "\n";
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, movieText);
        return shareIntent;
    }

    private void shareMovie() {
        startActivity(createShareForecastIntent());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Debug.c();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle passedBundle = getArguments();
        Debug.bundle(passedBundle);
        if (passedBundle != null) {
            ImageView posterImageView = (ImageView) rootView.findViewById(R.id.movie_poster_image);
            posterImageView.setImageBitmap((Bitmap) passedBundle.get(AppConstants
                    .MOVIE_BITMAP_SHARE));
            mMovie = (Movie) passedBundle.get(AppConstants.MOVIE_SHARE);
            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle(mMovie.title);
        }

        //for crate home button
        final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
