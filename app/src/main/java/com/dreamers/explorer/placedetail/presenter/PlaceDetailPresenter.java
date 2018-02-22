package com.dreamers.explorer.placedetail.presenter;

import com.dreamers.explorer.R;
import com.dreamers.explorer.RetrofitService;
import com.dreamers.explorer.common.Constants;
import com.dreamers.explorer.placedetail.modal.PlaceResult;
import com.dreamers.explorer.placedetail.view.PlaceDetailView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by c029312 on 2/22/18.
 */

public class PlaceDetailPresenter {

    RetrofitService retrofitService;
    PlaceDetailView placeDetailView;

    public void attach(PlaceDetailView placeDetailView, RetrofitService retrofitService){
        this.placeDetailView = placeDetailView;
        this.retrofitService = retrofitService;
    }

    public void getPlaceDetail(String placeId){
        if(placeId == null){
            placeDetailView.showToastMessage(R.string.empty_place_id);
            return;
        }
        retrofitService.searchForPlaceInDetail(placeId, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PlaceResult>() {
                    @Override
                    public void accept(PlaceResult placeResult) throws Exception {
                        if(placeResult.status.equalsIgnoreCase("OK"))
                            placeDetailView.onGetPlaceDetailResponse(placeResult.place);
                        else
                            placeDetailView.showToastMessage(placeResult.status);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        placeDetailView.showToastMessage("Exception: "+throwable.toString());
                    }
                });
    }

    public void detach(){
        placeDetailView = null;
    }

    public PlaceDetailView getView() {
        return placeDetailView;
    }
}
