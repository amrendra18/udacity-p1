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
import android.widget.TextView;

import com.amrendra.popularmovies.R;
import com.amrendra.popularmovies.logger.Debug;
import com.amrendra.popularmovies.model.Movie;
import com.amrendra.popularmovies.utils.AppConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    @Bind((R.id.collapsing_toolbar))
    CollapsingToolbarLayout mCollapsingToolbar;

    @Bind(R.id.movie_poster_image)
    ImageView posterImageView;

    // Overview Card
    @Bind(R.id.detail_overview_card_title)
    TextView movieOverviewTitleTv;

    @Bind(R.id.detail_overview_card_ratings)
    TextView movieRatingsTv;

    @Bind(R.id.detail_overview_card_content)
    TextView movieOverviewContentTv;


    @Bind(R.id.detail_orginal_title_tv)
    TextView movieOriginalTitleTv;

    @Bind(R.id.detail_release_date_tv)
    TextView movieReleaseDateTv;

    @Bind(R.id.detail_orginal_language_tv)
    TextView movieOriginalLanguageTv;

    // End Overview Card


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

    private void shareMovie() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        shareIntent.setType("text/plain");

        String movieText = "I think you'll like this movie.";
        if (mMovie != null) {
            movieText += " " + mMovie.title + " #" + getResources().getString(R.string.app_name) + "\n";
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, movieText);
        startActivity(shareIntent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Debug.c();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        Bundle passedBundle = getArguments();
        if (passedBundle != null) {
            setupDetails(passedBundle);
        } else {
            Debug.e("SHOULD NOT HAPPEN", false);
        }
        addBackHomeArrow(rootView);
        return rootView;
    }

    private void setupDetails(Bundle bundle) {
        // fetch the movie
        mMovie = (Movie) bundle.get(AppConstants.MOVIE_SHARE);
        // set the poster for now
        // later get high resolution pic and replace silently ;-)
        posterImageView.setImageBitmap((Bitmap) bundle.get(AppConstants
                .MOVIE_BITMAP_SHARE));
        // set the title in the toolbar
        mCollapsingToolbar.setTitle(mMovie.title);

        // add the overview
        // add the rating
        // add details
        movieOverviewContentTv.setText(mMovie.overview);
        movieRatingsTv.setText(Double.toString(mMovie.averageVote)+"/10");

        movieOriginalTitleTv.setText(mMovie.originalTitle);
        movieReleaseDateTv.setText(mMovie.releaseDate);
        movieOriginalLanguageTv.setText(mMovie.originalLanguage);

    }

    private void setupToolbar() {

    }

    private void addBackHomeArrow(View rootView) {
        //for creating home button
        final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        ButterKnife.unbind(this);
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
