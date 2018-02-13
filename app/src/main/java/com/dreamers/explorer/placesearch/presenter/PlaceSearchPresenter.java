package com.dreamers.explorer.placesearch.presenter;

import com.dreamers.explorer.R;
import com.dreamers.explorer.RetrofitService;
import com.dreamers.explorer.common.Constants;
import com.dreamers.explorer.placesearch.modal.PlaceSearchResult;
import com.dreamers.explorer.placesearch.view.PlaceSearchView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by c029312 on 2/7/18.
 */

public class PlaceSearchPresenter {

    private RetrofitService retrofitService;
    private PlaceSearchView placeSearchView;

    public PlaceSearchPresenter(){
    }

    public void attach(PlaceSearchView placeSearchView, RetrofitService retrofitService){
        this.placeSearchView = placeSearchView;
        this.retrofitService = retrofitService;
    }

    public void detach(){
        this.placeSearchView = null;
    }

    public void lookupForPlaces(String query){
        if(query.length() == 0){
            placeSearchView.showToastMessage(R.string.empty_search_query);
            return;
        }
        if(query.length() > 2){
            retrofitService.queryForPlace(query, Constants.QUERY_TYPES, Constants.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<PlaceSearchResult>() {
                        @Override
                        public void accept(PlaceSearchResult placeSearchResult) throws Exception {
                            if(placeSearchResult.status.equalsIgnoreCase("OK")) {
                                placeSearchView.onLookupQueryResponse(true, placeSearchResult);
                            }
                            else {
                                placeSearchView.showToastMessage(placeSearchResult.status);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            placeSearchView.showToastMessage("Exception - "+throwable.toString());
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {

                        }
                    });
            return;
        }

    }

    public void lookupForLocation(String placeId){

    }

}
