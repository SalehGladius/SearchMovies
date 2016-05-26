/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.oveissi.searchmovies.features.movies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.oveissi.searchmovies.R;
import ir.oveissi.searchmovies.adapters.MoviesAdapter;
import ir.oveissi.searchmovies.data.Movie;
import ir.oveissi.searchmovies.utils.EndlessRecyclerOnScrollListener;


public class MoviesFragment extends Fragment implements MoviesContract.View {

    private MoviesContract.Presenter mPresenter;

    private MoviesAdapter mListAdapter;

    public MoviesFragment() {
        // Requires empty public constructor
    }

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter( MoviesContract.Presenter presenter) {
        mPresenter = presenter;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mPresenter.result(requestCode, resultCode);
//    }

    RecyclerView rv;
    public String title="Batman";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movies, container, false);
        rv=(RecyclerView)root.findViewById(R.id.rvMovies);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mListAdapter=new MoviesAdapter(getActivity(), new ArrayList<Movie>());
        rv.setAdapter(mListAdapter);
        mPresenter.getMoviesByTitle(title,1);


        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) rv.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                mPresenter.getMoviesByTitle(title,current_page);
            }
        });

        return root;
    }

    @Override
    public void showMoreMovies(List<Movie> movies) {
        for(Movie p:movies)
        {
            mListAdapter.addItem(p);
        }
    }
}