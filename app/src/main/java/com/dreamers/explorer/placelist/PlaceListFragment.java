package com.dreamers.explorer.placelist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dreamers.explorer.HomeApp;
import com.dreamers.explorer.R;
import com.dreamers.explorer.RetrofitService;
import com.dreamers.explorer.placedetail.modal.Place;
import com.dreamers.explorer.placelist.modal.PlaceListResult;
import com.dreamers.explorer.placelist.presenter.PlaceListPresenter;
import com.dreamers.explorer.placelist.view.PlaceListView;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlaceListFragment extends Fragment implements PlaceListView{
    private static final String ARG_PARAM1 = "placeId";
    private String placeId;
    PlaceListAdapter placeListAdapter;
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.listView)
    ListView listView;
    @Inject
    PlaceListPresenter placeListPresenter;
    @Inject
    RetrofitService retrofitService;

    public PlaceListFragment() {
    }

    public static PlaceListFragment newInstance(String placeId) {
        PlaceListFragment fragment = new PlaceListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, placeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            placeId = getArguments().getString(ARG_PARAM1);
        }
        ((HomeApp) getActivity().getApplication()).getAppComponent().inject(this);
        placeListPresenter.attach(this, retrofitService);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_list, container, false);
        ButterKnife.bind(this, view);
        lookupForNearbyPlaces();
        return view;
    }

    public void setPlaceIdAndSearch(String placeId){
        this.placeId = placeId;
        lookupForNearbyPlaces();
    }

    public void lookupForNearbyPlaces(){
        placeListPresenter.lookupForNearbyPlaces(placeId);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
        placeListPresenter.detach();
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
    public void onGetPlacesListResponse(boolean success, PlaceListResult placeListResult) {
        ArrayList<Place> placeArrayList = new ArrayList<>();
        placeArrayList.addAll(Arrays.asList(placeListResult.places));
        if (placeArrayList != null && placeArrayList.size() > 0) {
            listView.setVisibility(View.VISIBLE);
            if (placeListAdapter == null)
                placeListAdapter = new PlaceListAdapter(getActivity(), -1, placeArrayList);
            else
                placeListAdapter.setItems(placeArrayList);
            listView.setAdapter(placeListAdapter);
        } else {
            listView.setVisibility(View.INVISIBLE);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place place = (Place) view.getTag(R.id.place);
                mListener.showPlaceDetail(place);
            }
        });
    }

    @Override
    public void showToastMessage(int resId) {
        Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        Log.e("TAG", ""+message);
    }

    public interface OnFragmentInteractionListener {
        void showProgress();
        void dismissProgress();

        void showPlaceDetail(Place place);
    }
}
