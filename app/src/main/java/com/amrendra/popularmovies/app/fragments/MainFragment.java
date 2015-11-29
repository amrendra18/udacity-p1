package com.amrendra.popularmovies.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.amrendra.popularmovies.R;
import com.amrendra.popularmovies.adapter.CustomSpinnerAdapter;
import com.amrendra.popularmovies.adapter.MovieGridAdapter;
import com.amrendra.popularmovies.app.activities.MainActivity;
import com.amrendra.popularmovies.loaders.MoviesLoader;
import com.amrendra.popularmovies.logger.Debug;
import com.amrendra.popularmovies.model.Movie;
import com.amrendra.popularmovies.utils.MoviesConstants;
import com.amrendra.popularmovies.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<List<Movie>>, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener {


    private static final int MOVIE_LOADER = 0;

    private List<Movie> movieList;
    private MainActivity mainActivity;
    private MovieGridAdapter mMovieGridAdapter;


    @Bind(R.id.movies_gridlist)
    public RecyclerView movieGridRecyleView;

    @Bind(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    int navColor;

    String currentSortingBy;

        /*
    Lifecycle of a fragment
    1. onInflate
    2. onAttach()
    3. onCreate()
    4. onCreateView()
       Activity.onCreate()
    5. onActivityCreated()
    6. onStart()
    7. onResume() Fragment is visible now
    8. onPause()
    9. onStop()
    10. onDestroyView();
    11. onDestroy()
    12. onDetach
     */


    public MainFragment() {
        Debug.c();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Debug.c();
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.c();
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Debug.c();
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setProgressViewOffset(true, 200, 500);
        mSwipeRefreshLayout.setOnRefreshListener(this);

/*        try {
            Field f = mSwipeRefreshLayout.getClass().getDeclaredField("mCircleView");
            f.setAccessible(true);
            ImageView img = (ImageView)f.get(mSwipeRefreshLayout);
            img.setImageResource(R.mipmap.loading);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }*/

        int gridColumns = getResources().getInteger(R.integer.grid_columns);
        Debug.e("GRID : " + gridColumns, false);


        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), gridColumns);

        movieGridRecyleView.setLayoutManager(mGridLayoutManager);
        movieGridRecyleView.setHasFixedSize(true);
        navColor = ContextCompat.getColor(getActivity(), (R.color.colorPrimaryTransparentNav));
        mMovieGridAdapter = new MovieGridAdapter(movieList, navColor, getActivity());
        movieGridRecyleView.setAdapter(mMovieGridAdapter);
        Debug.c();
        Debug.bundle(savedInstanceState);
        if (savedInstanceState != null) {
            currentSortingBy = savedInstanceState.getString(MoviesConstants.SORT_BY);
        } else {
            currentSortingBy = MoviesConstants.SORT_BY_POPULARITY;
        }
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Debug.c();
        Debug.bundle(savedInstanceState);


        Spinner spinner = (Spinner) getActivity().findViewById(R.id.toolbar_spinner);
        final CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(getActivity());
        String[] sortOptions = getResources().getStringArray(R.array.string_sort_by);
        spinnerAdapter.addItems(Arrays.asList(sortOptions));

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);


        movieList = new ArrayList<>();
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MoviesConstants.SORT_BY, currentSortingBy);
        Debug.c();
    }

    @Override
    public void onStart() {
        super.onStart();
        Debug.c();
    }

    @Override
    public void onResume() {
        super.onResume();
        Debug.c();
    }

    @Override
    public void onPause() {
        super.onPause();
        Debug.c();
    }

    @Override
    public void onStop() {
        super.onStop();
        Debug.c();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Debug.c();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Debug.c();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
        Debug.c();
    }


    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        Debug.c();
        String sortBy = PreferenceManager.getInstance(getActivity()).readValue(MoviesConstants
                .SORT_BY, MoviesConstants.SORT_BY_POPULARITY);
        if (sortBy.equals(MoviesConstants.SORT_BY_FAVOURITES)) {
            Debug.showToastShort("Favourites not fetched", getActivity(), true);
            mSwipeRefreshLayout.setRefreshing(false);
            return null;
        }
        return new MoviesLoader(getActivity(), sortBy);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        Debug.c();
        mSwipeRefreshLayout.setRefreshing(false);
        //Debug.e("" + data.size(), false);
        if (data == null || data.size() == 0) {
            Debug.showToastShort("Error", getActivity());
            //return;
        }
        movieList = data;
        mMovieGridAdapter.resetMovieList(data);

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        Debug.c();
        mMovieGridAdapter.resetMovieList(null);
    }

    @Override
    public void onRefresh() {
        Debug.c();
        restartLoader();
    }

    public void restartLoader() {
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    // spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Debug.c();
        String selection = parent.getItemAtPosition(position).toString();
        Debug.showToastShort(selection, getActivity());
        Debug.e(selection, false);
        PreferenceManager.getInstance(getActivity()).debug();
        String nextSortingBy;
        switch (position) {
            case 0: // popularity
                nextSortingBy = MoviesConstants.SORT_BY_POPULARITY;
                break;
            case 1: // ratings
                nextSortingBy = MoviesConstants.SORT_BY_RATINGS;
                break;
            case 2: //favourite
                nextSortingBy = MoviesConstants.SORT_BY_FAVOURITES;
                break;
            default: //popularity
                nextSortingBy = MoviesConstants.SORT_BY_POPULARITY;
                break;
        }
        PreferenceManager.getInstance(getActivity()).writeValue(MoviesConstants.SORT_BY,
                nextSortingBy);
        if (nextSortingBy != currentSortingBy) {
            restartLoader();
        }
        currentSortingBy = nextSortingBy;
    }


    // spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
