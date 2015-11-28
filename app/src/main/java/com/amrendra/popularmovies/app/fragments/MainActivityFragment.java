package com.amrendra.popularmovies.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amrendra.popularmovies.R;
import com.amrendra.popularmovies.app.activities.MainActivity;
import com.amrendra.popularmovies.logger.Debug;
import com.amrendra.popularmovies.model.Movie;

import java.util.List;

import butterknife.Bind;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    List<Movie> movieList;
    MainActivity mainActivity;

    @Bind(R.id.movies_list)
    public RecyclerView movieGridRecyleView;



    public MainActivityFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Debug.c();
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recList = (RecyclerView) view.findViewById(R.id.movies_list);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
}
