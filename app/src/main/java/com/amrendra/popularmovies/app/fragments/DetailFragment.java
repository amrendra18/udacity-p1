package com.amrendra.popularmovies.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amrendra.popularmovies.R;
import com.amrendra.popularmovies.loaders.ReviewsLoader;
import com.amrendra.popularmovies.loaders.TrailersLoader;
import com.amrendra.popularmovies.logger.Debug;
import com.amrendra.popularmovies.model.Movie;
import com.amrendra.popularmovies.model.Review;
import com.amrendra.popularmovies.model.ReviewList;
import com.amrendra.popularmovies.model.Trailer;
import com.amrendra.popularmovies.model.TrailerList;
import com.amrendra.popularmovies.utils.AppConstants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {


    private static final int REVIEWS_LOADER = 0;
    private static final int TRAILER_LOADER = 1;

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


    // Reviews Card
    @Bind(R.id.review_progressbar)
    ProgressBar reviewsProgressbar;

    @Bind(R.id.no_reviews_tv)
    TextView noReviewsTv;

    @Bind(R.id.reviews_container)
    LinearLayout reviewsContainer;
    // End : Reviews Card


    // Trailes Card
    @Bind(R.id.trailer_progressbar)
    ProgressBar trailerProgressbar;

    @Bind(R.id.no_trailers_tv)
    TextView noTrailerTv;

    @Bind(R.id.trailer_recyvlerview)
    RecyclerView trailerRecyclerView;
    // End : Reviews Card


    Movie mMovie = null;
    List<Review> mReviewList = null;
    List<Trailer> mTrailerList = null;


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
        movieRatingsTv.setText(Double.toString(mMovie.averageVote) + "/10");

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
        Debug.c();
        if (mReviewList == null || mReviewList.size() == 0) {
            getLoaderManager().initLoader(REVIEWS_LOADER, null, reviewListLoaderCallbacks);
        }
        if (mTrailerList == null || mTrailerList.size() == 0) {
            getLoaderManager().initLoader(TRAILER_LOADER, null, trailerListLoaderCallbacks);
        }

    }

    @Override
    public void onPause() {
        Debug.c();
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


    private LoaderManager.LoaderCallbacks<ReviewList> reviewListLoaderCallbacks
            = new LoaderManager.LoaderCallbacks<ReviewList>() {
        @Override
        public Loader<ReviewList> onCreateLoader(int id, Bundle args) {
            Debug.c();
            reviewsProgressbar.setVisibility(ProgressBar.VISIBLE);
            return new ReviewsLoader(getActivity(), mMovie.id);
        }

        @Override
        public void onLoadFinished(Loader<ReviewList> loader, ReviewList data) {
            Debug.c();
            addReviews(data);
        }

        @Override
        public void onLoaderReset(Loader<ReviewList> loader) {
            Debug.c();
        }
    };

    private LoaderManager.LoaderCallbacks<TrailerList> trailerListLoaderCallbacks
            = new LoaderManager.LoaderCallbacks<TrailerList>() {

        @Override
        public Loader<TrailerList> onCreateLoader(int id, Bundle args) {
            Debug.c();
            return new TrailersLoader(getActivity(), mMovie.id);
        }

        @Override
        public void onLoadFinished(Loader<TrailerList> loader, TrailerList data) {
            Debug.e(data.toString(), false);
            Debug.c();
        }

        @Override
        public void onLoaderReset(Loader<TrailerList> loader) {
            Debug.c();
        }
    };


    private void addReviews(ReviewList result) {
        reviewsProgressbar.setVisibility(ProgressBar.INVISIBLE);
        Debug.e(result.toString(), false);
        if (result == null) {
            noReviewsTv.setText(getResources().getText(R.string.error_detail_reviews));
            noReviewsTv.setVisibility(View.VISIBLE);
            return;
        }
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        List<Review> reviewList = result.results;

        if (!(reviewList == null || reviewList.isEmpty())) {
            int len = reviewList.size();


            for (int i = 0; i < len; i++) {
                Review review = reviewList.get(i);
                final View reviewView = inflater.inflate(R.layout.review_layout, reviewsContainer, false);
                TextView reviewAuthor = ButterKnife.findById(reviewView, R.id.review_by);
                TextView reviewContent = ButterKnife.findById(reviewView, R.id.review_content);
                reviewAuthor.setText(review.author);
                reviewContent.setText(review.content);
                reviewsContainer.addView(reviewView);
                if (i < len - 1) {
                    final View dividerView = inflater.inflate(R.layout.divider_content,
                            reviewsContainer, false);
                    View divider = ButterKnife.findById(dividerView, R.id.divider_content);
                    reviewsContainer.addView(divider);
                }
            }
            noReviewsTv.setVisibility(View.GONE);
        } else {
            noReviewsTv.setText(getResources().getText(R.string.no_detail_reviews));
            noReviewsTv.setVisibility(View.VISIBLE);
        }

    }


    private void addTrailers(TrailerList result) {
        trailerProgressbar.setVisibility(ProgressBar.INVISIBLE);
        Debug.e(result.toString(), false);
/*        if (result == null) {
            noReviewsTv.setText(getResources().getText(R.string.error_detail_reviews));
            noReviewsTv.setVisibility(View.VISIBLE);
            return;
        }
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        List<Review> reviewList = result.results;
        if (!(reviewList == null || reviewList.isEmpty())) {
            for (Review review : reviewList) {
                final View reviewView = inflater.inflate(R.layout.review_layout, reviewsContainer, false);
                TextView reviewAuthor = ButterKnife.findById(reviewView, R.id.review_by);
                TextView reviewContent = ButterKnife.findById(reviewView, R.id.review_content);
                reviewAuthor.setText(review.author);
                reviewContent.setText(review.content);
                reviewsContainer.addView(reviewView);
            }
        } else {
            noReviewsTv.setText(getResources().getText(R.string.no_detail_reviews));
            noReviewsTv.setVisibility(View.VISIBLE);
        }*/

    }
}
