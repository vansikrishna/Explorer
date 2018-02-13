package com.dreamers.explorer.placelist.presenter;

import com.dreamers.explorer.R;
import com.dreamers.explorer.RetrofitService;
import com.dreamers.explorer.common.Constants;
import com.dreamers.explorer.placedetail.modal.PlaceResult;
import com.dreamers.explorer.placelist.modal.PlaceListResult;
import com.dreamers.explorer.placelist.view.PlaceListView;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by c029312 on 2/7/18.
 */

public class PlaceListPresenter {

    private PlaceListView placeListView;
    private RetrofitService retrofitService;

    public PlaceListPresenter() {
    }

    public void attach(PlaceListView placeListView, RetrofitService retrofitService){
        this.placeListView = placeListView;
        this.retrofitService = retrofitService;
    }

    public void detach(){
        placeListView = null;
        retrofitService = null;
    }

    public void lookupForNearbyPlaces(String placeId) {
        if(placeId == null){
            placeListView.showToastMessage(R.string.empty_place_id);
            return;
        }
        if(placeId != null && placeId.length() > 0){
            retrofitService.searchForPlaceInDetail(placeId, Constants.API_KEY)
                    .flatMap(new Function<PlaceResult, Flowable<PlaceListResult>>() {
                        @Override
                        public Flowable<PlaceListResult> apply(PlaceResult placeResult) throws Exception {
                            if(placeResult.status.equalsIgnoreCase("OK")) {
                                return retrofitService.searchForPlacesNearby(placeResult.place.geometry.location.toLatLng(),
                                        Constants.RADIUS, Constants.API_KEY);
                            }
                            else{
                                placeListView.showToastMessage(placeResult.status);
                                return Flowable.empty();
                            }
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<PlaceListResult>() {
                        @Override
                        public void accept(PlaceListResult placeListResult) throws Exception {
                            if(placeListResult.status.equalsIgnoreCase("OK")){
                                placeListView.onGetPlacesListResponse(true, placeListResult);
                            }
                            else{
                                placeListView.showToastMessage(placeListResult.status);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            placeListView.showToastMessage(throwable.toString());
                        }
                    });
        }
    }

}