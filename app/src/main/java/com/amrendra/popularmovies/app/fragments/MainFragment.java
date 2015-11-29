package com.amrendra.popularmovies.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.ArrayList;
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


        setHasOptionsMenu(false);
        Debug.c();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sort_favourite:
                break;
            case R.id.action_sort_popularity:
                break;
            case R.id.action_sort_year:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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

        mMovieGridAdapter = new MovieGridAdapter(movieList);
        movieGridRecyleView.setAdapter(mMovieGridAdapter);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Spinner spinner = (Spinner) mainActivity.findViewById(R.id.toolbar_spinner);
        final CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(mainActivity);
        spinnerAdapter.addItem("Popularity");
        spinnerAdapter.addItem("Year");
        spinnerAdapter.addItem("Favourite");

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        Debug.c();
        movieList = new ArrayList<>();
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    // spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Debug.showToastShort(parent.getItemAtPosition(position).toString(), mainActivity);
    }


    // spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new MoviesLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        Debug.c();
        mSwipeRefreshLayout.setRefreshing(false);
        //Debug.e("" + data.size(), false);
        if(data == null || data.size() == 0){
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

    private void restartLoader() {
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }
}
