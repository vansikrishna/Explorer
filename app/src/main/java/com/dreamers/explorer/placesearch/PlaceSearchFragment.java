package com.dreamers.explorer.placesearch;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dreamers.explorer.HomeApp;
import com.dreamers.explorer.R;
import com.dreamers.explorer.RetrofitService;
import com.dreamers.explorer.placesearch.modal.PlaceSearchResult;
import com.dreamers.explorer.placesearch.modal.Prediction;
import com.dreamers.explorer.placesearch.presenter.PlaceSearchPresenter;
import com.dreamers.explorer.placesearch.view.PlaceSearchView;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceSearchFragment extends Fragment implements PlaceSearchView {
    private OnFragmentInteractionListener mListener;
    @Inject
    RetrofitService retrofitService;
    @Inject
    PlaceSearchPresenter placeSearchPresenter;
    @BindView(R.id.searchEditText)
    public EditText searchEditText;
    @BindView(R.id.listView)
    public ListView listView;
    public PlaceSearchResultAdapter placeSearchResultAdapter;

    public PlaceSearchFragment() {
    }

    public static PlaceSearchFragment newInstance() {
        PlaceSearchFragment fragment = new PlaceSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeApp) getActivity().getApplication()).getAppComponent().inject(this);
        placeSearchPresenter.attach(this, retrofitService);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_search, container, false);
        ButterKnife.bind(this, view);
        initListeners();
        return view;
    }

    private void initListeners() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 2){
                    placeSearchPresenter.lookupForPlaces(s.toString());
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void showProgress() {
        if(mListener != null)
            mListener.showProgress();
    }

    @Override
    public void dismissProgress() {
        if(mListener != null)
            mListener.dismissProgress();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        placeSearchPresenter.detach();
        mListener = null;
    }

    @Override
    public void onLookupQueryResponse(boolean success, PlaceSearchResult placeSearchResult) {
        Prediction[] predictions = placeSearchResult.predictions;
        if(predictions != null && predictions.length > 0) {
            showPredictionsList(predictions);
        }
        else{
            showToastMessage("ZERO RESULTS FROM API");
        }
    }

    private void showPredictionsList(Prediction[] predictions) {
        ArrayList<Prediction> predictionArrayList = new ArrayList<>();
        predictionArrayList.addAll(Arrays.asList(predictions));
        if (predictionArrayList != null && predictionArrayList.size() > 0) {
            listView.setVisibility(View.VISIBLE);
            if (placeSearchResultAdapter == null)
                placeSearchResultAdapter = new PlaceSearchResultAdapter(getActivity(), -1, predictionArrayList);
            else
                placeSearchResultAdapter.setItems(predictionArrayList);
            listView.setAdapter(placeSearchResultAdapter);
        } else {
            listView.setVisibility(View.INVISIBLE);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prediction prediction = (Prediction) view.getTag(R.id.prediction);
                mListener.lookupForPlacesOfInterestWith(prediction);
            }
        });
    }

    @Override
    public void showToastMessage(@StringRes int resId) {
        Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public EditText getSearchText() {
        return searchEditText;
    }

    public interface OnFragmentInteractionListener {
        void showProgress();
        void dismissProgress();
        void lookupForPlacesOfInterestWith(Prediction prediction);
    }
}
