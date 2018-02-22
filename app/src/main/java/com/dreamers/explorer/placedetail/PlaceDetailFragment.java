package com.dreamers.explorer.placedetail;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamers.explorer.HomeActivity;
import com.dreamers.explorer.HomeApp;
import com.dreamers.explorer.R;
import com.dreamers.explorer.RetrofitService;
import com.dreamers.explorer.placedetail.modal.Place;
import com.dreamers.explorer.placedetail.presenter.PlaceDetailPresenter;
import com.dreamers.explorer.placedetail.view.PlaceDetailView;
import com.dreamers.explorer.placeimages.PlaceImagesAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceDetailFragment extends Fragment implements PlaceDetailView{
    private static final String ARG_PARAM1 = "param1";

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private Place place;
    @Inject
    RetrofitService retrofitService;
    @Inject
    PlaceDetailPresenter placeDetailPresenter;

    private OnFragmentInteractionListener mListener;

    public PlaceDetailFragment() {
    }

    public static PlaceDetailFragment newInstance(Place place) {
        PlaceDetailFragment fragment = new PlaceDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, place);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            place = getArguments().getParcelable(ARG_PARAM1);
        }
        ((HomeApp) getActivity().getApplication()).getAppComponent().inject(this);
        placeDetailPresenter.attach(this, retrofitService);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_detail, container, false);
        ButterKnife.bind(this, view);
        loadPlaceDetail();
        return view;
    }

    private void loadPlaceDetail() {
        setupViews(place);
        placeDetailPresenter.getPlaceDetail(place.place_id);
    }

    private void setupViews(Place place) {
        name.setText(place.name);
        address.setText(place.formatted_address);
        ratingBar.setRating(place.rating);
        description.setText(place.reference);
        PlaceImagesAdapter storePagerAdpter =  new PlaceImagesAdapter(getActivity(), place.photos);
        viewPager.setAdapter(storePagerAdpter);
        tabLayout.setupWithViewPager(viewPager, true);
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
    }

    @Override
    public void showProgress() {
        if(getActivity() != null){
            ((HomeActivity) getActivity()).showProgress();
        }
    }

    @Override
    public void dismissProgress() {
        if(getActivity() != null){
            ((HomeActivity) getActivity()).dismissProgress();
        }
    }

    @Override
    public void onGetPlaceDetailResponse(Place place) {
        setupViews(place);
    }

    @Override
    public void showToastMessage(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    public interface OnFragmentInteractionListener {
        void showProgress();
        void dismissProgress();
    }
}
