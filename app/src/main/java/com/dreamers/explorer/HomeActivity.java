package com.dreamers.explorer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dreamers.explorer.placedetail.PlaceDetailFragment;
import com.dreamers.explorer.placedetail.modal.Place;
import com.dreamers.explorer.placelist.PlaceListFragment;
import com.dreamers.explorer.placesearch.PlaceSearchFragment;
import com.dreamers.explorer.placesearch.modal.Prediction;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements PlaceListFragment.OnFragmentInteractionListener,
        PlaceSearchFragment.OnFragmentInteractionListener, PlaceDetailFragment.OnFragmentInteractionListener{

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        loadSearchFragment();
    }

    private void loadSearchFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_layout, PlaceSearchFragment.newInstance())
                .addToBackStack("SEARCH")
                .commit();
    }

    private void loadPlacesOfInterestFragment(String placeId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_layout, PlaceListFragment.newInstance(placeId))
                .commit();
    }

    public void showProgress(){
        if(progressDialog == null){
            progressDialog = ProgressDialog.show(this, "Loading", "Please wait");
        }
        else{
            progressDialog.show();
        }
    }

    public void dismissProgress(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showPlaceDetail(Place place) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_layout, PlaceDetailFragment.newInstance(place))
                .addToBackStack("DETAIL")
                .commit();
    }

    @Override
    public void lookupForPlacesOfInterestWith(Prediction prediction) {
        getSupportFragmentManager().popBackStack();
        loadPlacesOfInterestFragment(prediction.place_id);
    }
}
